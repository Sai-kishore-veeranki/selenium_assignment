package framework.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeePage {
    private final WebDriver driver;

    public EmployeePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openCreatePage(String baseUrl) {
        driver.get(baseUrl + "/Employee/Create");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("FirstName")));
    }

    public WebElement firstName() {
        return driver.findElement(By.id("FirstName"));
    }

    public WebElement lastName() {
        return driver.findElement(By.id("LastName"));
    }

    public WebElement email() {
        return driver.findElement(By.id("EmailId"));
    }

    public WebElement mobileNumber() {
        return driver.findElement(By.id("MobileNo"));
    }

    public WebElement dateOfBirth() {
        return driver.findElement(By.id("DOB"));
    }

    public WebElement address() {
        return driver.findElement(By.id("Address"));
    }

    public List<WebElement> genderOptions() {
        return driver.findElements(By.name("rdbGender"));
    }

    public List<WebElement> skillOptions() {
        return driver.findElements(By.cssSelector("input.chkSkill"));
    }

    public Select countries() {
        return new Select(driver.findElement(By.id("CountryId")));
    }

    public Select cities() {
        return new Select(driver.findElement(By.id("CityId")));
    }

    public WebElement saveButton() {
        return driver.findElement(By.xpath("//button[normalize-space()='Save']"));
    }

    public WebElement cancelButton() {
        return driver.findElement(By.linkText("Cancel"));
    }
}
