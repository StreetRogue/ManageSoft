/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.infra;

/**
 *
 * @author juane
 */

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;



public class HttpRequestUtil {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    // GET Request
    public static String sendGetRequest(String url) throws Exception {
        String token = TokenManager.getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .build();

        HttpResponse<String> response = httpClient.send(
                request, 
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }

    // POST Request with JSON body
    public static String sendPostRequest(String url, String jsonBody) throws Exception {
        String token = TokenManager.getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();

        HttpResponse<String> response = httpClient.send(
                request, 
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }

    // PUT Request with JSON body
    public static String sendPutRequest(String url, String jsonBody) throws Exception {
        String token = TokenManager.getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();

        HttpResponse<String> response = httpClient.send(
                request, 
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }

    // DELETE Request
    public static String sendDeleteRequest(String url) throws Exception {
        String token = TokenManager.getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .build();

        HttpResponse<String> response = httpClient.send(
                request, 
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }

    // POST Request with form data
    public static String sendFormPostRequest(String url, Map<String, String> formData) throws Exception {
        String token = TokenManager.getToken();
        String form = formData.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Bearer " + token)
                .build();

        HttpResponse<String> response = httpClient.send(
                request, 
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }
}