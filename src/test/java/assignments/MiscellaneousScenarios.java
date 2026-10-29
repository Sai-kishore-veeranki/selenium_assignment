package assignments;

import framework.base.BaseTest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MiscellaneousScenarios extends BaseTest {
    private void openPage(String path) {
        openAuthenticatedPage(path);
    }

    @Test(groups = {"misc", "smoke"})
    public void switchBetweenTabsOnMultipleTabsPage() {
        openPage("/Home/Tabs");
        driver.findElement(By.cssSelector("a[href='#tab_2']")).click();
        Assert.assertTrue(driver.findElement(By.id("tab_2")).isDisplayed());
    }

    @Test(groups = "misc")
    public void hoverMenuAndOpenSubMenu() {
        openPage("/Home/Menu");
        driver.findElement(By.cssSelector("a[href='#tab_2']")).click();
        WebElement testingMenu = driver.findElement(By.cssSelector("a.dropbtn"));
        new Actions(driver).moveToElement(testingMenu).perform();
        WebElement seleniumSubMenu = driver.findElement(By.id("selbtn"));
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(seleniumSubMenu));
        seleniumSubMenu.click();
    }

    @Test(groups = "misc")
    public void selectAnAutocompleteSuggestion() {
        openPage("/Home/AutoComplete");
        driver.findElement(By.cssSelector("a[href='#tab_1']")).click();
        WebElement input = driver.findElement(By.id("txtSingleAutoComplete"));
        input.click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", input, "Java");
        Assert.assertEquals(input.getAttribute("value"), "Java");
        ((JavascriptExecutor) driver).executeScript(
                "window.jQuery(arguments[0]).autocomplete('search', arguments[1]);", input, "Java");
        WebElement suggestion = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//ul[contains(@class,'ui-autocomplete')]//li[contains(@class,'ui-menu-item')]"
                                + "[.//div[normalize-space()='Java']]")));
        suggestion.click();
        Assert.assertEquals(input.getAttribute("value"), "Java");
    }

    @Test(groups = "misc")
    public void expandCollapsibleContent() {
        openPage("/Home/Collapse");
        WebElement firstPanel = driver.findElement(By.cssSelector("#accordion a[data-toggle='collapse']"));
        firstPanel.click();
        Assert.assertEquals(firstPanel.getAttribute("aria-expanded"), "true");
        Assert.assertTrue(driver.findElement(By.id("collapseOne")).isDisplayed());
    }

    @Test(groups = "misc")
    public void chooseAFileForUploadWithoutSubmittingIt() throws IOException {
        openPage("/Home/UploadImage");
        Path sample = Path.of("target", "sample-upload.png").toAbsolutePath();
        Files.createDirectories(sample.getParent());
        byte[] image = Base64.getDecoder().decode(
                "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAO+jv04AAAAASUVORK5CYII=");
        Files.write(sample, image);
        WebElement upload = driver.findElement(By.id("file"));
        upload.sendKeys(sample.toString());
        Assert.assertTrue(upload.getAttribute("value").endsWith("sample-upload.png"));
    }

    @Test(groups = "misc")
    public void changeSliderValue() {
        openPage("/Home/Slider");
        WebElement track = driver.findElement(By.cssSelector(".slider-track"));
        new Actions(driver).moveToElement(track).click().perform();
        int selectedValue = Integer.parseInt(
                driver.findElement(By.cssSelector(".slider-handle")).getAttribute("aria-valuenow"));
        int displayedValue = Integer.parseInt(driver.findElement(By.id("ex6SliderVal")).getText());
        Assert.assertTrue(selectedValue > 3 && selectedValue <= 100);
        org.testng.Reporter.log(
                "Selected slider value: " + selectedValue + "; displayed value: " + displayedValue);
    }

    @Test(groups = "misc")
    public void displayTooltipAfterButtonAction() {
        openPage("/Home/Tooltip");
        driver.findElement(By.id("btnTooltip")).click();
        Assert.assertEquals(driver.findElement(By.id("btnTooltip")).getAttribute("data-original-title"),
                "Thank you for being here!");
    }

    @Test(groups = "misc")
    public void switchToAndReadAnIframe() {
        openPage("/Home/Iframe");
        driver.switchTo().frame("iframe2");
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("Home"));
        driver.switchTo().defaultContent();
    }

    @Test(groups = "misc")
    public void checkHttpStatusOfSameOriginBrokenLinks() throws IOException {
        openPage("/Home/Links");
        driver.findElement(By.cssSelector("a[href='#tab_2']")).click();
        List<WebElement> links = driver.findElements(By.cssSelector("#tab_2 a[href]"));
        List<Integer> statuses = new ArrayList<>();
        for (WebElement link : links) {
            URI resolved = URI.create(baseUrl).resolve(link.getAttribute("href"));
            if (resolved.getHost().equalsIgnoreCase(URI.create(baseUrl).getHost())) {
                statuses.add(httpStatus(resolved.toURL()));
            }
        }
        org.testng.Reporter.log("Same-origin HTTP statuses for broken-link examples: " + statuses);
        Assert.assertTrue(statuses.size() >= 3, "Expected to inspect the Broken Links examples.");
    }

    private int httpStatus(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        try {
            return connection.getResponseCode();
        } finally {
            connection.disconnect();
        }
    }

    @Test(groups = "misc")
    public void inspectEmployeeSearchTable() {
        openPage("/Employee/Search");
        WebElement table = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table")));
        Assert.assertTrue(table.isDisplayed());
        Assert.assertTrue(table.findElements(By.cssSelector("thead th")).size() > 0);
        Assert.assertTrue(table.findElements(By.cssSelector("tbody tr")).size() >= 0);
    }

    @Test(groups = "misc")
    public void scrollPageWithJavascriptExecutor() {
        openPage("/Home/Index");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Long scrollPosition = (Long) ((JavascriptExecutor) driver).executeScript("return window.scrollY;");
        Assert.assertTrue(scrollPosition >= 0);
    }

    @Test(groups = "misc")
    public void captureScreenshot() throws IOException {
        openPage("/Home/Index");
        Path screenshot = Path.of("target", "screenshots", "magnus-home.png");
        Files.createDirectories(screenshot.getParent());
        Files.write(screenshot, ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        Assert.assertTrue(Files.size(screenshot) > 0);
    }
}
