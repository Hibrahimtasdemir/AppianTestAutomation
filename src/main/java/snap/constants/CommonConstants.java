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

package snap.constants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonConstants {

    // Timeout settings
    private static final int IMPLICIT_WAIT_TIME = 10;
    private static final int PAGE_LOAD_TIMEOUT = 10;

    // Timestamp for filenames
    private static final String DATE_TIME_STAMP = new SimpleDateFormat("ddMMyyyy_hhmmss").format(new Date());

    // Directory paths
    public static final String PROJECT_DIRECTORY = System.getProperty("user.dir");
    private static final String RESOURCES_FILE_PATH = PROJECT_DIRECTORY + "\\src\\test\\resources";
    private static final String CONFIG_FILE_PATH = RESOURCES_FILE_PATH + "\\properties\\config.properties";
    private static final String DATA_FILE_PATH = RESOURCES_FILE_PATH + "\\properties\\testData.properties";
    private static final String CURRENT_DATA_FILE_PATH = RESOURCES_FILE_PATH + "\\properties\\currentData.properties";
    private static final String LOG4J_FILE_PATH = RESOURCES_FILE_PATH + "\\properties\\log4j.properties";
    private static final String SCREENSHOT_FILE_PATH = PROJECT_DIRECTORY + "\\Screenshots\\";
    private static final String EXTENT_REPORT_FILE_PATH = PROJECT_DIRECTORY + "\\Reports\\";
    private static final String APP_TEST_DATA_PATH = RESOURCES_FILE_PATH + "\\testdata";
    private static final String SCREENSHOT_FILE_NAME = "Screenshot_" + DATE_TIME_STAMP + ".png";

    /**
     * Returns the file path for storing screenshots.
     *
     * @return the screenshot file path
     */
    public static String getScreenshotFilePath() {
        return SCREENSHOT_FILE_PATH;
    }

    /**
     * Returns the file path for storing Extent reports.
     *
     * @return the Extent report file path
     */
    public static String getExtentReportFilePath() {
        return EXTENT_REPORT_FILE_PATH;
    }

    /**
     * Returns the file path for accessing the test data properties file.
     *
     * @return the test data file path
     */
    public static String getDataFilePath() {
        return DATA_FILE_PATH;
    }

    /**
     * Returns the file path for accessing the current data properties file.
     *
     * @return the current data file path
     */
    public static String getCurrentDataFilePath() {
        return CURRENT_DATA_FILE_PATH;
    }

    /**
     * Returns the file path for accessing the configuration properties file.
     *
     * @return the config file path
     */
    public static String getConfigFilePath() {
        return CONFIG_FILE_PATH;
    }

    /**
     * Returns a timestamp string used for file names.
     *
     * @return the current date and time stamp
     */
    public static String getDateTimeStamp() {
        return DATE_TIME_STAMP;
    }

    /**
     * Returns the file name for a screenshot, including a timestamp.
     *
     * @return the screenshot file name
     */
    public static String getScreenshotFileName() {
        return SCREENSHOT_FILE_NAME;
    }

    /**
     * Returns the implicit wait time for WebDriver.
     *
     * @return the implicit wait time in seconds
     */
    public static int getImplicitWaitTime() {
        return IMPLICIT_WAIT_TIME;
    }

    /**
     * Returns the page load timeout for WebDriver.
     *
     * @return the page load timeout in seconds
     */
    public static int getPageLoadTimeout() {
        return PAGE_LOAD_TIMEOUT;
    }

    /**
     * Returns the file path for accessing the Log4j properties file.
     *
     * @return the Log4j file path
     */
    public static String getLog4jFilePath() {
        return LOG4J_FILE_PATH;
    }

    /**
     * Returns the file path for accessing application test data.
     *
     * @return the application test data path
     */
    public static String getAppTestDataPath() {
        return APP_TEST_DATA_PATH;
    }
}
