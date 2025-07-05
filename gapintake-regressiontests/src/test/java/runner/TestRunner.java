package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;
import service_manager.FileReaderServiceManager;

@CucumberOptions(
        features = {"src/test/resources/featureFiles"},
        glue = {"stepDefs"},
        plugin = {
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:target/Testexecution-reports.html"
        },
        tags = "@TESTING",
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

        private static int parallelCount = 1; // Default value

        static {
                try {
                        parallelCount = Integer.parseInt(
                                FileReaderServiceManager.getInstance()
                                        .getAppFileReader()
                                        .getThreadCount()
                        );
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }

        @BeforeTest(alwaysRun = true)
        public void setUpTest() {
                System.out.println("Parallel thread count: " + parallelCount);
                System.setProperty("dataproviderthreadcount", String.valueOf(parallelCount));
        }
}
