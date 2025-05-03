/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.Gson;
/**
 *
 * @author juane
 */
public class UsuarioRepositorioMicroservicio implements IUsuarioRepositorio{
    
    
    ////PENE
    private static final String URL_MICROSERVICIO = "http://localhost:8081/api/usuarios/";

     @Override
    public boolean registrarUsuario(Usuario nuevoUsuario) {
        String urlStr = URL_MICROSERVICIO + "register";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Crear el objeto JSON para enviar al microservicio
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(nuevoUsuario);

            // Enviar la solicitud POST
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Leer la respuesta del microservicio
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true; // Registro exitoso
            } else {
                // Si no es exitoso, puede agregar aquí un log o mostrar un mensaje
                return false; // Fallo en el registro
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error al intentar registrar
        }
    }


    @Override
    public Usuario iniciarSesion(String nombreUsuario, String contrasenaUsuario) {
        String urlStr = URL_MICROSERVICIO + "login";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Crear el objeto JSON para enviar al microservicio
            String jsonInputString = "{\"nombreUsuario\": \"" + nombreUsuario + "\", \"contrasena\": \"" + contrasenaUsuario + "\"}";

            // Enviar la solicitud POST
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Leer la respuesta del microservicio
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta y convertirla en un objeto Usuario
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }


                    Gson gson = new Gson();
                    Usuario usuario = gson.fromJson(response.toString(), Usuario.class);

                    if (usuario.getTipoUsuario() == enumTipoUsuario.EMPRESA) {
                        return gson.fromJson(response.toString(), Empresa.class);
                    } else if (usuario.getTipoUsuario() == enumTipoUsuario.COORDINADOR) {

                        return gson.fromJson(response.toString(), Coordinador.class);
                    } else if (usuario.getTipoUsuario() == enumTipoUsuario.ESTUDIANTE) {

                        return gson.fromJson(response.toString(), Estudiante.class);
                    }

                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Error al intentar hacer login
        }
    }
    
    
    
    
    
    
    
    
    

    @Override
    public void setRepositorioEmpresa(IEmpresaRepositorio repositorioEmpresa) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setRepositorioCoordinador(ICoordinadorRepositorio repositorioCoordinador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setRepositorioEstudiante(IEstudianteRepositorio repositorioEstudiante) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setRepositorioProyecto(IProyectoRepositorio repositorioProyecto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setRepositorioCorreo(INotificacionRepositorio repositorioCorreo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
