package page_object;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import cucumber_context.TestContext;
import service_manager.BrowserServiceManager;

import java.util.ArrayList;
import java.util.List;

public class PageClientID {

    private final BrowserServiceManager browserServiceManager;
    private final TestContext testContext;
    private Page browserDriver;

    private final String clientIDPage = "playwrightGapIntake.clientIDPage.";
    private final String appGenericPage = "app.genericPage";

    /**
     * Constructor for PageClientID.
     * @param context The test context containing shared objects.
     */
    public PageClientID(TestContext context) {
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

    /**
     * Returns the Locator for the Add Client ID button.
     */
    private Locator buttonAddClientID(String name) {
        String actualUserDefinedKey = clientIDPage + "buttonAddClientID.xpath";
        return browserServiceManager.findElementByUserInput(actualUserDefinedKey, name);
    }

    /**
     * Returns the Locator for the Client ID Status dropdown.
     */
    private Locator dropDownClientIDStatus() {
        String actualUserDefinedKey = clientIDPage + "dropDownClientIDStatus.xpath";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }

    /**
     * Returns the Locator for the Client ID text field.
     */
    private Locator textClientID() {
        String actualUserDefinedKey = clientIDPage + "textClientID.id";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }
    /**
     * Returns the Locator for the Client ID Name text field.
     */
    private Locator textClientIDName() {
        String actualUserDefinedKey = clientIDPage + "textClientIDName.id";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }

    /**
     * Returns the Locator for the Reporting ID text field.
     */
    private Locator textReportingID() {
        String actualUserDefinedKey = clientIDPage + "textReportingID.id";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }
    /**
     * Returns the Locator for the Comments text field.
     */
    private Locator textComments() {
        String actualUserDefinedKey = clientIDPage + "textComments.id";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }
    /**
     * Returns the Locator for the Save button.
     */
    private Locator buttonSave() {
        String actualUserDefinedKey = clientIDPage + "buttonSave.testid";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }

    /**
     * Returns the Locator for the Cancel button.
     */
    private Locator buttonCancel() {
        String actualUserDefinedKey = clientIDPage + "buttonCancel.testid";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }

    /**
     * Returns the Locator for the dialog title.
     */
    private Locator dialogTitle() {
        String actualUserDefinedKey = clientIDPage + "dialogTitle.xpath";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }
    /**
     * Returns the locator for the alert message.
     * @return Locator for the alert message.
     */
    public Locator alertMessage() {
        String actualUserDefinedKey = clientIDPage + "alertMessage.xpath";
        return browserServiceManager.findElementByLocator(actualUserDefinedKey);
    }

    /**
     * Clicks the Add Client ID button.
     * @param name The name to use for the locator.
     */
    public void clickButtonAddClientID(String name) {
        browserServiceManager.click(buttonAddClientID(name));
    }

    /**
     * Selects a value from the Client ID Status dropdown.
     * @param clientIDStatus The status to select.
     */
    public void selectDropDownClientIDStatus(String clientIDStatus) {
        browserServiceManager.selectValueFromDropDown(dropDownClientIDStatus(), clientIDStatus);
    }

    /**
     * Sets the Client ID text field.
     * @param clientID The client ID to set.
     */
    public void setTextClientID(String clientID) {
        browserServiceManager.setText(textClientID(), clientID);
    }

    /**
     * Sets the Client ID Name text field.
     */
    public void setTextClientIDName(String cClientIDName) {
        browserServiceManager.setText(textClientIDName(), cClientIDName);
    }

    /**
     * Sets the Reporting ID text field.
     */
    public void setTextReportingID(String reportingID) {
        browserServiceManager.setText(textReportingID(), reportingID);
    }

    /**
     * Sets the Comments text field.
     */
    public void setTextComments(String comments) {
        browserServiceManager.setText(textComments(), comments);
    }

    /**
     * Clicks the Save button.
     */
    public void clickButtonSave() {
        browserServiceManager.click(buttonSave());
    }
    /**
     * Clicks the Cancel button.
     */
    public void clickButtonCancel() {
        browserServiceManager.click(buttonCancel());
    }

    /**
     * Gets the page title.
     */
    public String getTitle() {
        return this.browserDriver.title();
    }

    /**
     * Gets the text of the dialog title.
     */
    public String getTextDialogTitle() {
        return browserServiceManager.getText(dialogTitle());
    }

    /**
     * Gets the text of the alert message.
     */
    public String getTextAlertMessage() {
        return browserServiceManager.getText(alertMessage());
    }

    public ArrayList<String> genericErrorList() {
        ArrayList<String> listDetails = new ArrayList<>();
        String actualUserDefinedKey = clientIDPage + "genericErrorList.xpath";
        try {
            List<Locator> list1 = browserServiceManager.findElementsByLocator(actualUserDefinedKey);
            for (Locator obj : list1) {
                listDetails.add(obj.textContent());
            }
        } catch (Exception e) {
            listDetails.add(browserServiceManager.findElementByLocator(actualUserDefinedKey).textContent());
        }
        return listDetails;
    }

}
