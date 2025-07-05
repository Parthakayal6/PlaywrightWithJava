package service_manager;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import enum_detail.EnvironmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class BrowserServiceManager {
    private Page browserDriver;
    private Locator element;
    private List<Locator> elements;
    private final EnvironmentType environmentType;

    private Browser browser;
    private BrowserContext browserContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserServiceManager.class);
    public BrowserServiceManager() {
        environmentType = FileReaderServiceManager.getInstance().getAppFileReader().getEnvironment();
    }

    /*
     * Returns the browser driver instance based on the environment type.
     * If the browser driver is already created, it reuses the existing instance.
     *
     * @return The Playwright Page instance representing the browser driver.
     * @throws AWTExecution if there is an error creating the browser driver.
     */
    public Page getBrowserDriver() {
        final var appFileReader = FileReaderServiceManager.getInstance().getAppFileReader();
        if (this.browserDriver == null) {
            try {
                Playwright playwright;
                if (this.browser == null) {
                    playwright = Playwright.create();
                    BrowserType chrome = playwright.chromium();
                    if (Boolean.TRUE.equals(appFileReader.getHeadlessModeOption())) {
                        this.browser = chrome.launch(new BrowserType.LaunchOptions()
                                .setHeadless(true)
                                .setArgs(Arrays.asList("--window-size=1920,1080"))
                        );
                        browserContext = browser.newContext(new Browser.NewContextOptions()
                                .setViewportSize(1920, 1080));
                    } else {
                        this.browser = chrome.launch(new BrowserType.LaunchOptions()
                                .setHeadless(false)
                                .setArgs(Arrays.asList("--start-maximized"))
                        );
                        browserContext = browser.newContext(new Browser.NewContextOptions()
                                .setViewportSize(null));
                    }
                }

                if (this.browserContext == null) {
                    this.browserContext = browser.newContext();
                }

                this.browserDriver = browserContext.newPage();
                LOGGER.info("Browser driver has been created using reused context");
            } catch (Exception e) {
                LOGGER.error("Failed to initialize browser driver: {}", e.getMessage());
                throw new RuntimeException("Browser driver initialization failed", e);
            }
        }
            return this.browserDriver;

    }
    /**
     * Navigates to the specified URL and waits for it to load.
     *
     * @param url The URL to navigate to.
     * @param timeoutMillis The timeout in milliseconds for waiting for the URL to load.
     * @return true if the navigation was successful, false otherwise.
     */
    public boolean navigateToURL(String url, int timeoutMillis) {
        try {
            LOGGER.info("Navigating to URL: {}", url);
            this.browserDriver.navigate(url);
            this.browserDriver.waitForURL(url, new Page.WaitForURLOptions().setTimeout(timeoutMillis));
            String currentUrl = this.browserDriver.url();
            if (currentUrl.equals(url)) {
                LOGGER.info("URL loaded successfully: {}", url);
                return true;
            } else {
                LOGGER.error("Navigation failed. Expected URL: {}, but got: {}", url, currentUrl);
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Failed to load URL '{}': {}", url, e.getMessage());
            return false;
        }
    }
    /**
     * Returns the locator for the specified element on the page.
     *
     * @param page             The Playwright Page instance.
     * @param locatorType      The type of locator (e.g. id,class,css,xpath).
     * @param locatorValue     The value of locator.
     * @return the Locator object representing the element.
     */
    public Locator getElement(Page page, String locatorType, String locatorValue) {
        if (locatorType == null || locatorValue == null) {
            throw new IllegalArgumentException("Locator type or value cannot be null");
        }
        try {
            switch (locatorType.toLowerCase()) {
                case "id":
                    return page.locator("#" + locatorValue);
                case "class":
                    return page.locator("." + locatorValue);
                case "css":
                    return page.locator(locatorValue);
                case "xpath":
                    return page.locator(locatorValue);
                case "testid":
                    return page.getByTestId(locatorValue);
                case "role":
                    return page.getByRole(AriaRole.valueOf(locatorValue.toUpperCase()));
                case "placeholder":
                    return page.getByPlaceholder(locatorValue);
                case "bytext":
                    return page.getByText(locatorValue);
                case "name":
                    return page.locator("[name=\"" + locatorValue + "\"]");
                case "alt":
                    return page.locator("[alt=\"" + locatorValue + "\"]");
                case "label":
                    return page.getByLabel(locatorValue);
                case "title":
                    return page.locator("[title=\"" + locatorValue + "\"]");
                case "text":
                    return page.locator("[text=\"" + locatorValue + "\"]");
                default:
                    throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
            }

        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid argument: LocatorType={}, LocatorValue={}, Error: {}", locatorType, locatorValue, e.getMessage());
            throw e;
        }

        catch (Exception e) {
            LOGGER.error("Unexpected error: LocatorType={}, LocatorValue={}, Error: {}", locatorType, locatorValue, e.getMessage());
            throw e;
        }
    }

    /**
     * Returns a list of elements matching the specified locator type and value.
     *
     * @param page The Playwright Page instance.
     * @param locatorType The type of locator (e.g., id, class, css, xpath).
     * @param locatorValue The value of the locator.
     * @return A list of Locator objects representing the elements.
     */
    public List<Locator> getElements(Page page, String locatorType, String locatorValue) {
        if (page == null || locatorType == null || locatorValue == null) {
            throw new IllegalArgumentException("Page, LocatorType, or LocatorValue cannot be null");
        }

        switch (locatorType.toLowerCase()) {
            case "id":
                return page.locator("id=" + locatorValue).all();
            case "css":
                return page.locator(locatorValue).all();
            case "xpath":
                return page.locator(locatorValue).all();
            case "testid":
                return page.getByTestId(locatorValue).all();
            case "testId":
                return page.getByTestId(locatorValue).all();
            case "role":
                return page.getByRole(AriaRole.valueOf(locatorValue.toUpperCase())).all();
            case "placeholder":
                return page.getByPlaceholder(locatorValue).all();
            case "bytext":
                return page.getByText(locatorValue).all();
            default:
                LOGGER.error("Unsupported locator type: {}", locatorType);
                throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
        }
        }
    /**
     * Sets the text in the specified web element.
     *
     * @param webElement The Locator object representing the web element.
     * @param valueToEnter The value to enter in the web element.
     */
    public void setText(Locator webElement, String valueToEnter) {
        if (webElement == null || valueToEnter == null) {
            throw new IllegalArgumentException("WebElement or valueToEnter cannot be null");
        }
        webElement.clear();
        webElement.fill(valueToEnter);
        LOGGER.info("Set text '{}' in element", valueToEnter);
    }
    /**
     * Clicks on the specified web element.
     *
     * @param webElement The Locator object representing the web element.
     */
    public void click(Locator webElement) {
        if (webElement == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        webElement.click();
        LOGGER.info("Clicked on element");
    }

    /**
     * Scrolls to the specified web element and clicks on it.
     *
     * @param webElement The Locator object representing the web element.
     */
    public void scrollAndClick(Locator webElement) {
        if (webElement == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        webElement.scrollIntoViewIfNeeded();
        webElement.click();
        LOGGER.info("Scrolled to and clicked on element");
    }

    /**
     * Retrieves the text content of the specified web element.
     *
     * @param webElement The Locator object representing the web element.
     * @return The text content of the web element.
     */
    public String getText(Locator webElement) {
        if (webElement == null) {
            throw new IllegalArgumentException("WebElement cannot be null");
        }
        String text;
        try {
            webElement.waitFor();
            text = webElement.textContent();
            LOGGER.info("Retrieved text: {}", text);
        } catch (Exception e) {
            text = "Element not found: " + e.getMessage();
            LOGGER.warn("Element not found: {}", webElement, e);
        }
        return text;
    }
    /**
     * Retrieves the value of the specified attribute from the web element.
     *
     * @param webElement The Locator object representing the web element.
     * @param attribute The name of the attribute to retrieve.
     * @return The value of the specified attribute.
     */
    public String getAttribute(Locator webElement, String attribute) {
        if (webElement == null || attribute == null) {
            throw new IllegalArgumentException("WebElement or attribute cannot be null");
        }
        String value;
        try {
            webElement.waitFor();
            value = webElement.getAttribute(attribute);
            LOGGER.info("Retrieved attribute '{}': with value: {}", attribute, value);
        } catch (Exception e) {
            value = "Element not found: " + e.getMessage();
            LOGGER.warn("Element not found: {}", e.getMessage());
        }
        return value;
    }
    /**
     * Selects a value from an auto-search box by filling it and clicking on the option.
     *
     * @param autoSearchBox The Locator object representing the auto-search box.
     * @param valueToBeSelected The value to be selected from the auto-search options.
     */
    public void selectValueFromAutoSearch(Locator autoSearchBox, String valueToBeSelected) {
        if (autoSearchBox == null || valueToBeSelected == null) {
            throw new IllegalArgumentException("AutoSearchBox or valueToBeSelected cannot be null");
        }

        autoSearchBox.clear();
        autoSearchBox.fill(valueToBeSelected);
        LOGGER.info("Filled auto-search box with value: {}", valueToBeSelected);

        Locator option = browserDriver.getByRole(
                AriaRole.OPTION,
                new Page.GetByRoleOptions().setName(valueToBeSelected)
        );
        option.waitFor();
        option.click();
        LOGGER.info("Selected value '{}' from auto-search options", valueToBeSelected);
    }
    /**
     * Selects a value from a dropdown by clicking on it and selecting the option.
     *
     * @param dropDown The Locator object representing the dropdown.
     * @param valueToBeSelected The value to be selected from the dropdown options.
     */
    public void selectValueFromDropDown(Locator dropDown, String valueToBeSelected) {
        if (dropDown == null || valueToBeSelected == null) {
            throw new IllegalArgumentException("dropDown or valueToBeSelected cannot be null");
        }

        boolean isVisible = fluentWaitForVisible(dropDown, 10000, 500); // 10s timeout, 0.5s polling
        dropDown.click();
        LOGGER.info("Clicked on dropdown");

        browserDriver.getByRole(
                AriaRole.OPTION,
                new Page.GetByRoleOptions().setName(valueToBeSelected)
        ).click();
        LOGGER.info("Selected value '{}' from dropdown", valueToBeSelected);
    }

    /**
     * Waits for the specified locator to become visible within the given timeout period.
     *
     * @param locator The Locator object to check visibility for.
     * @param timeoutMillis The maximum wait time in milliseconds.
     * @param pollingMillis The polling interval in milliseconds.
     * @return true if the element becomes visible, false if timeout is reached.
     */
    public boolean fluentWaitForVisible(Locator locator, int timeoutMillis, int pollingMillis) {
        long endTime = System.currentTimeMillis() + timeoutMillis;
        while (System.currentTimeMillis() < endTime) {
            try {
                if (locator.isVisible()) {
                    return true;
                }
            } catch (Exception ignored) {}
            try {
                Thread.sleep(pollingMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return false;
    }

    /**
     * Checks if the specified element is present and visible on the page.
     *
     * @param element The Locator object representing the element to check.
     * @return true if the element is present and visible, false otherwise.
     */
    public boolean isElementPresent(Locator element) {
        if (element == null) {
            return false;
        }
        try {
            return element.count() > 0 && element.isVisible();
        } catch (Exception e) {
            LOGGER.warn("Element presence check failed: {}", e.getMessage());
            return false;
        }
    }
    /**
     * Generic method to download a file in Playwright.
     *
     * @param page The Playwright Page instance.
     * @param triggerAction The action that triggers the file download (e.g., clicking a button).
     * @param saveFilePath The path where the downloaded file should be saved.
     * @return The path of the saved file.
     */
    public static Path downloadFile(Page page, Runnable triggerAction, String saveFilePath) {
        try {
            // Wait for the download event using waitForDownload
            Download download = page.waitForDownload(triggerAction);

            // Convert saveFilePath to Path and save the downloaded file
            Path savePath = Path.of(saveFilePath);
            download.saveAs(savePath);

            // Return the path of the saved file
            return savePath;
        } catch (Exception e) {
            throw new RuntimeException("File download failed: " + e.getMessage(), e);
        }
    }
    /**
     * Finds an element by user input and replaces the placeholder with the actual value.
     *
     * @param actualVal The locator key with a placeholder.
     * @param actualUserDefinedKey The actual value to replace the placeholder.
     * @return The Locator object representing the element.
     */
    public Locator findElementByUserInput(String actualVal, String actualUserDefinedKey) {
        String prop = FileReaderServiceManager.getInstance()
                .getPageObjectFileReader()
                .getObjectDetails(actualVal);

        prop = prop.replaceAll("userDefinedInput", actualUserDefinedKey);
        element = getElement(browserDriver, returnLocatorType(actualVal), prop);
        return element;
    }
    /**
     * Finds and returns a Playwright Locator for the given locator key.
     * The Locator key is used to fetch the actual locator value from the page object file reader,
     * and the Locator type is determined from the key.
     *
     * @param actualUserDefinedKey The key used to look up the locator details.
     * @return A list of Locator objects representing the found elements.
     */
    public Locator findElementByLocator(String actualUserDefinedKey) {
        String prop = FileReaderServiceManager.getInstance()
                .getPageObjectFileReader()
                .getObjectDetails(actualUserDefinedKey);

        element = getElement(browserDriver, returnLocatorType(actualUserDefinedKey), prop);
        return element;
    }
    /**
     * Finds and returns a list of Playwright Locator objects for the given locator key.
     * The Locator key is used to fetch the actual locator value from the page object file reader,
     * and the Locator type is determined from the key.
     *
     * @param actualUserDefinedKey The key used to look up the locator details.
     * @return A list of Locator objects representing the found elements.
     */
    public List<Locator> findElementsByLocator(String actualUserDefinedKey) {
        String prop = FileReaderServiceManager.getInstance()
                .getPageObjectFileReader()
                .getObjectDetails(actualUserDefinedKey);

        elements = getElements(browserDriver, returnLocatorType(actualUserDefinedKey), prop);
        return elements;
    }

    /**
     * Returns the locator type based on the provided element key.
     * The element key is expected to be in the format "appPageName.locatorType.LocatorValue".
     *
     * @param elementKey The key representing the element.
     * @return The locator type as a string (e.g., "xpath", "css").
     */
    public String returnLocatorType(String elementKey) {
        String[] appPageNameLocatorType = elementKey.split("\\.");
        return appPageNameLocatorType[3];
    }

    /**
     * Closes the current Playwright browser driver if it exists.
     * Ensures the driver is properly closed and resources are released.
     * Logs the closure or any errors encountered during the process.
     */
    public void closeDriver() {
        try {
            if (browserDriver != null) {
                browserDriver.close();
                browserDriver = null;
                LOGGER.info("Browser driver has been closed");
            }
        } catch (Exception e) {
            LOGGER.error("Error while closing the browser driver: {}", e.getMessage());
        }
    }
}
