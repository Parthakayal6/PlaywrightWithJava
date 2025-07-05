package service_manager;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileServiceManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelFileServiceManager.class);
    private static final String USER_DIR = "user.dir";

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    /**
     * This method checks if a specific folder exists in the current working directory.
     *
     * @param folderName The name of the folder to check.
     * @return True if the folder exists, false otherwise.
     * @throws SecurityException If a security manager exists and its checkRead method denies access.
     */
    public boolean doesFolderExist(String folderName) {
        try {
            String fileDirectoryLocation = System.getProperty(USER_DIR) + File.separator + folderName;
            File directory = new File(fileDirectoryLocation);
            return directory.exists();
        } catch (SecurityException e) {
            LOGGER.error("Security exception while checking if folder exists", e);
            return false;
        }
    }

    /**
     * This method creates a new folder in the current working directory.
     *
     * @param folderName The name of the folder to create.
     */
    public void createFolder(String folderName) {
        String fileDirectoryLocation = System.getProperty(USER_DIR) + File.separator + folderName;
        File directory = new File(fileDirectoryLocation);
        if (directory.mkdir()) {
            LOGGER.info("Directory: {} created", folderName);
        } else {
            LOGGER.info("Sorry, unable to create the directory: {}", folderName);
        }
    }
    /**
     * This method deletes files in a specified folder that start with a certain pattern.
     *
     * @param folderName               The name of the folder to check.
     * @param startingPatternOfFile    The starting pattern of the file names to delete.
     */
    public void deleteExistingFile(String folderName, String startingPatternOfFile) {
        String fileDirectoryLocation = System.getProperty(USER_DIR) + File.separator + folderName;
        File directory = new File(fileDirectoryLocation);
        if (directory.exists()) {
            File[] listOfFiles = directory.listFiles();
            assert listOfFiles != null;
            for (File file : listOfFiles) {
                if (file.getName().startsWith(startingPatternOfFile)) {
                    if (file.delete()) {
                        LOGGER.info("Deleted file:{} ", file.getName());
                    } else {
                        LOGGER.info("Sorry, unable to delete the file:{} ", file.getName());
                    }
                }
            }
        } else {
            LOGGER.info("The {} folder doesn't exist", folderName);
        }
    }
    /**
     * This method checks if a file with a specified name is present in a specified folder.
     *
     * @param folderName        The name of the folder to check.
     * @param expectedFileName  The name of the file to check.
     * @return                  The name of the file if it exists, null otherwise.
     */
    public String isDownloadedFilePresent(String folderName, String expectedFileName) {
        String fileDirectoryLocation = System.getProperty(USER_DIR) + File.separator + folderName;
        String actualFileName = null;
        try {
            File folder = new File(fileDirectoryLocation);
            if (folder.exists()) {
                File[] listOfFiles = folder.listFiles();
                assert listOfFiles != null;
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().contains(expectedFileName)) {
                        LOGGER.info("Expected File is{} %s and Actual name is{} %s", expectedFileName, file.getName());
                        actualFileName = file.getName();
                    }
                }
            } else {
                LOGGER.info("Folder doesn't exist");
            }
        } catch (Exception e) {
            return null;
        }
        return actualFileName;
    }
    /**
     * Checks if a specified file is present in a specified directory within a given
     * timeout period.
     *
     * @param folderName           the name of the directory to check
     * @param expectedFileName     the name of the file to look for
     * @param startTime            the timestamp after which the file must be modified
     * @param timeOutMilliSecond   the timeout period in milliseconds
     * @return                     the name of the file if found, null otherwise
     */
    public String isDownLoadedFilePresent(String folderName, String expectedFileName, long startTime, int timeOutMilliSecond) {
        String fileDirectoryLocation = System.getProperty(USER_DIR) + File.separator + folderName;
        String actualFileName = null;
        File folder = new File(fileDirectoryLocation);
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            actualFileName = file.getName();
            if (file.isFile()
                    && actualFileName.contains(expectedFileName)
                    && !actualFileName.contains("crdownload")
                    && file.lastModified() > startTime) {
                LOGGER.info("Expected File----[%s] and Actual name is---[%s]", expectedFileName, file.getName());
                break;
            }
        }
        return actualFileName;
    }
    /**
     * This method extracts the value of a single cell from a specified Excel
     * workbook and worksheet.
     *
     * @param folderName               The name of the folder where the workbook is located.
     * @param actualExcelWorkbookName The name of the Excel workbook.
     * @param workSheetName           The name of the worksheet in the workbook.
     * @param row                     The row number of the cell.
     * @param column                  The column number of the cell.
     * @return                        The value of the specified cell.
     * @throws IOException            If an I/O error occurs.
     */
    public String extractSingleCell(String folderName, String actualExcelWorkbookName, String workSheetName, int row, int column) throws IOException {
        String actualFilePath = System.getProperty(USER_DIR) + File.separator + folderName + File.separator + actualExcelWorkbookName;
        try (FileInputStream inputStream = new FileInputStream(actualFilePath)) {
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            sheet = wb.getSheet(workSheetName);
            return sheet.getRow(row).getCell(column).getStringCellValue().trim();
        }
    }
    /**
     * This method extracts all rows from a specified Excel workbook and worksheet.
     *
     * @param folderName               The name of the folder where the workbook is located.
     * @param actualExcelWorkbookName The name of the Excel workbook.
     * @param workSheetName           The name of the worksheet in the workbook.
     * @return A list of strings, each string representing a roq in the worksheet.
     */
    public List<String> extractedAllRow(String folderName, String actualExcelWorkbookName, String workSheetName) {
        List<String> allRowRecord = new ArrayList<>();
        String actualfilepath = System.getProperty(USER_DIR) + File.separator + folderName + File.separator + actualExcelWorkbookName;

        try (FileInputStream inputStream = new FileInputStream(actualfilepath)) {
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            sheet = wb.getSheet(workSheetName);
            int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

            for (int i = 0; i <= rowCount; i++) {
                StringBuilder singleRecord = new StringBuilder();
                int cellCount = sheet.getRow(i).getLastCellNum();

                for (int j = 0; j < cellCount; j++) {
                    DataFormatter formatter = new DataFormatter();
                    String singleCell = formatter.formatCellValue(sheet.getRow(i).getCell(j));
                    singleRecord.append("|").append(singleCell);
                }

                LOGGER.info("Record--{} and record is {}", i, singleRecord);
                allRowRecord.add(singleRecord.toString().trim().replaceAll(" +", " "));
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found", e);
        } catch (Exception e) {
            LOGGER.error("Error reading file", e);
        }

        return allRowRecord;
    }
    /**
     * This method sets the Excel file and the specific sheet to be used.
     *
     * @param excelFilePath The path to the Excel file.
     * @param sheetName     The name of the sheet within the Excel file.
     */
    public void setExcelFile(String excelFilePath, String sheetName) {
        File file = new File(excelFilePath);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            LOGGER.info("The exception is there");
        }
    }

    /**
     * This method retrieves the value of a specific cell in the Excel sheet.
     *
     * @param rowNumber The row number of the cell (0-based index).
     * @param colNumber The column number of the cell (0-based index).
     * @return The value of the specified cell as a string.
     */
    public String getCellData(int rowNumber, int colNumber) {
        //getting the cell value from rowNumber and cellNumber
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(sheet.getRow(rowNumber).getCell(colNumber));
    }
    /**
     * This method retrieves the total number of rows in the specified Excel sheet.
     *
     * @return The total number of rows in the sheet.
     */
    public int getRowCountInSheet() {
        return sheet.getLastRowNum() - sheet.getFirstRowNum();
    }

    /**
     * This method retrieves the total number of columns in the specified Excel sheet.
     *
     * @return The total number of columns in the sheet.
     */
    public int getColumnCountInSheet() {
        return sheet.getRow(0).getLastCellNum();
    }

    /**
     * This method safely closes the workbook.
     */
    public void closeWorkBook() { // no usages v9p645
        try {
            workbook.close();
        } catch (Exception e) {
            LOGGER.info("Exception is there");
        }
    }
}
