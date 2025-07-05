package service_manager;

import data_providers.AppFileYamlReader;
import data_providers.PageObjectFileYamlReader;

public class FileReaderServiceManager {

    private static final FileReaderServiceManager fileReaderManager = new FileReaderServiceManager();
    private static AppFileYamlReader appFileYamlReader;
    private static PageObjectFileYamlReader pageObjectFileYamlReader;
    /**
     * Private Constructor to prevent instantiation of this class.
     */
    private FileReaderServiceManager() {
        // Private constructor to enforce singleton pattern
    }
    /**
     * Method to get the singleton instance of FileReaderServiceManager.
     * @return Singleton instance of FileReaderServiceManager.
     */
    public static FileReaderServiceManager getInstance() {
        return fileReaderManager;
    }

    /**
     * Method to get the instance of AppFileYamlReader.
     * If the instance is null, a new instance is created.
     * @return Instance of AppFileYamlReader.
     */
    public AppFileYamlReader getAppFileReader() {
        return (appFileYamlReader == null) ? new AppFileYamlReader() : appFileYamlReader;
    }

    /**
     * Method to get the instance of PageObjectFileYamlReader.
     * If the instance is null, a new instance is created.
     * @return Instance of PageObjectFileYamlReader.
     */
    public PageObjectFileYamlReader getPageObjectFileReader() {
        return (pageObjectFileYamlReader == null) ? new PageObjectFileYamlReader() : pageObjectFileYamlReader;
    }


}
