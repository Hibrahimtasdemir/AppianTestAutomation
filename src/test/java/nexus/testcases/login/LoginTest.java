package nexus.testcases.login;

import nexus.base.InstanceManager;
import snap.utilities.WebDriverMgr;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import java.net.MalformedURLException;

public class LoginTest extends InstanceManager {

    private static final Logger LOG = LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClass());

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    void beforeClass(@Optional("browser") String browser) throws MalformedURLException {
        setDriver(browser);
        WebDriverMgr.setWebDriver(driver);
    }

    @Test(priority = 1, description = "Login Credentials Validation", groups = {"login"})
    public void TC_LoginTest(){
        System.out.println("Test initialized with web-driver");
        loginPage.performAppianLogin();
        loginPage.verifySuccessfulLogin(homePage);
        homePage.validateLogin();
    }
    
    @AfterMethod(alwaysRun = true)
    public void endTest() {
        common.afterTest();
    }
}