# Magnus Selenium Assignment

Java 11+, Maven, Selenium Java 4.25.0, and TestNG 7.10.2 project for the Magnus training application. Selenium Manager locates the browser driver automatically; install Chrome, Firefox, or Edge before running.

## Configure and run

For IntelliJ/TestNG runs, credentials may instead be stored in a project-root `test-config.properties` file:

```properties
magnus.username=training@jalaacademy.com
magnus.password=<training password>
```

This local file is excluded from version control. Java system properties override environment variables, which override values from this file.

Set the supplied training account credentials in `MAGNUS_USERNAME` and `MAGNUS_PASSWORD` environment variables. They can also be passed as `-Dmagnus.username=... -Dmagnus.password=...` Maven properties. The password is not stored in this project.

From this directory:

```powershell
$env:MAGNUS_USERNAME = "training@jalaacademy.com"
$env:MAGNUS_PASSWORD = "<training password>"
mvn test
```

For tests launched directly from IntelliJ/TestNG, configure `MAGNUS_USERNAME` and `MAGNUS_PASSWORD` in that run configuration's **Environment variables** field. Environment variables set in PowerShell are inherited only by processes started from that shell; IntelliJ does not automatically inherit them if it was launched separately. Authenticated tests are reported as skipped when either credential is missing.

By default the suite uses Chrome and the live Magnus URL. Override `magnus.baseUrl` and `browser` as needed, and set `headless=true` for a headless browser, for example:

```powershell
mvn test -Dbrowser=firefox -Dheadless=true
```

The browser can be `chrome`, `firefox`, or `edge`. Screenshots captured after a test failure are saved under `target/screenshots`.

## Project layout

- `src/test/java/assignments` contains the six assignment exercise classes.
- `src/test/java/framework` contains the reusable BaseTest, page objects, and Employee tests.
- `testng.xml` runs all assignment and framework tests.
- `DEFECTS.md` contains observed Magnus-page issues in JIRA-ready format.

Employee form tests deliberately do not submit new employee records. The save-button example submits an empty form only to exercise its client-side validation.
