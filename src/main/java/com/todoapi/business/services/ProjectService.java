package com.todoapi.business.services;

import com.todoapi.core.client.ApiClient;
import io.restassured.response.Response;

import java.util.Map;

/** Creates, reads, updates, and deletes Todo.ly projects. */
public class ProjectService {
    private static final String PROJECTS_PATH = "/api/projects";
    private final ApiClient apiClient;

    /** Creates a project service with an API client. */
    public ProjectService() {
        apiClient = new ApiClient();
    }

    /** Creates a project using an authentication token.
     * @param name project name
     * @param token authentication token
     * @return the API response
     */
    public Response createProject(String name, String token) {
        return apiClient.postWithToken(PROJECTS_PATH + ".json", Map.of("Content", name), token);
    }

    /** Retrieves a project using an authentication token.
     * @param id project identifier
     * @param token authentication token
     * @return the API response
     */
    public Response getProject(int id, String token) {
        return apiClient.getWithToken(PROJECTS_PATH + "/" + id + ".json", token);
    }

    /** Updates a project's name using an authentication token.
     * @param id project identifier
     * @param name new project name
     * @param token authentication token
     * @return the API response
     */
    public Response updateProject(int id, String name, String token) {
        return apiClient.putWithToken(PROJECTS_PATH + "/" + id + ".json", Map.of("Content", name), token);
    }

    /** Deletes a project using an authentication token.
     * @param id project identifier
     * @param token authentication token
     * @return the API response
     */
    public Response deleteProject(int id, String token) {
        return apiClient.deleteWithToken(PROJECTS_PATH + "/" + id + ".json", token);
    }
}
