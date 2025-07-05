package stepDefs;

import com.microsoft.playwright.Page;
import cucumber_context.TestContext;
import enum_detail.EnvironmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import page_object.PageClientID;
import service_manager.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    private static final Logger LOGGER = LoggerFactory.getLogger(Helper.class);
    private final Page driver;
    private final BrowserServiceManager browserServiceManager;
    private final TestContext testContext;
    private final AssertionServiceManager assertionServiceManager;
    private final PageClientID pageClientID;

    public Helper(TestContext context) {
        this.testContext = context;
        this.browserServiceManager = testContext.getBrowserServiceManager();
        this.assertionServiceManager = testContext.getAssertionServiceManager();
        this.driver = browserServiceManager.getBrowserDriver();
        this.pageClientID = new PageClientID(testContext);
    }

    /**
     * Method to subStringBetween
     *
     * @param text Original String
     * @param after Text to appear after
     * @param before Text to appear before
     * @return The substring between 'after' and 'before'
     */
    public static String subStringBetween(String text, String after, String before) {
        Pattern pattern = Pattern.compile("(?<=\\Q" + after + "\\E)(.*?)(?=\\Q" + before + "\\E)");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            return matcher.group(1);
        }

        return ""; // or null based on use case
    }
    /**
     * Method to get the URL based on the provided string.
     *
     * @param url The input that determines which environment URL to return.
     * @return The resolved URL as a string.
     */
    public String getURL(String url) {
        if (url.equalsIgnoreCase("GAPINTAKE-SIT-URL")) {
            url = FileReaderServiceManager.getInstance()
                    .getAppFileReader()
                    .getgapIntakeSitApplicationUrl();
        } else if (url.equalsIgnoreCase("GAPINTAKE-UAT-URL")) {
            url = FileReaderServiceManager.getInstance()
                    .getAppFileReader()
                    .getgapIntakeUatApplicationUrl();
        }

        return url;
    }

        public void addRecordsInForm(String listVal, String userDefinedVal) {
            boolean flag = false;
            String actualVal;

            switch (listVal) {
                case "dropdownclientidstatus":
                    pageClientID.selectDropDownClientIDStatus(userDefinedVal);
                    break;
                case "textclientid":
                    pageClientID.setTextClientID(userDefinedVal);
                    break;
                case "textclientidname":
                    pageClientID.setTextClientIDName(userDefinedVal);
                    break;
                case "textreportingid":
                    pageClientID.setTextReportingID(userDefinedVal);
                    break;
                case "textcomments":
                    pageClientID.setTextComments(userDefinedVal);
                    break;
                default:
                    LOGGER.warn("Unsupported userEnteredOption: {}", listVal);
            }
        }
    public void eventAction(String userAction, String name, String expectedValue) {
        if (userAction.equalsIgnoreCase("addClientID")) {
            pageClientID.clickButtonAddClientID(name);
            String dialogTitle = pageClientID.getTextDialogTitle();
            assertionServiceManager.assertResponse("DialogTitle", dialogTitle, expectedValue);
        } else {
            LOGGER.warn("The user action is not present for: {}", userAction);
        }
    }
    public void eventAction(String userAction, String expectedValue) {
        if (userAction.equalsIgnoreCase("Save")) {
            pageClientID.clickButtonSave();
            String actualVal;
            if (pageClientID.genericErrorList().size() > 0) {
                actualVal = pageClientID.genericErrorList().toString();
                pageClientID.genericErrorList().clear();
            } else if (browserServiceManager.isElementPresent(pageClientID.alertMessage())) {
                actualVal = pageClientID.getTextAlertMessage();
            } else {
                actualVal = "Alert message not displayed, Check the Alert delay in application";
            }
            assertionServiceManager.assertResponse("Alert Message", actualVal, expectedValue);
        } else if (userAction.equalsIgnoreCase("Cancel")) {
            pageClientID.clickButtonCancel();
        } else {
            LOGGER.warn("The user action is not present for: {}", userAction);
        }
    }
    /**
     * Method to get the environment based on the provided string.
     * @return The environment as an EnvironmentType.
     */
    public EnvironmentType getEnvironment() {
        return FileReaderServiceManager.getInstance()
                .getAppFileReader()
                .getEnvironment();
    }

    /**
     * Method to get the incognito mode based on the provided string.
     * @return True if incognito mode is enabled, false otherwise.
     */
    public Boolean getIncognitoMode() {
        return FileReaderServiceManager.getInstance()
                .getAppFileReader()
                .getIncognitoModeOption();
    }
    /**
     * Method to get the email ID based on the provided string.
     *
     * @return The emailID as a string.
     */
    public String getEmail() {
        return FileReaderServiceManager.getInstance()
                .getAppFileReader()
                .getUserEmail();
    }

    /**
     * Method to get the password based on the provided string.
     *
     * @return The password as a string.
     */
    public String getPassword() {
        return FileReaderServiceManager.getInstance()
                .getAppFileReader()
                .getPassword();
    }


    }
