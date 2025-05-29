package co.edu.unicauca.usermicroservices.infrastructure.input.rest;


import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author juane
 */
public class TokenGenerator {
    public static String obtenerToken(String username, String password) throws IOException {
        String tokenUrl = "http://localhost:8080/realms/ManageSoft/protocol/openid-connect/token";
        String clientId = "ManageSoft-Desktop";
        String clientSecret = "ZCwtERrCat7s126xLYsnZNjzDg9W8zXj"; // si aplica

        String params = "grant_type=password"
                + "&client_id=" + clientId
                + "&username=" + username
                + "&password=" + password;

        // Solo agregar client_secret si tu cliente lo requiere
        if (!clientSecret.isEmpty()) {
            params += "&client_secret=" + clientSecret;
        }

        URL url = new URL(tokenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = connection.getOutputStream()) {
            os.write(params.getBytes());
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // El token está en el campo "access_token"
                Gson gson = new Gson();
                TokenResponse tokenResponse = gson.fromJson(response.toString(), TokenResponse.class);
                return tokenResponse.access_token;
            }
        } else {
            throw new IOException("Error al obtener token: " + responseCode);
        }
    }

    // Clase interna para mapear la respuesta del token
    static class TokenResponse {
        String access_token;
        String refresh_token;
        String expires_in;
        String token_type;
    }
}