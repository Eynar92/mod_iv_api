package com.todoapi.steps;

import io.restassured.response.Response;

/** Holds state shared by step classes during one scenario. */
public class ProjectScenarioContext {
    String email;
    String password;
    public String token;
    public int projectId;
    String initialProjectName;
    String updatedProjectName;
    Response registrationResponse;
    Response tokenResponse;
    Response createResponse;
    Response updateResponse;
    Response deleteResponse;
    public boolean projectDeleted;
}
