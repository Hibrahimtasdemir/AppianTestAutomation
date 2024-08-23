package nexus.testcases.fill_form;

import nexus.base.InstanceManager;
import snap.utilities.WebDriverMgr;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import com.thedeanda.lorem.LoremIpsum;

import java.net.MalformedURLException;

public class FillFormTest extends InstanceManager {

    private static final Logger LOG = LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClass());

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    void beforeClass(@Optional("browser") String browser) throws MalformedURLException {
        setDriver(browser);
        WebDriverMgr.setWebDriver(driver);
    }

    @Test(priority = 2, description = "Fill Form Validation", groups = {"fillform"})
    public void TC_FillFormTest(){
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
        common.populateFieldNameWithValue("Interests", "Manual Testing");
        common.populateFieldNameWithValue("Languages", "English");
        common.populateFieldNameWithValue("Skill", "API");
        common.populateFieldNameWithValue("Country", "India");
        common.clickOnButton("SUBMIT");
        homePage.refreshTable();
        homePage.sortById();
        common.verifyTextIsPresent(firstName);
        common.verifyTextIsPresent(lastName);
    }
    
    @AfterMethod(alwaysRun = true)
    public void endTest() {
        common.afterTest();
    }
}