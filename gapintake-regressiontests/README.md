# Automation Practice Framework

## Project Overview

#### Automated Framework for Practice using Java, Maven, and Playwright

## Generic Coding Standards for Automation

* Use soft assertions instead of multiple asserting statements.
* Avoid too many static imports in classes.
* Methods that are private and final and do not access instance data should be static.
* Avoid duplication of assertions.
* Avoid using thread delays.
* Ensure code functionality is user-friendly.
* Avoid using numbers inside XPath expressions.
* Appropriately document code with comments.
* Take a screenshot for failed investigations.

## Languages and Tools
* Java
* Playwright (Java)

## Playwright Java Test Organization Best Practices
##   Test Flow Overview
1. **Test Initialization:**
 
    Playwright and browser are initialized in a base test class. Test context and page are created before each test.
2.  **Page Object Usage:**

    Test classes use Page Object classes from pages/ to interact with the application UI.
3. **Test Execution:**

   Each test is independent, sets up its own state, and uses utility classes (e.g., NetworkLogger) for logging or tracing.
4. **Assertions and Reporting:**

   Soft assertions are used for validation. On failure, screenshots and traces are captured for debugging.
5. **Teardown:**
 
   Browser context is closed after each test. Playwright is closed after all tests.
6. **Reports and Traces:**

   Test results, screenshots, and Playwright traces are stored in the Reports/ directory.
7. **To view a trace:**

   Open the trace in a browser using the command:

```bash
  npx playwright show-trace path/to/trace.zip 
