/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Coordinador;
import com.google.gson.Gson;
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
            URL url = new URL(baseUrl); // No concatenes usuario/contraseña en la URL
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
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    Coordinador coordinador = gson.fromJson(reader, Coordinador.class);
                    return coordinador;
                }
            } else {
                System.out.println("Respuesta del servidor: " + conn.getResponseCode());
            }

        } catch (Exception e) {
            System.out.println("Error al buscar coordinador por usuario y contraseña: " + e.getMessage());
        }
        return null;
    }
}
