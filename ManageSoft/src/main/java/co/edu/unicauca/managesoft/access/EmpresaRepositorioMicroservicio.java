package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.infra.TokenGenerator;
import co.edu.unicauca.managesoft.infra.TokenManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpresaRepositorioMicroservicio implements IEmpresaRepositorio {

    private final String baseUrl = "http://localhost:8086/api/empresas";
    private final Gson gson = new Gson();
    private IProyectoRepositorio repositorioProyecto;

    

    @Override
    public boolean guardar(Empresa nuevaEmpresa) {
        try {
            

            URL url = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            String jsonInput = gson.toJson(nuevaEmpresa);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            return conn.getResponseCode() == 200 || conn.getResponseCode() == 201;
        } catch (Exception e) {
            System.out.println("Error al guardar empresa: " + e.getMessage());
            return false;
        }
    }

 @Override
public Empresa buscarEmpresa(String nombreUsuario) {
    
    
    try {
        // Construyes la URL con el username como path variable
        URL url = new URL(baseUrl + "/login/user/" + URLEncoder.encode(nombreUsuario, "UTF-8"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST"); // Sigue siendo POST
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(false); // No necesitas enviar cuerpo

        if (conn.getResponseCode() == 200) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                Empresa empresita = gson.fromJson(reader, Empresa.class);
                empresita.setRepositorioProyectos(repositorioProyecto);
                return empresita;
            }
        }

    } catch (Exception e) {
        System.out.println("Error al buscar empresa por username: " + e.getMessage());
    }
    return null;
}

    @Override
    public Empresa buscarEmpresa(String nombreUsuario, String contrasenaUsuario) {
        
        try {
            
            String jwtToken = TokenGenerator.obtenerToken(nombreUsuario, contrasenaUsuario);
            TokenManager.setToken(jwtToken);
            
            URL url = new URL(baseUrl + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + jwtToken);
            conn.setDoOutput(true);

            Map<String, String> datos = new HashMap<>();
            datos.put("username", nombreUsuario);
            datos.put("password", contrasenaUsuario);
            String jsonInput = gson.toJson(datos);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes("utf-8"));
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    Empresa empresita =  gson.fromJson(reader, Empresa.class);
                    empresita.setRepositorioProyectos(repositorioProyecto);
                    
                      
                    
                    return empresita;
                }
            }

        } catch (Exception e) {
            System.out.println("Error al buscar empresa por usuario y contraseña: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Empresa> listarEmpresas() {
        try {
            
            String token = TokenManager.getToken();
            
            URL url = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            
            
            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    // Usamos un TypeToken para poder deserializar el array de empresas
                    Type listType = new TypeToken<List<Empresa>>(){}.getType();
                    List<Empresa> empresas = gson.fromJson(reader, listType);
                    for (Empresa empresa : empresas) {
                        empresa.setRepositorioProyectos(repositorioProyecto);
                    }
                    return empresas;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar empresas: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void setRepositorioProyecto(IProyectoRepositorio repositorioProyecto) {
        this.repositorioProyecto = repositorioProyecto;
    }

    @Override
    public IProyectoRepositorio getRepositorioProyecto() {
        return this.repositorioProyecto;
    }
}
