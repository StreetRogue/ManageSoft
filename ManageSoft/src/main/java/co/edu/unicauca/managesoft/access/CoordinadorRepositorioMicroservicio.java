/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Coordinador;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author camac
 */
public class CoordinadorRepositorioMicroservicio implements ICoordinadorRepositorio {

    private final String baseUrl = "http://localhost:8083/api/coordinador/buscar";
    private final Gson gson = new Gson();

    @Override
    public Coordinador buscarCoordinador(String nombreUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Coordinador buscarCoordinador(String nombreUsuario, String contrasenaUsuario) {
        try {
            URL url = new URL(baseUrl); // Usa la URL real que necesites
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            Map<String, String> datos = new HashMap<>();
            datos.put("username", nombreUsuario);
            datos.put("password", contrasenaUsuario);
            String jsonInput = gson.toJson(datos);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes("utf-8"));
            }

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JsonObject obj = JsonParser.parseString(response.toString()).getAsJsonObject();

                    String nombre = (obj.has("nombreCoordinador") ? obj.get("nombreCoordinador").getAsString() : "")
                            + " "
                            + (obj.has("apellidoCoordinador") ? obj.get("apellidoCoordinador").getAsString() : "");
                    String email = obj.has("emailCoordinador") ? obj.get("emailCoordinador").getAsString() : "";
                    String telefono = obj.has("telefonoCoordinador") ? obj.get("telefonoCoordinador").getAsString() : "";
                    String nombreUsr = obj.has("nombreUsuario") ? obj.get("nombreUsuario").getAsString() : "";
                    String contrasenaUsr = obj.has("contrasenaUsuario") ? obj.get("contrasenaUsuario").getAsString() : "";

                    return new Coordinador(nombreUsr, contrasenaUsr, nombre, email, telefono);
                }
            } else {
                System.err.println("Error: Código de respuesta " + conn.getResponseCode());
            }
        } catch (Exception e) {
            System.err.println("Error al deserializar Coordinador: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
    
    
   
}
