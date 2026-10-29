package assignments;

import framework.base.BaseTest;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebDriverMethods extends BaseTest {
    @Test(groups = {"webdriver", "smoke"})
    public void getAndReadPageProperties() {
        driver.get(baseUrl);
        Assert.assertEquals(driver.getTitle(), "Login");
        Assert.assertTrue(driver.getCurrentUrl().startsWith(baseUrl));
        Assert.assertTrue(driver.getPageSource().contains("frmLogin"));
        Assert.assertNotNull(driver.getWindowHandle());
    }

    @Test(groups = "webdriver")
    public void navigateBackForwardAndRefresh() {
        driver.get(baseUrl + "/Account/AdminLogin");
        driver.navigate().to(baseUrl);
        driver.navigate().refresh();
        Assert.assertEquals(driver.getTitle(), "Login");
        driver.navigate().back();
        driver.navigate().forward();
        Assert.assertEquals(driver.getTitle(), "Login");
    }

    @Test(groups = "webdriver")
    public void manageWindowAndCloseASecondaryWindow() {
        driver.manage().window().setSize(new Dimension(1200, 800));
        driver.manage().window().setPosition(new Point(20, 20));
        Dimension actualSize = driver.manage().window().getSize();
        Assert.assertTrue(Math.abs(actualSize.getWidth() - 1200) <= 5);
        Assert.assertTrue(Math.abs(actualSize.getHeight() - 800) <= 5);
        Assert.assertNotNull(driver.manage().window().getPosition());

        String original = driver.getWindowHandle();
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", baseUrl);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> webDriver.getWindowHandles().size() == 2);
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(original);
        Assert.assertEquals(driver.getWindowHandles().size(), 1);
    }

    @Test(groups = "webdriver")
    public void quitIsHandledByTheSharedTeardown() {
        Assert.assertTrue(driver.findElement(By.id("UserName")).isDisplayed());
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("Password")));
    }
}
