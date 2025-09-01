package nexus.testcases.fill_form;

import com.thedeanda.lorem.LoremIpsum;
import nexus.base.InstanceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import snap.utilities.WebDriverMgr;

import java.net.MalformedURLException;

public class SavaDraft extends InstanceManager {
    private static final Logger LOG = LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClass());

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    void beforeClass(@Optional("browser") String browser) throws MalformedURLException {
        setDriver(browser);
        WebDriverMgr.setWebDriver(driver);
    }
    @Test(priority = 1, description = "Clicking Save Draft", groups = {"Save Draft"})
    public void TC_SaveDraft() {
        System.out.println("Test initialized with web-driver");
        loginPage.performAppianLogin();
        loginPage.verifySuccessfulLogin(homePage);
        homePage.validateLogin();
        common.clickOnSitePage("DEMO QA");
        common.clickOnCard("Add User Link");
        String firstName = LoremIpsum.getInstance().getFirstName();
        String lastName = LoremIpsum.getInstance().getLastName();
        common.populateFieldNameWithValue("First Name", firstName);
        common.populateFieldNameWithValue("Last Name", lastName);
        common.populateFieldNameWithValue("Address", LoremIpsum.getInstance().getCity());
        common.populateFieldNameWithValue("Email", LoremIpsum.getInstance().getEmail());
        common.populateFieldNameWithValue("Phone", LoremIpsum.getInstance().getPhone());
        common.populateFieldNameWithValue("Gender", "Male");
        common.clickOnText("Save Draft");


    }
    @AfterMethod(alwaysRun = true)
    public void endTest() {
        common.afterTest();
    }
}
