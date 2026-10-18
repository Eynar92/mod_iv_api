package com.todoapi.core.client;

import com.todoapi.core.config.ConfigReader;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/** Sends JSON requests to the configured API base URL. */
public class ApiClient {
    private final String baseUrl;

    /** Creates a client using the configured API base URL.
     * @throws IllegalStateException when the configuration resource cannot be loaded
     */
    public ApiClient() {
        baseUrl = new ConfigReader().getBaseUrl();
    }

    /** Sends an unauthenticated JSON POST request.
     * @param path API resource path
     * @param body request body to serialize as JSON
     * @return the API response
     */
    public Response post(String path, Object body) {
        return given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .accept("application/json")
                .body(body)
                .when()
                .post(path);
    }

    /** Sends a GET request with HTTP Basic authentication.
     * @param path API resource path
     * @param email account email
     * @param password account password
     * @return the API response
     */
    public Response getWithBasicAuth(String path, String email, String password) {
        return given()
                .baseUri(baseUrl)
                .auth().preemptive().basic(email, password)
                .accept("application/json")
                .when()
                .get(path);
    }

    /** Sends a JSON POST request with a token header.
     * @param path API resource path
     * @param body request body to serialize as JSON
     * @param token authentication token
     * @return the API response
     */
    public Response postWithToken(String path, Object body, String token) {
        return given()
                .baseUri(baseUrl)
                .header("Token", token)
                .contentType("application/json")
                .accept("application/json")
                .body(body)
                .when()
                .post(path);
    }

    /** Sends a JSON PUT request with a token header.
     * @param path API resource path
     * @param body request body to serialize as JSON
     * @param token authentication token
     * @return the API response
     */
    public Response putWithToken(String path, Object body, String token) {
        return given()
                .baseUri(baseUrl)
                .header("Token", token)
                .contentType("application/json")
                .accept("application/json")
                .body(body)
                .when()
                .put(path);
    }

    /** Sends a DELETE request with a token header.
     * @param path API resource path
     * @param token authentication token
     * @return the API response
     */
    public Response deleteWithToken(String path, String token) {
        return given()
                .baseUri(baseUrl)
                .header("Token", token)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .delete(path);
    }

    /** Sends a GET request with a token header.
     * @param path API resource path
     * @param token authentication token
     * @return the API response
     */
    public Response getWithToken(String path, String token) {
        return given()
                .baseUri(baseUrl)
                .header("Token", token)
                .accept("application/json")
                .when()
                .get(path);
    }
}
