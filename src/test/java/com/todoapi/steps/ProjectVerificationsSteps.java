package com.todoapi.steps;

import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Verifies Todo.ly lifecycle responses. */
public class ProjectVerificationsSteps {
    private final ProjectScenarioContext context;

    /** Creates verification steps with scenario-scoped state.
     * @param context state shared across this scenario
     */
    public ProjectVerificationsSteps(ProjectScenarioContext context) {
        this.context = context;
    }

    /** Verifies user, token, project creation, and update responses. */
    @Then("the project reflects the expected changes during its lifecycle")
    public void verifyProjectLifecycle() {
        assertEquals(200, context.registrationResponse.statusCode(), "User registration should return HTTP 200");
        assertNull(context.registrationResponse.jsonPath().get("ErrorCode"), "User registration should succeed");
        assertTrue(context.registrationResponse.jsonPath().getInt("Id") > 0, "Registration should return a user ID");

        assertEquals(200, context.tokenResponse.statusCode(), "Token retrieval should return HTTP 200");
        assertNull(context.tokenResponse.jsonPath().get("ErrorCode"), "Token retrieval should succeed");
        assertNotNull(context.token, "Token retrieval should return a token");
        assertFalse(context.token.isBlank(), "Token should not be empty");

        assertEquals(200, context.createResponse.statusCode(), "Project creation should return HTTP 200");
        assertNull(context.createResponse.jsonPath().get("ErrorCode"), "Project creation should succeed");
        assertEquals(context.projectId, context.createResponse.jsonPath().getInt("Id"), "Created project ID should match");
        assertEquals(context.initialProjectName, context.createResponse.jsonPath().getString("Content"), "Created name should match");

        assertEquals(200, context.updateResponse.statusCode(), "Project update should return HTTP 200");
        assertEquals(context.projectId, context.updateResponse.jsonPath().getInt("Id"), "Updated project ID should match");
        assertEquals(context.updatedProjectName, context.updateResponse.jsonPath().getString("Content"), "Updated name should match");
    }

    /** Verifies that the API confirmed deletion of the same project. */
    @Then("the API confirms the project was successfully deleted")
    public void verifyProjectDeleted() {
        assertEquals(200, context.deleteResponse.statusCode(), "Project deletion should return HTTP 200");
        assertEquals(context.projectId, context.deleteResponse.jsonPath().getInt("Id"), "Deleted project ID should match");
        assertTrue(Boolean.TRUE.equals(context.deleteResponse.jsonPath().getBoolean("Deleted")), "API should confirm deletion");
    }
}
