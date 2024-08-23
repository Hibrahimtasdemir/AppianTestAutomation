package nexus.pages;

import com.appiancorp.ps.automatedtest.fixture.SitesFixture;
import com.aventstack.extentreports.Status;

import snap.listeners.ReportListeners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    public WebDriver driver;
    public SitesFixture fixture;

    @FindBy(xpath = "//*[text()='What do you need help with?']")
    WebElement homeText;

    @FindBy(xpath = "//*[text()='Id']/ancestor::th[1]")
    WebElement idColumn;

    @FindBy(xpath = "//*[text()='Refresh']/ancestor::button[1]")
    WebElement refreshTable;
    

    public HomePage(WebDriver driver, SitesFixture fixture) {
        this.driver = driver;
        this.fixture = fixture;
        PageFactory.initElements(driver, this);
    }

    public void validateLogin(){
        if(homeText.isDisplayed()){
            ReportListeners.logStep("Login successful, HomePage Text verified.");
        }else{
            ReportListeners.logStep(Status.FAIL,"Login unsuccessful, HomePage Text not verified.");
        }
    }

    public void sortById(){
        idColumn.click();
        ReportListeners.logStep("Sorted by ID");
    }

    public void refreshTable(){
        refreshTable.click();
        ReportListeners.logStep("Refresh Table Icon Clicked");
    }
}
  