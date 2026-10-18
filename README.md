# Todo.ly API Automation

This project will automate the Todo.ly user lifecycle with Java, Gradle, Cucumber, and REST Assured. The planned flow creates a user, updates that user's profile, and deletes that same user. Each scenario will generate its own unique email and password and use HTTP Basic authentication for the API requests; no preexisting account credentials or environment variables are required.

## Current status

This branch contains the Java and Gradle setup only. No API classes, Cucumber feature, or automated tests have been added yet. At this stage, `clean check` verifies that the project builds; Gradle reports no test sources to run.

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
- `src/main/java/com/todoapi/business`: Todo.ly user operations and lifecycle rules
- `src/test/java/com/todoapi/steps`: Cucumber step definitions and scenario state
- `src/test/java/com/todoapi/hooks`: cleanup hooks
- `src/test/resources/features`: Gherkin feature files
- `src/main/resources/config.properties`: nonsensitive base URL and timeout settings

The core layer will handle common HTTP concerns. The business layer will describe the Todo.ly user flow. The test layer will express and verify that flow with Cucumber. These source directories will be added with their implementation stages.

## Configuration and code conventions

The planned `config.properties` file will contain only nonsensitive settings such as the base URL and request timeouts. The test scenario will generate account credentials at runtime. Do not commit secrets, tokens, or other sensitive values; local build output and optional `config.local.properties` overrides are ignored by Git. No configuration classes or properties are included in this setup stage.

Repository content is in English. Each Java class will have a concise, one-line Javadoc description. Constructors and methods will have concise Javadocs; method documentation will include `@param` for arguments and `@return` for non-void methods, and `@throws` where useful.

## Branch progression

Branches are stacked: each stage starts from its predecessor, and each implementation branch contains one squashed commit relative to its parent. Branch names use `jalvarez/short_description`.

1. `main` — minimal README and `.gitignore` baseline
2. `jalvarez/project_setup` — Java 21 build, dependencies, and Gradle Wrapper (current branch)
3. `jalvarez/core_layer` — shared API configuration and HTTP support
4. `jalvarez/business_layer` — Todo.ly user operations and lifecycle logic
5. `jalvarez/test_layer` — Cucumber feature, step definitions, assertions, and cleanup hooks

All work is kept in local branches; no remote is configured.
