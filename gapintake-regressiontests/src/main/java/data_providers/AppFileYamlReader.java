package data_providers;

import enum_detail.BrowserType;
import enum_detail.EnvironmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.*;

public class AppFileYamlReader {

    private final Yaml yaml;
    Map<String, Object> data;
    private static final Logger LOGGER = LoggerFactory.getLogger(AppFileYamlReader.class);

    /**
     * Constructor for AppFileYamlReader. It reads the application.yml file and loads the data.
     */
    public AppFileYamlReader() {
        this.yaml = new Yaml();

        String propertyFilePath = "src/test/resources/user_defined_properties/application.yml";
        try (InputStream inputStream = new FileInputStream(propertyFilePath)) {
            data = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            LOGGER.error("ObjectRepo yaml not found at " + propertyFilePath, e);
        } catch (IOException e) {
            LOGGER.error("IOException occurred", e);
        }
    }
    /**
     * Method to get the maximum page wait time from the YAML file.
     *
     * @return The maximum page wait time as a long. If maxPageWait is not specified, it returns 30.
     */
    public long getMaxPageWait() {
        return Optional.ofNullable((String) data.get("maxPageWait"))
                .map(Long::parseLong)
                .orElse(30L);
    }

    /**
     * Method to get the GAPINTAKE SIT application URL from the YAML file.
     *
     * @return The GAPINTAKE SIT application URL as a string.
     * @throws RuntimeException if GAPINTAKESitUrl is not specified in the Configuration yaml file.
     */
    public String getgapIntakeSitApplicationUrl() {
        String url = (String) data.get("gapIntakeSitUrl");
        if (url != null)
            return url;
        else
            throw new IllegalArgumentException("gapIntakeSitUrl not specified in the Configuration yaml file.");
    }

    /**
     * Method to get the GAPINTAKE UAT application URL from the YAML file.
     *
     * @return The GAPINTAKE UAT application URL as a string.
     * @throws RuntimeException if gapIntakeUatUrl is not specified in the Configuration yaml file.
     */
    public String getgapIntakeUatApplicationUrl() {
        String url = (String) data.get("gapIntakeUatUrl");
        if (url != null)
            return url;
        else
            throw new IllegalArgumentException("gapIntakeUatUrl not specified in the Configuration yaml file.");
    }
    /**
     * Method to get the browser type from the YAML file.
     *
     * @return The browser type as a BrowserType enum.
     * @throws RuntimeException if the browser name key value in Configuration yaml does not match any known browser.
     */
    public BrowserType getBrowser() {
        String browserName = Optional.ofNullable((String) data.get("browser")).orElse("chrome");
        switch (browserName.toLowerCase()) {
            case "chrome":
                return BrowserType.CHROME;
            case "firefox":
                return BrowserType.FIREFOX;
            case "chromium":
                return BrowserType.CHROMIUM;
            default:
                throw new IllegalArgumentException(
                        "Browser Name Key value in Configuration yaml is not matched : " + browserName);
        }
    }

    /**
     * Method to get the environment type from the YAML file.
     *
     * @return The environment type as an EnvironmentType enum.
     * @throws RuntimeException if the environment type key value in Configuration yaml does not match any known environment.
     */
        public EnvironmentType getEnvironment() {
            String environmentName = (String) data.get("environment");
            if (environmentName == null || environmentName.equalsIgnoreCase("Local"))
                return EnvironmentType.LOCAL;
            else if (environmentName.equals("remote"))
                return EnvironmentType.REMOTE;
            else
                throw new IllegalArgumentException(
                        "Environment Type Key value in Configuration yaml is not matched : " + environmentName);
        }

        /**
         * Method to check if the browser window should be maximized, based on the value in the YAML file.
         * @return True if the window should be maximized, false otherwise.
         */
        public Boolean isBrowserWindowSize() {
            Boolean windowSize = (Boolean) data.get("windowMaximize");
            if (windowSize == null)
                return windowSize;
            return true;
        }

    /**
     * This method retrieves the name of the download folder from the configuration data.
     * The name is expected to be specified under the key "downloadFolderName".
     *
     * @return The name of the download folder as a String.
     * @throws IllegalArgumentException if the "downloadFolderName" key is not specified.
     */
    public String getDownloadFolderName() {
        String downloadFolderName = (String) data.get("downloadFolderName");
        if (downloadFolderName != null) {
            return downloadFolderName;
        } else {
            throw new IllegalArgumentException("downloadFolderName not specified in the Configuration yaml file.");
        }
    }

    /**
     * This method retrieves the headless option configuration data.
     *
     * @return The boolean value of headless mode.
     * @throws RuntimeException if the browser name key value in configuration yaml does not match any known browser.
     */
    public Boolean getHeadlessModeOption() {
        Boolean headlessMode = (Boolean) data.get("headlessMode");
        if (headlessMode != null) {
            return headlessMode;
        } else {
            throw new IllegalArgumentException("headless mode not specified in the Configuration yaml file.");
        }
    }
        /**
         * This method retrieves the incognito mode setting from the configuration data.
         *
         * @return the boolean value of incognito mode.
         * @throws IllegalArgumentException if "incognitoMode" is not specified in the YAML file.
         */
        public Boolean getIncognitoModeOption() {
            Boolean headlessMode = (Boolean) data.get("incognitoMode");
            if (headlessMode != null)
                return headlessMode;
            else
                throw new IllegalArgumentException("incognitoMode not specified in the Configuration yaml file.");
        }

        /**
         * Method to get the GAP INTAKE user email from YAML file.
         *
         * @return The GAPINTAKE user email as a string.
         * @throws IllegalArgumentException if userEmail is not specified in the Configuration yaml file.
         */
        public String getUserEmail() {
            String userEmail = (String) data.get("userEmail");
            if (userEmail != null)
                return userEmail;
            else
                throw new IllegalArgumentException("userEmail not specified in the Configuration yaml file.");
        }
    /**
     * Method to get the GAP INTAKE password from the YAML file.
     *
     * @return The GAPINTAKE password as a string.
     * @throws IllegalArgumentException if password is not specified in the Configuration yaml file.
     */
    public String getPassword() {
        String password = (String) data.get("password");
        if (password != null)
            return password;
        else
            throw new IllegalArgumentException("password not specified in the Configuration yaml file.");
    }

    /**
     * Method to get the resolution from the YAML file.
     *
     * @return The resolution as a string.
     * @throws IllegalArgumentException if resolution is not specified in the Configuration yaml file.
     */
    public String getScreenResolution() {
        String browserResolution = (String) data.get("browserResolution");
        if (browserResolution != null)
            return browserResolution;
        else
            throw new IllegalArgumentException("browserResolution not specified in the Configuration yaml file.");
    }
    /**
     * Method to get the thread count from the YAML file.
     *
     * @return The threadCount as a string.
     * @throws IllegalArgumentException if threadCount is not specified in the Configuration yaml file.
     */
    public String getThreadCount() {
        String threadCount = data.get("threadCount").toString();
        if (threadCount != null)
            return threadCount;
        else
            throw new IllegalArgumentException("threadCount not specified in the Configuration yaml file.");
    }
}


