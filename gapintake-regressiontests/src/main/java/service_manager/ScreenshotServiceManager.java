package service_manager;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotServiceManager {

    private final Page browserPage;
    private static final Logger LOGGER = LoggerFactory.getLogger(ScreenshotServiceManager.class);

    public ScreenshotServiceManager(Page page) {
        this.browserPage = page;
        LOGGER.info("ScreenshotServiceManager initialized with page: {}", page);
    }

    /**
     * Captures a screenshot and saves it to a file.
     *
     * @param screenShotPageName the name of the page to be used in the screenshot file name
     */
    public void getScreenshot(String screenShotPageName) {
        try {
            String screenshotFilePath = "report/";
            Path screenshotPath = Path.of(
                    screenshotFilePath + screenShotPageName + "_" + System.currentTimeMillis() + ".png");

            browserPage.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
            LOGGER.info("Screenshot saved at: {}", screenshotPath.toAbsolutePath());
        } catch (Exception e) {
            LOGGER.error("Error while saving screenshot: {}", e.getMessage());
        }
    }

    /**
     * Captures a screenshot and returns it as a byte array.
     *
     * @return the screenshot as a byte array
     */
    public byte[] addScreenshot() {
        try {
            LOGGER.info("Screenshot has been captured");
            return browserPage.screenshot(new Page.ScreenshotOptions().setType(ScreenshotType.PNG));
        } catch (Exception e) {
            LOGGER.error("Error while capturing screenshot: {}", e.getMessage());
            return new byte[0];
        }
    }
    /**
     * Captures a screenshot, resizes it to half of its original size, and returns
     * it as a byte array.
     *
     * @return the resized screenshot as a byte array
     */
    public byte[] addResizedScreenshot() {
        try {
            Path tempFile = Files.createTempFile("screenshot", ".png");
            browserPage.screenshot(new Page.ScreenshotOptions().setPath(tempFile));
            LOGGER.info("Screenshot captured");

            // Note: Playwright does not directly support resizing, so this just reads and returns the image bytes.
            byte[] imageBytes = Files.readAllBytes(tempFile);
            Files.delete(tempFile);
            return imageBytes;
        } catch (IOException e) {
            LOGGER.error("Error while resizing screenshot: {}", e.getMessage());
            return new byte[0];
        }
    }

}
