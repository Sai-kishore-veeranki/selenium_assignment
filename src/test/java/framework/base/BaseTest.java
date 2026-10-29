package framework.base;

import framework.pages.LoginPage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected WebDriver driver;
    protected String baseUrl;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        baseUrl = System.getProperty(
                "magnus.baseUrl",
                System.getenv().getOrDefault("MAGNUS_BASE_URL", "https://magnus.jalatechnologies.com"))
                .replaceAll("/+$", "");
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--window-size=1440,1000");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get(baseUrl);
    }

    protected void login() {
        Properties credentials = loadCredentials();
        String username = credential("magnus.username", "MAGNUS_USERNAME", credentials);
        String password = credential("magnus.password", "MAGNUS_PASSWORD", credentials);
        if (username == null || password == null) {
            throw new SkipException(
                    "Set magnus.username/magnus.password, MAGNUS_USERNAME/MAGNUS_PASSWORD, "
                            + "or add them to test-config.properties to run authenticated tests.");
        }
        new LoginPage(driver).login(username, password);
    }

    protected void openAuthenticatedPage(String path) {
        login();
        driver.get(baseUrl + path);
    }

    private Properties loadCredentials() {
        Path config = Paths.get("test-config.properties");
        Properties credentials = new Properties();
        if (!Files.exists(config)) {
            return credentials;
        }

        try (InputStream input = Files.newInputStream(config)) {
            credentials.load(input);
            return credentials;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read test-config.properties.", exception);
        }
    }

    private String credential(String property, String environmentVariable, Properties credentials) {
        String value = System.getProperty(property);
        if (value == null || value.isBlank()) {
            value = System.getenv(environmentVariable);
        }
        if (value == null || value.isBlank()) {
            value = credentials.getProperty(property);
        }
        return value == null || value.isBlank() ? null : value;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver == null) {
            return;
        }

        if (!result.isSuccess() && driver instanceof TakesScreenshot) {
            saveFailureScreenshot(result.getName());
        }
        driver.quit();
        driver = null;
    }

    private void saveFailureScreenshot(String testName) {
        try {
            Path directory = Paths.get("target", "screenshots");
            Files.createDirectories(directory);
            Path screenshot = directory.resolve(testName + "-" + System.currentTimeMillis() + ".png");
            Files.write(screenshot, ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        } catch (IOException exception) {
            throw new IllegalStateException("Could not save failure screenshot.", exception);
        }
    }
}
