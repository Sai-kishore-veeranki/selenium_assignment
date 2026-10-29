package assignments;

import framework.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MagnusTestNGSuite extends BaseTest {
    private boolean testMethodStarted;

    @BeforeMethod
    public void beforeAssignmentTest() {
        testMethodStarted = true;
    }

    @AfterMethod
    public void afterAssignmentTest() {
        testMethodStarted = false;
    }

    @Test(priority = 1, groups = {"testng", "smoke"})
    public void prerequisiteTest() {
        Assert.assertTrue(testMethodStarted);
    }

    @Test(priority = 2, dependsOnMethods = "prerequisiteTest", groups = "testng")
    public void dependentTest() {
        Assert.assertTrue(testMethodStarted);
    }

    @Test(dataProvider = "sampleValues", priority = 3, groups = "testng")
    public void dataDrivenTest(String expectedText) {
        Assert.assertFalse(expectedText.isBlank());
    }

    @DataProvider(name = "sampleValues")
    public Object[][] sampleValues() {
        return new Object[][] {{"Magnus"}, {"JALA Academy"}};
    }
}
