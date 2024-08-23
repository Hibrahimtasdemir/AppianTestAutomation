/*
 * Copyright (c) 2024 Arjit Yadav
 *
 * Permission is hereby granted to use, copy, modify, and distribute this code for any purpose, with or without
 * modifications, subject to the following conditions:
 *
 * 1. This notice shall be included in all copies or substantial portions of the code.
 * 2. Suggestions and improvements are welcome and can be submitted via pull requests or issues on the GitHub repository.
 *
 * THE CODE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT,
 * OR OTHERWISE, ARISING FROM, OUT OF, OR IN CONNECTION WITH THE CODE OR THE USE OR OTHER DEALINGS IN THE CODE.
 */

package snap.utilities;

import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import org.apache.commons.imaging.Imaging;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.appiancorp.ps.automatedtest.fixture.SitesFixture;
import com.aventstack.extentreports.Status;
import snap.constants.CommonConstants;
import snap.listeners.ReportListeners;
import java.io.File;
import java.io.IOException;

public class CommonMethods {

	// WebDriver and fixture instance variables
	public WebDriver driver;
	public SitesFixture fixture;

	// Constructor
	public CommonMethods(WebDriver driver, SitesFixture fixture) {
		this.driver = driver;
		this.fixture = fixture;
	}

	/* 
	 * Group 1: Screenshot Methods 
	 * Methods related to capturing and handling screenshots.
	 */

	/**
	 * Captures a screenshot of the current page.
	 *
	 * @param methodName the name of the method where the screenshot is captured
	 */
	public void capturePageScreenshot(String methodName) {
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		System.out.println("Screenshot captured");
		File destination = new File(CommonConstants.getScreenshotFilePath() + "Screenshot_" + methodName + "_"
				+ CommonConstants.getDateTimeStamp() + ".png");
		try {
			FileHandler.copy(source, destination);
			System.out.println("Screenshot copied to " + CommonConstants.getScreenshotFilePath());
		} catch (IOException e) {
			System.out.println("Unable to copy screenshot file to '" + CommonConstants.getScreenshotFilePath()
					+ "'. Error occured :" + e);
		}
	}

	/* 
	 * Group 2: Time and Date Methods 
	 * Methods for generating timestamps and handling date-related functionalities.
	 */

	/**
	 * Returns the current timestamp in the format 'dd MMM yyyy hh:mm:ss a'.
	 *
	 * @return the formatted current date and time
	 */
	public String getTimeStamp() {
		DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");
		Date currentDate = new Date();
		return outputFormat.format(currentDate);
	}

	/**
	 * Returns the name of the method that invoked this method.
	 *
	 * @return the method name
	 */
	public String getMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	/* 
	 * Group 3: Element Interaction Methods 
	 * Methods for interacting with web elements such as buttons, links, text, and fields.
	 */

	/**
	 * Clicks on a button identified by its text.
	 *
	 * @param strButton the button text
	 */
	public void clickOnButton(String strButton) {
		try {
			fixture.waitForProgressBar();
			Assert.assertTrue(fixture.verifyButtonIsEnabled(strButton), "Button Enabled");
			fixture.clickOnButton(strButton);
			fixture.waitForProgressBar();
			ReportListeners.logStep("Clicked on Button: " + strButton);
		} catch (Exception e) {
			ReportListeners.logStep(Status.FAIL, "An error occurred while clicking on the button '" + strButton + "' : " + e);
			throw e;
		}
	}

	/**
	 * Clicks on a card identified by its link text.
	 *
	 * @param strCardLink the card link text
	 */
	public void clickOnCard(String strCardLink) {
		try {
			fixture.clickOnCard(strCardLink);
			ReportListeners.logStep("Clicked on Card: " + strCardLink);
		} catch (Exception e) {
			ReportListeners.logStep("An error occurred while clicking on the card '" + strCardLink + "' : " + e);
			throw e;
		}
	}

