/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;
import co.edu.unicauca.managesoft.infra.TokenGenerator;
import co.edu.unicauca.managesoft.infra.TokenManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jutak
 */
public class EstudianteRepositorioMicroservicio implements IEstudianteRepositorio {

    private final String baseUrl = "http://localhost:8086/api/estudiante";
    private final Gson gson = new Gson();
    private IProyectoRepositorio repositorioProyecto;

    public class EstudianteDto {

        private String nombreUsuario;
        private String contrasenaUsuario;
        private enumTipoUsuario tipoUsuario;
        private Long codigoSimcaEstudiante;
        private String nombreEstudiante;
        private String apellidoEstudiante;
        private String emailEstudiante;
        private List<Long> proyectosPostulados;
        private List<Long> proyectosAceptados;

        public EstudianteDto(String nombreUsuario, String contrasenaUsuario, Long codigoSimcaEstudiante, String nombreEstudiante, String apellidoEstudiante, String emailEstudiante) {
            this.nombreUsuario = nombreUsuario;
            this.contrasenaUsuario = contrasenaUsuario;
            this.tipoUsuario = enumTipoUsuario.ESTUDIANTE;
            this.codigoSimcaEstudiante = codigoSimcaEstudiante;
            this.nombreEstudiante = nombreEstudiante;
            this.apellidoEstudiante = apellidoEstudiante;
            this.emailEstudiante = emailEstudiante;
            this.proyectosPostulados = new ArrayList<>();
            this.proyectosAceptados = new ArrayList<>();
        }

        public String getNombreEstudiante() {
            return nombreEstudiante;
        }

        public void setNombreEstudiante(String nombreEstudiante) {
            this.nombreEstudiante = nombreEstudiante;
        }

        public String getApellidoEstudiante() {
            return apellidoEstudiante;
        }

        public void setApellidoEstudiante(String apellidoEstudiante) {
            this.apellidoEstudiante = apellidoEstudiante;
        }

        public Long getCodigoSimcaEstudiante() {
            return codigoSimcaEstudiante;
        }

        public void setCodigoSimcaEstudiante(Long codigoSimcaEstudiante) {
            this.codigoSimcaEstudiante = codigoSimcaEstudiante;
        }

        public String getEmailEstudiante() {
            return emailEstudiante;
        }

        public void setEmailEstudiante(String emailEstudiante) {
            this.emailEstudiante = emailEstudiante;
        }

        public List<Long> getProyectosPostulados() {
            return proyectosPostulados;
        }

        public void setProyectosPostulados(List<Long> proyectosPostulados) {
            this.proyectosPostulados = proyectosPostulados;
        }

        public List<Long> getProyectosAceptados() {
            return proyectosAceptados;
        }

        public void setProyectosAceptados(List<Long> proyectosAceptados) {
            this.proyectosAceptados = proyectosAceptados;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public String getContrasenaUsuario() {
            return contrasenaUsuario;
        }

        public void setContrasenaUsuario(String contrasenaUsuario) {
            this.contrasenaUsuario = contrasenaUsuario;
        }

        public Estudiante convertirAEstudiante() {
            // Crear un objeto Estudiante a partir del DTO
            Estudiante estudiante = new Estudiante(
                    this.getNombreEstudiante(),
                    this.getApellidoEstudiante(),
                    this.getCodigoSimcaEstudiante().toString(),
                    this.getEmailEstudiante(),
                    this.getNombreUsuario(),
                    this.getContrasenaUsuario()
            );

            List<Proyecto> postuladosEstudiante = new ArrayList<>();
            List<Proyecto> aceptados = new ArrayList<>();

            // Convertir las listas de IDs de proyectos a objetos Proyecto
            for (Long proyecto : this.getProyectosPostulados()) {
                postuladosEstudiante.add(repositorioProyecto.encontrarPorId(proyecto.toString()));
            }

            for (Long proyecto : this.getProyectosAceptados()) {
                aceptados.add(repositorioProyecto.encontrarPorId(proyecto.toString()));
            }
            estudiante.setProyectosPostulados(postuladosEstudiante);
            estudiante.setProyectosAceptados(aceptados);

            return estudiante;
        }
    }

    @Override
    public boolean guardar(Estudiante nuevoEstudiante) {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            String jsonInput = gson.toJson(nuevoEstudiante);
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
    public Estudiante buscarEstudiante(String nombreUsuario, String contrasenaUsuario) {
        try {

            String jwtToken = TokenGenerator.obtenerToken(nombreUsuario, contrasenaUsuario);
            TokenManager.setToken(jwtToken);

            // Crear la URL del servicio
            URL url = new URL(baseUrl + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + jwtToken);
            conn.setDoOutput(true);

            // Crear los datos de solicitud como JSON
            Map<String, String> datos = new HashMap<>();
            datos.put("username", nombreUsuario);
            datos.put("password", contrasenaUsuario);
            String jsonInput = gson.toJson(datos);

            // Enviar la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes("utf-8"));
            }

            // Manejar la respuesta
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    EstudianteDto estudianteDto = gson.fromJson(reader, EstudianteDto.class);
                    return estudianteDto.convertirAEstudiante();
                }
            } else {
                System.out.println("Error: Código de respuesta HTTP " + conn.getResponseCode());
            }
        } catch (Exception e) {
            System.out.println("Error al buscar estudiante por usuario y contraseña: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Estudiante> listarEstudiantes() {
        try {

            String token = TokenManager.getToken();

            URL url = new URL(baseUrl + "/listar");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    Type listType = new TypeToken<List<EstudianteDto>>() {
                    }.getType();
                    List<EstudianteDto> estudiantesDto = gson.fromJson(reader, listType);
                    List<Estudiante> listaEstudiantes = new ArrayList<>();

                    for (EstudianteDto estudianteDto : estudiantesDto) {
                        Estudiante estudiante = estudianteDto.convertirAEstudiante();
                        listaEstudiantes.add(estudiante);
                    }

                    return listaEstudiantes;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar empresas: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public int cantidadEstudiantes() {
        try {
            
            String token = TokenManager.getToken();
            
            String urlStr = (baseUrl + "/total");
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                    String line = reader.readLine();
                    if (line != null && !line.isEmpty()) {
                        return Integer.parseInt(line.trim());
                    }
                }
            } else {
                System.err.println("Respuesta HTTP inesperada: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener total de estudiantes: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

}
