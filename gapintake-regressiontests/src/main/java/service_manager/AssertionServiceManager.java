package service_manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AssertionServiceManager {

    private final SoftAssert softAssert;
    private static final Logger LOGGER = LoggerFactory.getLogger(AssertionServiceManager.class);
    private static final String LOGGER_DESC = "Hard assertion: {} Expected = {}, Actual = {}";

    public AssertionServiceManager() {
        // Initialize a new SoftAssert object
        this.softAssert = new SoftAssert();
    }

    
    /**
     * This method performs a soft assertion to check if two strings are equal.
     *
     * @param message  The custom message for the assertion
     * @param actual   The actual string
     * @param expected The expected string
     */
    public void softAssertEquals(String message, String actual, String expected) {
        // Perform a soft assertion with correct argument order
        softAssert.assertEquals(actual, expected, message);
        // Log the result of the assertion
        LOGGER.info("Soft assertion: {} | Expected = {}, Actual = {}", message, expected, actual);
    }

    /**
     * This method performs a hard assertion to check if two Booleans are equal.
     *
     * @param userMessage The message to display if the assertion fails
     * @param actual      The actual Boolean
     * @param expected    The expected Boolean
     */
    public void assertResponse(String userMessage, Boolean actual, Boolean expected) {
        LOGGER.info(LOGGER_DESC, userMessage, expected, actual);
        Assert.assertEquals(actual, expected, userMessage);
    }

    /**
     * This method performs a hard assertion to check if two Strings are equal.
     *
     * @param userMessage The message to display if the assertion fails
     * @param actual      The actual String
     * @param expected    The expected String
     */
    public void assertResponse(String userMessage, String actual, String expected) {
        LOGGER.info(LOGGER_DESC, userMessage, expected, actual);
        Assert.assertEquals(actual, expected, userMessage);
    }



    /**
     * This method asserts that the actual response matches the expected response.
     * If they do not match, an AssertionError is thrown with the provided user message.
     * The result of the assertion is logged.
     *
     * @param userMessage The message to be used if the assertion fails.
     * @param actual      The actual response value.
     * @param expected    The expected response value.
     */
    public void assertResponse(String userMessage, int actual, int expected) {
        LOGGER.info(LOGGER_DESC, userMessage, expected, actual);
        Assert.assertEquals(actual, expected, userMessage);
    }

    /**
     * This method performs a soft assertion to check if two booleans are equal.
     *
     * @param message  The custom message for the assertion
     * @param actual   The actual boolean
     * @param expected The expected boolean
     */
    public void softAssertEquals(String message, boolean actual, boolean expected) {
        LOGGER.info("Soft assertion: {} | Expected = {}, Actual = {}", message, expected, actual);
        softAssert.assertEquals(actual, expected, message);
    }

    /**
     * This method asserts all soft assertions and throws an exception if any fail.
     */
    public void assertAll() {
        softAssert.assertAll();
    }

}