	/**
	 * Clicks on a Site Page identified by its text.
	 *
	 * @param strSitePageName the site page text
	 */
	public void clickOnSitePage(String strSitePageName) {
		try {
			fixture.clickOnSitePage(strSitePageName);
			ReportListeners.logStep("Clicked on Site Page: " + strSitePageName);
		} catch (Exception e) {
			ReportListeners.logStep("An error occurred while clicking on the site page '" + strSitePageName + "' : " + e);
			throw e;
		}
	}

	/**
	 * Populates a field identified by its name with a given value.
	 *
	 * @param fieldName the name of the field
	 * @param value the value to populate
	 */
	public void populateFieldNameWithValue(String fieldName, String value) {
		try {
			fixture.populateFieldWithValue(fieldName, value);
			ReportListeners.logStep("Populated '" + fieldName + "' with '" + value + "'");
		} catch (Exception e) {
			ReportListeners.logStep("An error occurred while populating '" + fieldName + "' field: " + e);
			throw e;
		}
	}

	/**
	 * Populates a field identified by its index and name with a given value.
	 *
	 * @param index the index of the field
	 * @param fieldName the name of the field
	 * @param value the value to populate
	 */
	public void populateFieldIndexWithValue(Integer index, String fieldName, String value) {
		try {
			fixture.populateFieldWithValue("[" + index + "]", value);
			ReportListeners.logStep("Populated *" + fieldName + "' with '" + value + "'");
		} catch (Exception e) {
			ReportListeners.logStep("An error occurred while populating '" + fieldName + "' field: " + e);
			throw e;
		}
	}

	/**
	 * Clicks on a link identified by its text.
	 *
	 * @param linkText the link text
	 */
	public void clickOnLinkText(String linkText) {
		fixture.clickOnLink(linkText);
		fixture.waitForProgressBar();
		ReportListeners.logStep("Clicked on Link Text: " + linkText);
	}

