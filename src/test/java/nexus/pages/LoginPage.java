package nexus.pages;

import snap.listeners.ReportListeners;
import snap.utilities.ConfigReader;
import snap.utilities.DataReader;
import com.appiancorp.ps.automatedtest.fixture.SitesFixture;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

/**
 * The LoginPage class represents the login page of the application.
 * It provides methods to perform login operations and verify successful login.
 */
public class LoginPage {

    private WebDriver driver;
    private SitesFixture fixture;

    @FindBy(id = "un")
    private WebElement username;

    @FindBy(id = "pw")
    private WebElement password;

    @FindBy(xpath = "//*[@value='Sign In']/parent::div")
    private WebElement btnLogin;

    @FindBy(xpath = "//a[@id='forgotPasswordLink']")
    private WebElement forgotPasswordLink;

    /**
     * Constructor to initialize the LoginPage object with WebDriver and SitesFixture.
     *
     * @param driver   the WebDriver instance used to interact with the browser
     * @param fixture  the SitesFixture instance used for Appian-specific actions
     */
    public LoginPage(WebDriver driver, SitesFixture fixture) {
        this.driver = driver;
        this.fixture = fixture;
        PageFactory.initElements(driver, this);
    }

    /**
     * Performs the login operation by navigating to the specified URL and entering
     * the provided username and password.
     *
     * @param strURL      the URL to navigate to
     * @param strUsername the username to enter in the login form
     * @param strPassword the password to enter in the login form
     * @return a HomePage object if login is successful, null otherwise
     */
    public HomePage performLogin(String strURL, String strUsername, String strPassword) {
        try {
            driver.get(strURL);
            username.clear();
            username.sendKeys(strUsername);
            ReportListeners.logStep("Entered text '" + strUsername + "' in the username field.");
            System.out.println("Entered text '" + strUsername + "' in the username field.");

            password.clear();
            password.sendKeys(strPassword);
            ReportListeners.logStep("Entered text '" + strPassword + "' in the password field.");
            System.out.println("Entered text '" + strPassword + "' in the password field.");

            btnLogin.click();
            ReportListeners.logStep("Clicked on Login button.");
            System.out.println("Clicked on Login button.");

            return new HomePage(driver, fixture);
        } catch (Exception e) {
            System.out.println("Error occurred while performing login: " + e);
            return null;
        }
    }

    /**
     * Performs login into an Appian application using credentials from the configuration.
     *
     * @param application the name of the application to log into
     */
    public void performAppianLogin() {
        try {
            String url = ConfigReader.getValue("url");
            String username = DataReader.getValue("USERNAME");
            String password = DataReader.getValue("PASSWORD");
            fixture.loginIntoWithUsernameAndPassword(url, username, password);
            ReportListeners.logStep("Performed Appian login");
        } catch (Exception e) {
            ReportListeners.logStep("Error occurred while performing Appian login: " + e);
            System.out.println("Error occurred while performing login: " + e);
        }
    }

    /**
     * Verifies if the login was successful by checking the title of the HomePage.
     *
     * @param homePage the HomePage object representing the page after login
     */
    public void verifySuccessfulLogin(HomePage homePage) {
        try {
            fixture.waitForProgressBar();
            Assert.assertEquals(driver.getTitle(), DataReader.getValue("HomePageTitle"));
            ReportListeners.logStep("Login successful, HomePage title verified.");
        } catch (Exception e) {
            ReportListeners.logStep("Error occurred during login verification: " + e);
            System.out.println("Error occurred while verifying login: " + e);
            throw e;
        }
    }
}
