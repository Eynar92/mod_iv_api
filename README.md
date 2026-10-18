# Todo.ly API Automation

This project will automate Todo.ly with Java, Gradle, Cucumber, and REST Assured. The planned flow creates a user without authentication, obtains a token with the generated email and password using HTTP Basic authentication, then creates, updates, and deletes a project using the token.

## Current status

This branch adds `UserService` for user creation and token retrieval, and `ProjectService` for project creation, retrieval, update, and deletion. It builds on the core configuration and HTTP client. See the [Todo.ly API documentation](https://todo.ly/ApiWiki/). The Cucumber scenario is not implemented or live-verified yet. The planned flow creates a user, retrieves a token with the generated credentials using BasicAuth, then creates, updates, and deletes a project with the token.

## Requirements and commands

- JDK 21
- Network access to Todo.ly for the future live scenario
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
- `src/test/java/com/todoapi/steps`: Cucumber step definitions and scenario state
- `src/test/java/com/todoapi/hooks`: cleanup hooks
- `src/test/resources/features`: Gherkin feature files
- `src/main/resources/config.properties`: nonsensitive API base URL

The core layer will handle common HTTP concerns. The business layer will describe the Todo.ly user and project flow. The test layer will express and verify that flow with Cucumber. These source directories will be added with their implementation stages.

## Configuration and code conventions

`ConfigReader` loads `src/main/resources/config.properties` once per reader instance. Edit `api.base.url` there to change the service URL. The file contains no credentials, and no environment variables are needed. The test scenario will generate account credentials at runtime and use the returned token for project operations. Do not commit secrets or tokens; local build output is ignored by Git.

Repository content is in English. Each Java class will have a concise, one-line Javadoc description. Constructors and methods will have concise Javadocs; method documentation will include `@param` for arguments and `@return` for non-void methods, and `@throws` where useful. To keep the assignment close to its reference style, validation will focus on the planned Cucumber end-to-end scenario rather than separate unit tests.

## Branch progression

Branches are stacked: each stage starts from its predecessor, and each implementation branch contains one squashed commit relative to its parent. Branch names use `jalvarez/short_description`.

1. `main` — minimal README and `.gitignore` baseline
2. `jalvarez/project_setup` — Java 21 build, dependencies, and Gradle Wrapper
3. `jalvarez/core_layer` — shared API configuration and generic HTTP support
4. `jalvarez/business_layer` — Todo.ly user creation, token retrieval, and project operations (current branch)
5. `jalvarez/test_layer` — Cucumber feature, step definitions, assertions, and cleanup hooks

All work is kept in local branches; no remote is configured.
