package cucumber_context;

import service_manager.*;

public class TestContext {

    private final BrowserServiceManager browserServiceManager;
    private final AssertionServiceManager assertionServiceManager;
    private final ScreenshotServiceManager screenshotServiceManager;
    private final ExcelFileServiceManager excelFileServiceManager;

    public TestContext() {
        browserServiceManager = new BrowserServiceManager();
        assertionServiceManager = new AssertionServiceManager();
        screenshotServiceManager = new ScreenshotServiceManager(browserServiceManager.getBrowserDriver());
        excelFileServiceManager = new ExcelFileServiceManager();
    }

    /**
     * Getter for BrowserServiceManager
     *
     * @return browserServiceManager
     */
    public BrowserServiceManager getBrowserServiceManager() { return browserServiceManager; }

    /**
     * Getter for AssertionServiceManager
     *
     * @return assertionServiceManager
     */
    public AssertionServiceManager getAssertionServiceManager() { return assertionServiceManager; }

    /**
     * Getter for ExcelFileServiceManager
     *
     * @return excelFileServiceManager
     */
    public ExcelFileServiceManager getExcelFileServiceManager() { return excelFileServiceManager; }

    /**
     * Getter for ScreenshotServiceManager
     *
     * @return screenshotServiceManager
     */
    public ScreenshotServiceManager getScreenshotServiceManager() { return screenshotServiceManager; }
}
