package stepDefs;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import cucumber_context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v135.network.model.Request;
import org.openqa.selenium.devtools.v135.network.model.RequestId;
import org.openqa.selenium.devtools.v135.network.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service_manager.BrowserServiceManager;
import service_manager.ScreenshotServiceManager;
import utils.NetworkLogger;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Hooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);
    final TestContext testContext;
    private final Page browserDriver;
    private final BrowserServiceManager browserServiceManager;
    private final ScreenshotServiceManager screenshotServiceManager;
    public Hooks(TestContext context) {
        testContext = context;
        this.browserServiceManager = testContext.getBrowserServiceManager();
        this.browserDriver = browserServiceManager.getBrowserDriver();
        this.screenshotServiceManager = testContext.getScreenshotServiceManager();
    }
    @Before
    public void cucumberSetup(Scenario scenario) {
        LOGGER.info("Completed setup for scenario: {}", scenario.getName());
        NetworkLogger.attachNetworkListeners(this.browserDriver);

        browserDriver.context().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
    }
    @AfterStep
    public void addScreenshot(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            scenario.attach(
                    screenshotServiceManager.addScreenshot(),
                    "image/png",
                    "screenshot"
            );
        }
    }
    @After
    public void cucumberTearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            String tracePath = "traces/" + scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
            browserDriver.context().tracing().stop(new Tracing.StopOptions().setPath(Paths.get(tracePath)));
        } else {
            browserDriver.context().tracing().stop();
        }
        testContext.getBrowserServiceManager().closeDriver();
        LOGGER.info("Done setup for scenario: {}", scenario.getName());
    }

}