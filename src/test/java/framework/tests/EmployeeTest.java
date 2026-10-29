package framework.tests;

import framework.base.BaseTest;
import framework.pages.EmployeePage;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EmployeeTest extends BaseTest {
    @Test(groups = {"employee", "smoke"})
    public void employeeCreateFormAcceptsTextAndSelection() {
        login();
        EmployeePage employee = new EmployeePage(driver);
        employee.openCreatePage(baseUrl);

        employee.firstName().sendKeys("Selenium");
        employee.lastName().sendKeys("Candidate");

        Assert.assertEquals(employee.firstName().getAttribute("value"), "Selenium");
        Assert.assertEquals(employee.lastName().getAttribute("value"), "Candidate");
        Assert.assertTrue(employee.saveButton().isEnabled());
        Assert.assertEquals(employee.cancelButton().getAttribute("href"), baseUrl + "/Employee/Search");
    }

    @Test(groups = {"employee"})
    public void employeeCreateFormExposesRadioCheckboxAndDropdownControls() {
        login();
        EmployeePage employee = new EmployeePage(driver);
        employee.openCreatePage(baseUrl);

        List<WebElement> genders = employee.genderOptions();
        Assert.assertEquals(genders.size(), 2);
        genders.get(0).click();
        Assert.assertTrue(genders.get(0).isSelected());

        List<WebElement> skills = employee.skillOptions();
        Assert.assertTrue(skills.size() > 0);
        skills.get(0).click();
        Assert.assertTrue(skills.get(0).isSelected());

        Assert.assertTrue(employee.countries().getOptions().size() > 1);
        employee.countries().selectByVisibleText("India");
        Assert.assertEquals(employee.countries().getFirstSelectedOption().getText(), "India");
    }
}
