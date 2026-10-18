package com.todoapi.business.services;

import com.todoapi.core.client.ApiClient;
import io.restassured.response.Response;

import java.util.Map;

/** Creates Todo.ly users and retrieves their authentication tokens. */
public class UserService {
    private final ApiClient apiClient;

    /** Creates a user service with an API client. */
    public UserService() {
        apiClient = new ApiClient();
    }

    /** Registers a user with Todo.ly.
     * @param email user email address
     * @param fullName user's full name
     * @param password user password
     * @return the API response
     */
    public Response createUser(String email, String fullName, String password) {
        Map<String, String> body = Map.of(
                "Email", email,
                "FullName", fullName,
                "Password", password
        );
        return apiClient.post("/api/user.json", body);
    }

    /** Retrieves an authentication token with HTTP Basic authentication.
     * @param email user email address
     * @param password user password
     * @return the API response
     */
    public Response getToken(String email, String password) {
        return apiClient.getWithBasicAuth("/api/authentication/token.json", email, password);
    }
}
