package assignments;

import framework.base.BaseTest;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PopupsAndWindows extends BaseTest {
    private void openPopupPage() {
        openAuthenticatedPage("/Home/Popup");
    }

    @Test(groups = {"popups", "smoke"})
    public void simpleAlertTextAndAccept() {
        openPopupPage();
        driver.findElement(By.id("alertBox")).click();
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        Assert.assertFalse(alert.getText().isBlank());
        alert.accept();
    }

    @Test(groups = "popups")
    public void confirmationAlertCanBeAcceptedAndDismissed() {
        openPopupPage();
        driver.findElement(By.id("confirmBox")).click();
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        Assert.assertFalse(alert.getText().isBlank());
        alert.accept();

        driver.findElement(By.id("confirmBox")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent())
                .dismiss();
    }

    @Test(groups = "popups")
    public void promptAlertAcceptsText() {
        openPopupPage();
        driver.findElement(By.id("promptBtn")).click();
        Alert prompt = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        prompt.sendKeys("Selenium User");
        prompt.accept();
        Assert.assertTrue(driver.findElement(By.id("promptBox")).getText().contains("Selenium User"));
    }

    @Test(groups = "popups")
    public void inWindowModalCanBeOpenedAndClosed() {
        openPopupPage();
        driver.findElement(By.id("btn-six")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("myModal1")));
        Assert.assertTrue(driver.findElement(By.id("myModal1")).getText().contains("Popup One body"));
        Assert.assertTrue(driver.findElement(
                By.xpath("//div[@id='myModal1']//button[normalize-space()='Close']")).isDisplayed());
    }

    @Test(groups = "popups")
    public void switchBetweenMultipleWindows() {
        openPopupPage();
        String original = driver.getWindowHandle();
        driver.findElement(By.id("btn-one")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> webDriver.getWindowHandles().size() > 1);

        Set<String> handles = driver.getWindowHandles();
        Assert.assertEquals(handles.size(), 2);
        for (String handle : handles) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                Assert.assertNotNull(driver.getTitle());
                driver.close();
            }
        }
        driver.switchTo().window(original);
        Assert.assertEquals(driver.getWindowHandle(), original);
    }
}
