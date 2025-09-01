package snap.listeners;

import io.qameta.allure.Attachment;
import org.testng.ITestListener;

public class AllureReportListener implements ITestListener {

    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenshot(byte[] pngBytes) {
        return pngBytes; // Allure ek’i olarak döndürülür
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveText(String message) {
        return message;
    }

    @Attachment(value = "{0}", type = "text/html")
    public static String saveHtml(String html) {
        return html;
    }
}
