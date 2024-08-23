package nexus.base;

import nexus.pages.HomePage;
import nexus.pages.LoginPage;
import snap.listeners.ReportListeners;
import snap.utilities.CommonMethods;
import snap.utilities.ConfigReader;
import snap.utilities.FolderOperations;
import snap.utilities.WebDriverMgr;
import com.appiancorp.ps.automatedtest.fixture.SitesFixture;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeSuite;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * The InstanceManager class is responsible for managing the WebDriver instance,
 * initializing page objects, and setting up the testing environment.
 * It also provides methods to start and end tests, and log test steps.
 */
public class InstanceManager {

    // Logger instance for logging information and debugging
    private static final Logger LOG = LogManager.getLogger(InstanceManager.class);

    // Instance variables for WebDriver, SitesFixture, and Page Objects
    public WebDriver driver;
    public SitesFixture fixture;
    public CommonMethods common;
    public LoginPage loginPage;
    public HomePage homePage;
    public static boolean isLoggedIn;

    /**
     * Prepares the environment before any test suite runs.
     * This method manages the folders for reports and screenshots, and kills
     * existing instances of Chrome or Edge based on the browser being used.
     *
     * @throws Exception if any issue occurs during the setup process
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeEverything() throws Exception {
        // Manage folders for reports and screenshots
        FolderOperations.manageFolder(".\\Reports");
        FolderOperations.manageFolder(".\\Screenshots");

        // Kill browser instances based on the browser type
        String testBrowser = ConfigReader.getValue("test_browser").toLowerCase();
        if (testBrowser.equals("chrome")) {
            System.out.println("Starting Chrome instances killing");
            killChromeInstances();
            Thread.sleep(5000);
            System.out.println("Chrome instances killed");
        } else if (testBrowser.equals("edge")) {
            System.out.println("Starting Edge instances killing");
            killEdgeInstances();
            Thread.sleep(5000);
            System.out.println("Edge instances killed");
        }
    }

    /**
     * Sets up the WebDriver instance based on the specified browser.
     * It also initializes the SitesFixture with configurations from the config file.
     *
     * @param testBrowser the name of the browser to use (e.g., "chrome")
     * @throws MalformedURLException if the URL for the RemoteWebDriver is malformed
     */
    public void setDriver(String testBrowser) throws MalformedURLException {
        // Initialize the SitesFixture instance
        fixture = new SitesFixture();
        fixture.setTimeoutSecondsTo(Integer.parseInt(ConfigReader.getValue("appian_timeout")));
        fixture.setAppianVersionTo(ConfigReader.getValue("appian_version"));
        fixture.setAppianLocaleTo(ConfigReader.getValue("appian_locale"));

        switch (testBrowser.toLowerCase()) {
            case "chrome": {
                ChromeOptions chromeOptions = new ChromeOptions();
                if (ConfigReader.getValue("headless").equalsIgnoreCase("true")) {
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("window-size=1920,1080");
                } else {
                    chromeOptions.addArguments("start-maximized");
                }
                chromeOptions.addArguments("force-device-scale-factor=" + ConfigReader.getValue("browser_zoom"));
                chromeOptions.addArguments("high-dpi-support=" + ConfigReader.getValue("browser_zoom"));
                driver = new ChromeDriver(chromeOptions);
                break;
            }
            default: {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                System.out.println("***** Browser is Chrome *****");
                break;
            }
        }

        // Set WebDriver and SitesFixture in the WebDriverMgr
        WebDriverMgr.setWebDriver(driver);
        WebDriverMgr.setFixture(fixture);
        WebDriverMgr.getFixture().setWebDriver(WebDriverMgr.getDriver());

        // Initialize Page Objects
        common = new CommonMethods(WebDriverMgr.getDriver(), WebDriverMgr.getFixture());
        loginPage = new LoginPage(WebDriverMgr.getDriver(), WebDriverMgr.getFixture());
        homePage = new HomePage(WebDriverMgr.getDriver(), WebDriverMgr.getFixture());

        // Clear cookies
        WebDriverMgr.getDriver().manage().deleteAllCookies();
    }

    /**
     * Starts the Extent Report for a specific test.
     *
     * @param testName the name of the test to start
     */
    public void startTest(String testName) {
        ReportListeners.startTest(testName);
    }

    /**
     * Ends the current Extent Report test.
     */
    public void endTest() {
        ReportListeners.endTest();
    }

    /**
     * Logs a step in the Extent Report.
     *
     * @param stepDescription the description of the step to log
     */
    public void logStep(String stepDescription) {
        ReportListeners.logStep(stepDescription);
    }

    /**
     * Kills all running Chrome instances on the machine.
     */
    private void killChromeInstances() {
        try {
            // Execute command to list all Chrome processes
            Process process = Runtime.getRuntime().exec("tasklist /fi \"imagename eq chrome.exe\"");

            // Read the output of the command
            java.io.InputStream is = process.getInputStream();
            java.util.Scanner scanner = new java.util.Scanner(is).useDelimiter("\\A");
            String output = scanner.hasNext() ? scanner.next() : "";

            // Split the output into lines and kill Chrome processes
            String[] lines = output.split(System.getProperty("line.separator"));
            for (String line : lines) {
                if (line.contains("chrome.exe")) {
                    String[] parts = line.trim().split("\\s+");
                    String pid = parts[1];
                    Process killProcess = Runtime.getRuntime().exec("taskkill /F /PID " + pid);
                    killProcess.waitFor();
                }
            }

            System.out.println("All Chrome instances killed successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Kills all running Microsoft Edge instances on the machine.
     */
    private void killEdgeInstances() {
        try {
            // Execute command to list all Microsoft Edge processes
            Process process = Runtime.getRuntime().exec("tasklist /fi \"imagename eq msedge.exe\"");

            // Read the output of the command
            java.io.InputStream is = process.getInputStream();
            java.util.Scanner scanner = new java.util.Scanner(is).useDelimiter("\\A");
            String output = scanner.hasNext() ? scanner.next() : "";

            // Split the output into lines and kill Edge processes
            String[] lines = output.split(System.getProperty("line.separator"));
            for (String line : lines) {
                if (line.contains("msedge.exe")) {
                    String[] parts = line.trim().split("\\s+");
                    String pid = parts[1];
                    Process killProcess = Runtime.getRuntime().exec("taskkill /F /PID " + pid);
                    killProcess.waitFor();
                }
            }

            System.out.println("All Microsoft Edge instances killed successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
