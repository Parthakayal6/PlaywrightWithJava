package page_object;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import cucumber_context.TestContext;
import service_manager.BrowserServiceManager;

public class PageGeneric {

    private final BrowserServiceManager browserServiceManager;
    private final TestContext testContext;
    private Page browserDriver;
    private final String appGenericPage = "app.genericPage.";

    /**
     * Constructor for PageGeneric.
     *
     * @param context The test context containing shared objects.
     */
    public PageGeneric(TestContext context) {
        super();
        this.testContext = context;
        this.browserServiceManager = testContext.getBrowserServiceManager();
        this.setDriver();
    }
    /**
     * Sets the WebDriver instance from the BrowserServiceManager.
     */
    public void setDriver() {
        this.browserDriver = browserServiceManager.getBrowserDriver();
    }

    private Locator textUserEmail() {
        String actualUserDefinedKey = appGenericPage + "textUserEmail.xpath";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }

    private Locator textPassword() {
        String actualUserDefinedKey = appGenericPage + "textPassword.xpath";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }

    /**
     * Returns the Locator for the generic save button.
     * @return Locator for the generic save button.
     */
    private Locator buttonGenericSave() {
        String actualUserDefinedKey = appGenericPage + "buttonGenericSave.xpath";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }
    /**
     * Sets the user email in the text field.
     *
     * @param userEmail The user email to set.
     */
    public void setTextUserEmail(String userEmail) {
        browserServiceManager.setText(textUserEmail(), userEmail);
    }

    /**
     * Sets the password in the text field.
     *
     * @param password The password to set.
     */
    public void setTextPassword(String password) {
        browserServiceManager.setText(textPassword(), password);
    }

    /**
     * Clicks the generic save button.
     */
    public void clickButtonGenericSave() {
        browserServiceManager.click(buttonGenericSave());
    }

    /**
     * Checks if the user email field is visible on the page.
     *
     * @return true if the user email field is visible, false otherwise.
     */
    public boolean isVisibleTextUserID() {
        return browserServiceManager.isElementPresent(textUserEmail());
    }

    /**
     * Method to check Change Button is present on the page.
     *
     * @return True if the Expected text is present, false otherwise.
     */

    public boolean isVisibleUserPassword() {
        browserServiceManager.isElementPresent(textPassword());
        return false;
    }

}
