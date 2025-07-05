Feature: GapIntake_Playwright

  @TESTINGSWAY @TESTING
  Scenario Outline: Create new client ID1
    Given user navigate to "<url>" and validate the "<PageTitle>"
    Then user click on "addClientID" for "<CountryLevelAccount>" and validate the response "Add Client Id"
    Then user add new record with below details
      | # | Description              | UserInputValue     |
      | 1 | DropDownClientIDStatus   | <ClientIDStatus>   |
      | 2 | TextClientID             | <ClientID>         |
      | 3 | TextClientIDName         | <ClientIDName>     |
      | 4 | TextReportingID          | <ReportingID>      |
      | 5 | TextComments             | <Comments>         |
    Then user click on "Save" and validate the response "Client Id is created successfully."

    Examples:
      | url                | PageTitle   | CountryLevelAccount      | ClientIDStatus | ClientID | ClientIDName | ReportingID | Comments |
      | GAPINTAKE-SIT-URL  | GAP Intake  | Assa Abloy - Austria     | ON HOLD        | SINGA1   | OOM          | EXIT        | 1        |

  @TESTINGSWAY @TESTING
  Scenario Outline: Create new client ID2
    Given user navigate to "<url>" and validate the "<PageTitle>"
    Then user click on "addClientID" for "<CountryLevelAccount>" and validate the response "Add Client Id"
    Then user add new record with below details
      | # | Description              | UserInputValue     |
      | 1 | DropDownClientIDStatus   | <ClientIDStatus>   |
      | 2 | TextClientID             | <ClientID>         |
      | 3 | TextClientIDName         | <ClientIDName>     |
      | 4 | TextReportingID          | <ReportingID>      |
      | 5 | TextComments             | <Comments>         |
    Then user click on "Save" and validate the response "Client Id is created successfully."

    Examples:
      | url                | PageTitle   | CountryLevelAccount      | ClientIDStatus | ClientID | ClientIDName | ReportingID | Comments |
      | GAPINTAKE-SIT-URL  | GAP Intake  | Assa Abloy - Austria     | ON HOLD        | SINGA1   | OOM          | EXIT        | 1        |
