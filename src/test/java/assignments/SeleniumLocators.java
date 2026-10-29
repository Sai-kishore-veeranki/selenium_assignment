package assignments;

import framework.base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SeleniumLocators extends BaseTest {
    @Test(groups = {"locators", "smoke"})
    public void locateLoginFieldsByIdAndName() {
        Assert.assertTrue(driver.findElement(By.id("UserName")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.name("Password")).isDisplayed());
    }

    @Test(groups = "locators")
    public void locateLoginPageByClassAndTagName() {
        Assert.assertTrue(driver.findElement(By.className("login-page")).isDisplayed());
        Assert.assertTrue(driver.findElements(By.tagName("input")).size() >= 3);
    }

    @Test(groups = "locators")
    public void locateForgotPasswordByLinkText() {
        Assert.assertTrue(driver.findElement(By.linkText("Forgot Password")).isDisplayed());
    }

    @Test(groups = "locators")
    public void locateForgotPasswordByPartialLinkText() {
        Assert.assertTrue(driver.findElement(By.partialLinkText("Forgot")).isDisplayed());
    }

    @Test(groups = "locators")
    public void locateFieldsByCssSelectorAndXpath() {
        Assert.assertTrue(driver.findElement(By.cssSelector("form#frmLogin input[name='UserName']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='Password']")).isDisplayed());
    }
}
