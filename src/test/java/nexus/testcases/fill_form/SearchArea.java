package nexus.testcases.fill_form;

import com.thedeanda.lorem.LoremIpsum;
import nexus.base.InstanceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import snap.utilities.WebDriverMgr;

import java.net.MalformedURLException;

public class SearchArea extends InstanceManager {
    private static final Logger LOG = LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClass());

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    void beforeClass(@Optional("browser") String browser) throws MalformedURLException {
        setDriver(browser);
        WebDriverMgr.setWebDriver(driver);
    }
    @Test(priority = 1, description = "Entering the name for Search Area", groups = {"SearchArea"})
    public void TC_SearchArea(){
        System.out.println("Test initialized with web-driver");
        loginPage.performAppianLogin();
        loginPage.verifySuccessfulLogin(homePage);
        homePage.validateLogin();
        common.clickOnSitePage("DEMO QA");
        common.EnterSearchText("Selim");

    }
    @AfterMethod(alwaysRun = true)
    public void endTest() {
        common.afterTest();
    }
}
