# Todo.ly API Automation

This Java project uses Gradle, REST Assured, and Cucumber to exercise a Todo.ly project lifecycle. The scenario creates a user with unique credentials without authentication, obtains an authentication token with HTTP Basic authentication, creates a project, updates its name, and deletes it using the token. It then verifies the captured create, update, and delete responses.

## Requirements

- JDK 21
- Network access to Todo.ly
- No separate Gradle installation; the Gradle Wrapper is included

The Wrapper runs Gradle 8.14.3 and the Java toolchain targets Java 21.

## Run

Run the Cucumber scenario by itself. This is the quickest option for routine runs:

```powershell
.\gradlew.bat cucumberCli
```

On macOS or Linux:

```sh
./gradlew cucumberCli
```

The task compiles the test classes and runs all Cucumber scenarios. It reuses existing build outputs where possible; `clean` is not required for normal runs. To run the broader Gradle verification, including `check`, use `.\gradlew.bat check` on Windows or `./gradlew check` on macOS/Linux. To rebuild from scratch, use `.\gradlew.bat clean check` or `./gradlew clean check`.

### Filter scenarios by tag

The lifecycle scenario has the tags `@api` and `@project_lifecycle`. Pass a Cucumber tag expression through the Gradle `tags` property:

```powershell
.\gradlew.bat cucumberCli -Ptags="@project_lifecycle"
.\gradlew.bat cucumberCli -Ptags="@api and @project_lifecycle"
```

```sh
./gradlew cucumberCli -Ptags="@project_lifecycle"
./gradlew cucumberCli -Ptags="@api and @project_lifecycle"
```

When `-Ptags` is omitted, Cucumber runs all scenarios. Tag expressions select scenarios, not individual steps; the current scenario has both tags, so either single tag selects it. Other Cucumber tag expressions such as `or` and `not` can also be passed to `-Ptags`. A tag expression with no matches selects zero scenarios, so check the scenario count in the command output.

### View reports

The Cucumber HTML report is written to `build/reports/cucumber/report.html` and is replaced by the next Cucumber run. On Windows, open it from the project root with:

```powershell
Start-Process ".\build\reports\cucumber\report.html"
```

This is the report for the Cucumber scenario. `build/reports/tests/test/index.html` is Gradle's JUnit test report and is separate; this project has no standalone JUnit tests. `build/reports/problems/problems-report.html`, when Gradle generates it, contains Gradle build diagnostics rather than Cucumber results.

## Project structure

```text
mod_iv_api/
├── .gitignore
├── README.md
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
└── src/
    ├── main/
    │   ├── java/com/todoapi/
    │   │   ├── core/
    │   │   │   ├── client/ApiClient.java
    │   │   │   └── config/ConfigReader.java
    │   │   └── business/services/
    │   │       ├── UserService.java
    │   │       └── ProjectService.java
    │   └── resources/config.properties
    └── test/
        ├── java/com/todoapi/
        │   ├── hooks/ProjectHooks.java
        │   └── steps/
        │       ├── ProjectActionsSteps.java
        │       ├── ProjectVerificationsSteps.java
        │       └── ProjectScenarioContext.java
        └── resources/features/project_lifecycle.feature
```

The core layer provides configuration and generic HTTP methods. The business layer provides Todo.ly user registration, token retrieval, and project operations. The test layer separates Cucumber actions from response verification. PicoContainer gives the action, verification, and hook classes a scenario-scoped context. Verification checks the saved create, update, and delete responses because the project is deleted before the `Then` steps run.

## Configuration and conventions

- `src/main/resources/config.properties` contains the nonsensitive `api.base.url` setting, currently `https://todo.ly`. Edit it to target a different service URL.
- The scenario generates a unique email and password at runtime; no account credentials or environment variables are required. The account and authentication token are not logged.
- The `@After` hook removes the generated project when a scenario step fails before successful deletion. A generated user account remains in Todo.ly after each run.
- Scenario selection uses the optional Gradle `-Ptags` property; without it, all Cucumber scenarios run.
- The HTML report and other build output are stored under the ignored `build/` directory.
- Repository content and Java documentation are in English. Each class has a concise one-line Javadoc description. Constructors and methods use concise Javadocs; methods document their parameters and non-void return values, and document exceptions where useful.
- The scenario is the project's end-to-end validation; it does not include separate unit tests.

See the [Todo.ly API documentation](https://todo.ly/ApiWiki/) and [Cucumber state documentation](https://cucumber.io/docs/cucumber/state/).
