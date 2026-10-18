# Todo.ly API Automation

This project will automate Todo.ly with Java, Gradle, Cucumber, and REST Assured. The planned flow creates a user without authentication, obtains a token with the generated email and password using HTTP Basic authentication, then creates, updates, and deletes a project using the token.

## Current status

This branch adds one Cucumber scenario for the full flow: create a unique user, retrieve its token with BasicAuth, create, update, and delete a project, then verify the captured responses. `ProjectActionsSteps` performs requests and `ProjectVerificationsSteps` checks their responses. Cucumber PicoContainer shares a scenario-scoped `ProjectScenarioContext` between them; each scenario gets its own context. The live `check` passed with 1 scenario and 7 feature steps. `check` also creates an HTML report at `build/reports/cucumber/report.html`. See the [Todo.ly API documentation](https://todo.ly/ApiWiki/) and [Cucumber state documentation](https://cucumber.io/docs/cucumber/state/).

## Requirements and commands

- JDK 21
- Network access to Todo.ly for the live scenario
- No separate Gradle installation is needed

Run on Windows:

```powershell
.\gradlew.bat clean check
```

Run on macOS or Linux:

```sh
./gradlew clean check
```

The Gradle Wrapper uses Gradle 8.14.3. Java source targets the Java 21 toolchain.

## Planned project structure

- `src/main/java/com/todoapi/core`: shared API configuration and HTTP request support
- `src/main/java/com/todoapi/business`: Todo.ly user registration, authentication, and project operations
- `src/test/java/com/todoapi/steps`: action steps, verification steps, and scenario context
- `src/test/java/com/todoapi/hooks`: project cleanup hook
- `src/test/resources/features`: Gherkin feature files
- `src/main/resources/config.properties`: nonsensitive API base URL

The core layer handles common HTTP concerns. The business layer provides user and project operations. The test layer separates actions from response verifications and shares state through PicoContainer. `ProjectHooks` contains the `@After` hook that attempts to remove the generated project after a failed step. A generated user account remains after each run.

## Configuration and code conventions

`ConfigReader` loads `src/main/resources/config.properties` once per reader instance. Edit `api.base.url` there to change the service URL. The file contains no credentials, and no environment variables are needed. The scenario generates account credentials at runtime and uses the returned token for project operations. Credentials and tokens are not logged. Do not commit secrets or tokens; local build output is ignored by Git.

Repository content is in English. Each Java class will have a concise, one-line Javadoc description. Constructors and methods will have concise Javadocs; method documentation will include `@param` for arguments and `@return` for non-void methods, and `@throws` where useful. To keep the assignment close to its reference style, validation will focus on the planned Cucumber end-to-end scenario rather than separate unit tests.

## Branch progression

Implementation branches start from the preceding stage and contain one squashed commit per implementation. Branch names use `jalvarez/short_description`. Stages are integrated into `main` in order through pull requests using squash merge; remaining branches are rebased onto the updated `main` before their pull requests are opened.

1. `main` — integrated framework, starting from the README and `.gitignore` baseline
2. `jalvarez/project_setup` — Java 21 build, dependencies, and Gradle Wrapper
3. `jalvarez/core_layer` — shared API configuration and generic HTTP support
4. `jalvarez/business_layer` — Todo.ly user creation, token retrieval, and project operations
5. `jalvarez/test_layer` — Cucumber feature, step definitions, assertions, and cleanup

The repository is published at [Eynar92/mod_iv_api](https://github.com/Eynar92/mod_iv_api). Use `main` for the complete integrated framework. Implementation branches are retained as references for each stage.
