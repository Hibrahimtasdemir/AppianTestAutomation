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

import org.openqa.selenium.WebDriver;
import com.appiancorp.ps.automatedtest.fixture.SitesFixture;

public class WebDriverMgr {

    // ThreadLocal variables for WebDriver and SitesFixture to ensure thread safety
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<SitesFixture> fixture = new ThreadLocal<>();

    /**
     * Retrieves the WebDriver instance associated with the current thread.
     *
     * @return the WebDriver instance, or null if none is set
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Retrieves the SitesFixture instance associated with the current thread.
     *
     * @return the SitesFixture instance, or null if none is set
     */
    public static SitesFixture getFixture() {
        return fixture.get();
    }

    /**
     * Sets the WebDriver instance for the current thread.
     *
     * @param driverParam the WebDriver instance to be associated with the current thread
     */
    public static void setWebDriver(WebDriver driverParam) {
        driver.set(driverParam);
    }

    /**
     * Sets the SitesFixture instance for the current thread.
     *
     * @param fixtureParam the SitesFixture instance to be associated with the current thread
     */
    public static void setFixture(SitesFixture fixtureParam) {
        fixture.set(fixtureParam);
    }

    /**
     * Removes the WebDriver instance associated with the current thread, if any.
     */
    public static void removeWebDriver() {
        driver.remove();
    }

    /**
     * Removes the SitesFixture instance associated with the current thread, if any.
     */
    public static void removeFixture() {
        fixture.remove();
    }
}
