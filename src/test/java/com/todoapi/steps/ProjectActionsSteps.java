package com.todoapi.steps;

import com.todoapi.business.services.ProjectService;
import com.todoapi.business.services.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.UUID;

/** Performs Todo.ly user and project lifecycle actions. */
public class ProjectActionsSteps {
    private final ProjectScenarioContext context;
    private final UserService userService = new UserService();
    private final ProjectService projectService = new ProjectService();

    /** Creates action steps with scenario-scoped state.
     * @param context state shared across this scenario
     */
    public ProjectActionsSteps(ProjectScenarioContext context) {
        this.context = context;
    }

    /** Registers a user with unique credentials.
     */
    @Given("a new Todo.ly user is registered")
    public void registerUser() {
        String uniqueId = UUID.randomUUID().toString();
        context.email = "todo-" + uniqueId + "@example.com";
        context.password = UUID.randomUUID().toString();
        context.registrationResponse = userService.createUser(context.email, "API Test User", context.password);
        if (context.registrationResponse.statusCode() != 200
                || context.registrationResponse.jsonPath().get("ErrorCode") != null
                || context.registrationResponse.jsonPath().getInt("Id") <= 0) {
            throw new IllegalStateException("Todo.ly user registration did not succeed");
        }
    }

    /** Retrieves a token for the generated user credentials. */
    @Given("the user obtains an authentication token")
    public void obtainToken() {
        context.tokenResponse = userService.getToken(context.email, context.password);
        context.token = context.tokenResponse.jsonPath().getString("TokenString");
        if (context.tokenResponse.statusCode() != 200
                || context.tokenResponse.jsonPath().get("ErrorCode") != null
                || context.token == null || context.token.isBlank()) {
            throw new IllegalStateException("Todo.ly token retrieval did not succeed");
        }
    }

    /** Creates a project and stores its response and identifier.
     * @param name project name
     */
    @When("the user creates a project named {string}")
    public void createProject(String name) {
        context.initialProjectName = name;
        context.createResponse = projectService.createProject(name, context.token);
        context.projectId = context.createResponse.jsonPath().getInt("Id");
        if (context.createResponse.statusCode() != 200
                || context.createResponse.jsonPath().get("ErrorCode") != null
                || context.projectId <= 0) {
            throw new IllegalStateException("Todo.ly project creation did not succeed");
        }
    }

    /** Updates the project name and stores its response.
     * @param name updated project name
     */
    @When("the user updates the project name to {string}")
    public void updateProject(String name) {
        context.updatedProjectName = name;
        context.updateResponse = projectService.updateProject(context.projectId, name, context.token);
    }

    /** Deletes the project and stores whether cleanup is still needed. */
    @When("the user deletes the project")
    public void deleteProject() {
        context.deleteResponse = projectService.deleteProject(context.projectId, context.token);
        context.projectDeleted = context.deleteResponse.statusCode() == 200
                && context.deleteResponse.jsonPath().getInt("Id") == context.projectId
                && Boolean.TRUE.equals(context.deleteResponse.jsonPath().getBoolean("Deleted"));
    }

}
