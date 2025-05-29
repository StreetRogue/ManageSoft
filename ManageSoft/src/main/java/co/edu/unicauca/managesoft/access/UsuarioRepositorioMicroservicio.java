package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.*;
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;
import com.google.gson.*;
import co.edu.unicauca.managesoft.infra.TokenGenerator;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UsuarioRepositorioMicroservicio implements IUsuarioRepositorio {

    private static final String URL_MICROSERVICIO = "http://localhost:8086/api/usuarios/";

    private static IEmpresaRepositorio repositorioEmpresa;
    private static ICoordinadorRepositorio repositorioCoordinador;
    private static IEstudianteRepositorio repositorioEstudiante;
    private static IProyectoRepositorio repositorioProyecto;
    private static INotificacionRepositorio repositorioCorreo;
    private final Gson gson = new Gson();

    @Override
    public boolean registrarUsuario(Usuario nuevoUsuario) {
        String urlStr = URL_MICROSERVICIO + "register";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            Gson gson = new Gson();
            String jsonInputString = gson.toJson(nuevoUsuario);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

            String jsonInputString = "{\"nombreUsuario\": \"" + nombreUsuario + "\", \"contrasena\": \"" + contrasenaUsuario + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    Gson gson = new Gson();
                    Usuario usuario = gson.fromJson(response.toString(), Usuario.class);
                    System.out.println("Usuario: " + usuario.getNombreUsuario());
                    System.out.println("Contrasenia: " + usuario.getContrasenaUsuario());
                    
                    
                    
                    
                    switch (usuario.getTipoUsuario()) {
                        case ESTUDIANTE:
                            return repositorioEstudiante.buscarEstudiante(usuario.getNombreUsuario(), usuario.getContrasenaUsuario());
                        case COORDINADOR:
                            return repositorioCoordinador.buscarCoordinador(usuario.getNombreUsuario(), usuario.getContrasenaUsuario());
                        case EMPRESA:
                            return repositorioEmpresa.buscarEmpresa(usuario.getNombreUsuario(), usuario.getContrasenaUsuario());
                        default:
                            return null;
                    }
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void setRepositorioEmpresa(IEmpresaRepositorio repositorioEmpresa) {
        UsuarioRepositorioMicroservicio.repositorioEmpresa = repositorioEmpresa;
    }

    @Override
    public void setRepositorioCoordinador(ICoordinadorRepositorio repositorioCoordinador) {
        UsuarioRepositorioMicroservicio.repositorioCoordinador = repositorioCoordinador;
    }

    @Override
    public void setRepositorioEstudiante(IEstudianteRepositorio repositorioEstudiante) {
        UsuarioRepositorioMicroservicio.repositorioEstudiante = repositorioEstudiante;
    }

    @Override
    public void setRepositorioProyecto(IProyectoRepositorio repositorioProyecto) {
        UsuarioRepositorioMicroservicio.repositorioProyecto = repositorioProyecto;
    }

    @Override
    public void setRepositorioCorreo(INotificacionRepositorio repositorioCorreo) {
        UsuarioRepositorioMicroservicio.repositorioCorreo = repositorioCorreo;
    }
}