	/**
	 * Clicks on a text element.
	 *
	 * @param strText the text to click on
	 */
	public void clickOnText(String strText) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			String xpath = String.format("//*[text()='%s']", strText);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			element.click();
			ReportListeners.logStep("Clicked on Text: " + strText);
		} catch (Exception e) {
			ReportListeners.logStep("Clicked on Text *" + strText + "*: " + e);
			throw e;
		}
	}

	/**
	 * Clicks on a link containing a partial text match.
	 *
	 * @param strLinkText the partial link text
	 */
	public void clickOnPartialLinkText(String strLinkText) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			String xpath = String.format("//a[contains(text(),'%s')]", strLinkText);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			element.click();
			ReportListeners.logStep("Clicked on Text: " + strLinkText);
		} catch (Exception e) {
			ReportListeners.logStep("Clicked on Text *" + strLinkText + "*: " + e);
			throw e;
		}
	}

	/**
	 * Clicks on a text element containing a partial text match.
	 *
	 * @param strText the partial text to click on
	 */
	public void clickOnPartialText(String strText) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			String xpath = String.format("//*[contains(text(),'%s')]", strText);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			element.click();
			ReportListeners.logStep("Clicked on Text: " + strText);
		} catch (Exception e) {
			ReportListeners.logStep("Clicked on Text *" + strText + "*: " + e);
			throw e;
		}
	}

	/**
	 * Clicks on a checkbox option identified by its text.
	 *
	 * @param strCheckboxOption the checkbox option text
	 */
	public void clickOnCheckboxOption(String strCheckboxOption) {
		try {
			fixture.clickOnCheckboxOption(strCheckboxOption);
			fixture.waitForProgressBar();
			ReportListeners.logStep("Clicked on Checkbox: " + strCheckboxOption);
		} catch (Exception e) {
			ReportListeners
					.logStep("An error occurred while clicking on the checkbox '" + strCheckboxOption + "' : " + e);
			throw e;
		}
	}

	/**
	 * Populates a field identified by its placeholder with a given value.
	 *
	 * @param placeholder the placeholder text
	 * @param value the value to populate
	 */
	public void populateFieldValueWithPlaceholder(String placeholder, String value) {
		String[] inputValue = { value };
		fixture.populateFieldWithPlaceholderWith(placeholder, inputValue);
	}

	/**
	 * Populates a filter identified by its field name with a given value.
	 *
	 * @param fieldName the filter field name
	 * @param value the value to populate
	 */
	public void populateFilterWithValue(String fieldName, String value) {
		try {
			fixture.populateRecordTypeUserFilterWith(fieldName, value);
			fixture.waitForProgressBar();
				ReportListeners.logStep("Populated filter '" + fieldName + "' with '" + value + "'");
			} catch (Exception e) {
			ReportListeners.logStep("An error occurred while populating '" + fieldName + "' filter: " + e);
		}
	}

	/**
	 * Fetches the value from a field identified by its name.
	 *
	 * @param fieldName the field name
	 * @return the field value
	 */
	public String getFieldNameValue(String fieldName) {
		String value = null;
		try {
			value = fixture.getFieldValue(fieldName);
			ReportListeners.logStep("Fetched *" + fieldName + "* value: " + value);
		} catch (Exception e) {
			ReportListeners.logStep("Failed to get value for field *" + fieldName + "*: " + e);
			throw e;
		}
		return value;
	}

	/**
	 * Fetches the value from a field identified by its index and name.
	 *
	 * @param index the field index
	 * @param fieldName the field name
	 * @return the field value
	 */
	public String getFieldIndexValue(Integer index, String fieldName) {
		String value = null;
		try {
			value = fixture.getFieldValue("[" + index + "]");
			ReportListeners.logStep("Fetched *" + fieldName + "* value: " + value);
		} catch (Exception e) {
			ReportListeners.logStep("Failed to get value for field *" + fieldName + "*: " + e);
			throw e;
		}
		return value;
	}

	/* 
	 * Group 4: Verification Methods 
	 * Methods for verifying the presence of text or elements on the page.
	 */

	/**
	 * Verifies if a text is present on the page.
	 *
	 * @param text the text to verify
	 * @return true if the text is present, false otherwise
	 */
	public Boolean verifyTextIsPresent(String text) {
		boolean result = false;
		ReportListeners.logScreenshotStep(driver, "verification screenshot '"+text+"' is present or not!");
		result = fixture.verifyTextIsPresent(text);
		return result;
	}

	/**
	 * Verifies if a text is present on the page using a specified locator type.
	 *
	 * @param text the text to verify
	 * @param type the locator type
	 * @return true if the text is present, false otherwise
	 */
	public Boolean verifyTextIsPresent(String text, String type) {
		boolean result = false;
		ReportListeners.logScreenshotStep(driver, "verification screenshot '"+text+"' is present or not!");
		result = driver.findElement(By.xpath("//a//*[text()='"+text+"']")).isDisplayed();
		return result;
	}

	/**
	 * Verifies if an element is displayed on the page.
	 *
	 * @param locator the By locator of the element
	 * @return true if the element is displayed, false otherwise
	 */
	public boolean isElementDisplayed(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			return element.isDisplayed();
		} catch (Exception e) {
			System.out.println("No element present");
			return false;
		}
	}

	/* 
	 * Group 5: Window and Frame Handling Methods 
	 * Methods for handling browser windows, frames, and alerts.
	 */

	/**
	 * Gets the set of all open window handles.
	 *
	 * @return the set of window handles
	 */
	public Set<String> getTotalWindows() {
		Set<String> allWindows = driver.getWindowHandles();
		return allWindows;
	}

	/**
	 * Gets the current window handle.
	 *
	 * @return the current window handle
	 */
	public String getCurrentWindow() {
		return driver.getWindowHandle();
	}

	/**
	 * Switches to the child window.
	 */
	public void switchToChildWindow() {
		String mainWindowHandle = driver.getWindowHandle();
		Set<String> allWindowHandles = driver.getWindowHandles();
		Iterator<String> windowIterator = allWindowHandles.iterator();
		while (windowIterator.hasNext()) {
			String child_window = windowIterator.next();
			if (!mainWindowHandle.equals(child_window)) {
				driver.switchTo().window(child_window);
				System.out.println(driver.switchTo().window(child_window).getTitle());
			}
		}
		fixture.waitForProgressBar();
	}

	/**
	 * Switches to the main window.
	 *
	 * @param windowName the name of the main window
	 */
	public void switchToMainWindow(String windowName) {
		driver.switchTo().window(windowName);
	}

	/**
	 * Switches to a frame identified by its ID or name.
	 *
	 * @param idOrName the ID or name of the frame
	 */
	public void switchToFrameWithIdOrName(String idOrName) {
		driver.switchTo().frame(idOrName);
	}

	/**
	 * Switches back to the default content from a frame.
	 */
	public void switchToDefault() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Handles the scenario where an alert is present.
	 *
	 * @return true if an alert is present, false otherwise
	 */
	public boolean isAlertPresent() {
		try {
			System.out.println("Alert is present.");
			return (driver.switchTo().alert() != null);
		} catch (NoAlertPresentException e) {
			System.out.println("Alert not found. Exception occured : " + e);
			return false;
		}
	}

	/**
	 * Gets the alert popup.
	 *
	 * @return the Alert object, or null if no alert is present
	 */
	public Alert getAlert() {
		try {
			System.out.println("Switching to alert.");
			return driver.switchTo().alert();
		} catch (NoAlertPresentException e) {
			System.out.println("Unable to switch to alert. Exception occured : " + e);
			return null;
		}
	}

	/**
	 * Gets the text from the alert popup.
	 *
	 * @return the alert text, or an empty string if no alert is present
	 */
	public String getAlertText() {
		try {
			String alertText = driver.switchTo().alert().getText();
			ReportListeners.logStep("Text on the Alert popup is : " + alertText);
			System.out.println("Text on the Alert popup is : " + alertText);
			return alertText;
		} catch (NoAlertPresentException e) {
			ReportListeners.logStep("Unable to get Alert text. Exception occured : " + e);
			System.out.println("Unable to get Alert text. Exception occured : " + e);
			return "";
		}
	}

	/**
	 * Accepts the alert popup.
	 */
	public void acceptAlert() {
		try {
			driver.switchTo().alert().accept();
			System.out.println("Alert accepted.");
		} catch (NoAlertPresentException e) {
			System.out.println("Unable to accept Alert. Exception occured : " + e);
		}
	}

	/* 
	 * Group 6: Image Processing Methods 
	 * Methods for reading and comparing images.
	 */

	/**
	 * Reads an image from the given file path using Apache Commons Imaging.
	 *
	 * @param path the file path
	 * @return the BufferedImage object, or null if an error occurs
	 */
	public BufferedImage readImageWithApacheCommons(String path) {
		try {
			File file = new File(path);
			return Imaging.getBufferedImage(file);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
	}

	/**
	 * Compares two images to determine if they are identical.
	 *
	 * @param img1 the first image
	 * @param img2 the second image
	 * @return true if the images are identical, false otherwise
	 */
	public boolean compareImages(BufferedImage img1, BufferedImage img2) {
		if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
			return false;
		}

		for (int y = 0; y < img1.getHeight(); y++) {
			for (int x = 0; x < img1.getWidth(); x++) {
				if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
					return false;
				}
			}
		}
		return true;
	}

	/* 
	 * Group 7: Other Utility Methods 
	 * Miscellaneous methods that don't fit into the other groups.
	 */

	/**
	 * Logs out the user and quits the WebDriver.
	 */
	public void afterTest() {
		try {
			logout();
			ReportListeners.logStep("Logout Successful");
		} catch (Exception e) {
			System.out.println("Logout failed: " + e);
		}
		WebDriverMgr.getDriver().quit();
	}

	/**
	 * Logs out the user.
	 */
	public void logout() {
		System.out.println("Starting Log out process");
		try {
			driver.navigate().refresh();
			if (isAlertPresent()) {
				acceptAlert();
			}
			fixture.waitForProgressBar();
			fixture.logout();
		} catch (Exception e) {
			System.out.println("Error while logging out: " + e);
			throw e;
		}
	}
}
