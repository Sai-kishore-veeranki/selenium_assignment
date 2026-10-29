package assignments;

import framework.base.BaseTest;
import framework.pages.EmployeePage;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebElementOperations extends BaseTest {
    private EmployeePage openEmployeeForm() {
        login();
        EmployeePage employee = new EmployeePage(driver);
        employee.openCreatePage(baseUrl);
        return employee;
    }

    @Test(groups = {"elements", "smoke"})
    public void textBoxOperations() {
        EmployeePage employee = openEmployeeForm();
        employee.firstName().sendKeys("Test User");
        Assert.assertEquals(employee.firstName().getAttribute("value"), "Test User");
        Assert.assertEquals(employee.firstName().getAttribute("placeholder"), "First Name");
        Assert.assertTrue(employee.firstName().isEnabled());
        employee.firstName().clear();
        Assert.assertEquals(employee.firstName().getAttribute("value"), "");
    }

    @Test(groups = "elements")
    public void radioButtonOperations() {
        EmployeePage employee = openEmployeeForm();
        List<WebElement> options = employee.genderOptions();
        Assert.assertEquals(options.size(), 2);
        Assert.assertEquals(options.get(0).getAttribute("value"), "M");
        Assert.assertEquals(options.get(1).getAttribute("value"), "F");
        options.get(0).click();
        Assert.assertTrue(options.get(0).isSelected());
        Assert.assertTrue(options.get(0).isEnabled());
    }

    @Test(groups = "elements")
    public void checkBoxOperations() {
        EmployeePage employee = openEmployeeForm();
        List<WebElement> skills = employee.skillOptions();
        Assert.assertTrue(skills.size() > 0);
        WebElement skill = skills.get(0);
        if (!skill.isSelected()) {
            skill.click();
        }
        Assert.assertTrue(skill.isSelected());
    }

    @Test(groups = "elements")
    public void dropdownOperations() {
        EmployeePage employee = openEmployeeForm();
        Assert.assertTrue(employee.countries().getOptions().size() > 1);
        employee.countries().selectByValue("1");
        Assert.assertNotEquals(employee.countries().getFirstSelectedOption().getText(), "--Select Country--");
        employee.countries().selectByVisibleText("India");
        Assert.assertEquals(employee.countries().getFirstSelectedOption().getText(), "India");
        employee.countries().selectByIndex(1);
        Assert.assertNotEquals(employee.countries().getFirstSelectedOption().getText(), "--Select Country--");
    }

    @Test(groups = "elements")
    public void saveAndCancelButtonsOnEmployeeCreate() {
        EmployeePage employee = openEmployeeForm();
        Assert.assertTrue(employee.saveButton().isEnabled());
        Assert.assertTrue(employee.cancelButton().isDisplayed());
        employee.cancelButton().click();
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/Employee/Search"));
    }
}
