package stepDefs;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import cucumber_context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import page_object.PageClientID;
import page_object.PageGeneric;
import service_manager.AssertionServiceManager;
import service_manager.BrowserServiceManager;

import java.util.List;
import java.util.Map;

public class StepDef {
    private static final Logger LOGGER = LoggerFactory.getLogger(StepDef.class);
    private final Page driver;
    private final BrowserServiceManager browserServiceManager;
    private final TestContext testContext;
    private final AssertionServiceManager assertionServiceManager;
    private final Helper helper;
    private final PageClientID pageClientID;
    private final PageGeneric pageGeneric;
    public StepDef(TestContext context) {
        this.testContext = context;
        this.browserServiceManager = testContext.getBrowserServiceManager();
        this.assertionServiceManager = testContext.getAssertionServiceManager();
        this.driver = browserServiceManager.getBrowserDriver();
        this.helper = new Helper(context);
        this.pageClientID = new PageClientID(testContext);
        this.pageGeneric = new PageGeneric(testContext);
    }
    @Given("user navigate to {string} and validate the {string}")
    public void user_navigate_to_and_validate_the(String url, String pageTitle) throws InterruptedException {
        // Resolve actual test URL from environment settings
        url = helper.getURL(url);
        browserServiceManager.navigateToURL(url, 10000);
        PlaywrightAssertions.assertThat(driver).hasTitle(pageTitle);

        // Execute remote-specific flow
        if (helper.getEnvironment().toString().equalsIgnoreCase("REMOTE")) {
            if (pageGeneric.isVisibleTextUserID()) {
                LOGGER.info("User ID is: {}", helper.getEmail());
                pageGeneric.setTextUserEmail(helper.getEmail());
                pageGeneric.clickButtonGenericSave();

                if (pageGeneric.isVisibleUserPassword()) {
                    pageGeneric.setTextPassword(helper.getPassword());
                    pageGeneric.clickButtonGenericSave();
                } else {
                    LOGGER.warn("User Password field not visible after first save.");
                }
            } else {
                LOGGER.warn("User ID field not visible on the page.");
            }
        }
    }


    @Then("user click on {string} and validate the response {string}")
    public void userClickOnAndValidateTheResponse(String link, String expectedVal) {
        helper.eventAction(link, expectedVal);
    }

    @Then("add new record with below details")
    public void user_add_new_record_with_below_details(List<Map<String, String>> datatable) throws InterruptedException {
        for (Map<String, String> row : datatable) {
            String listVal = row.get("Description");
            String userDefinedVal = row.get("UserInputValue");

            if (!userDefinedVal.isEmpty()) {
                helper.addRecordsInForm(listVal, userDefinedVal);
            } else {
                LOGGER.warn("The userDefined value is not present for: {}", listVal);
            }
        }
    }
        @Then("user click on {string} for {string} and validate the response {string}")
        public void userClickOnForAndValidateTheResponse(String userAction, String name, String expectedValue) throws InterruptedException {
            helper.eventAction("addClientID", name, expectedValue);
        }

}
