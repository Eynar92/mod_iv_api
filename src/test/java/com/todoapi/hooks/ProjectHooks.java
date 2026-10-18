package com.todoapi.hooks;

import com.todoapi.business.services.ProjectService;
import com.todoapi.steps.ProjectScenarioContext;
import io.cucumber.java.After;
import io.restassured.response.Response;

/** Cleans up a generated Todo.ly project after a failed scenario step. */
public class ProjectHooks {
    private final ProjectScenarioContext context;
    private final ProjectService projectService = new ProjectService();

    /** Creates project hooks with scenario-scoped state.
     * @param context state shared across this scenario
     */
    public ProjectHooks(ProjectScenarioContext context) {
        this.context = context;
    }

    /** Attempts to delete the scenario project if it was not already deleted. */
    @After
    public void cleanUpProject() {
        if (context.projectId > 0 && context.token != null && !context.projectDeleted) {
            Response response = projectService.deleteProject(context.projectId, context.token);
            if (response.statusCode() != 200
                    || response.jsonPath().getInt("Id") != context.projectId
                    || !Boolean.TRUE.equals(response.jsonPath().getBoolean("Deleted"))) {
                throw new AssertionError("Cleanup could not confirm deletion of the scenario project");
            }
        }
    }
}
