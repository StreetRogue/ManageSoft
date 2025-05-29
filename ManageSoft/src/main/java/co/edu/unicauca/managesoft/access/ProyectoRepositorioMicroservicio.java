package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.*;
import co.edu.unicauca.managesoft.infra.EstadoProyectoDeserializer;
import co.edu.unicauca.managesoft.infra.ProyectTable;
import co.edu.unicauca.managesoft.infra.TokenGenerator;
import co.edu.unicauca.managesoft.infra.TokenManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProyectoRepositorioMicroservicio implements IProyectoRepositorio {

    private static final String BASE_URL = "http://localhost:8086/api";
    private final Gson gson = new GsonBuilder().registerTypeAdapter(IEstadoProyecto.class, new EstadoProyectoDeserializer())
            .create();
    
    

    @Override
    public boolean guardarProyecto(Proyecto nuevoProyecto, Empresa empresa) {
        // URL del microservicio de Empresa para guardar el proyecto
        String urlString = BASE_URL + "/empresas/" + empresa.getNitEmpresa() + "/proyectos";

        // Crear los datos a enviar (en este caso en formato JSON)
        String jsonData = "{"
                + "\"nombreProyecto\": \"" + nuevoProyecto.getNombreProyecto() + "\","
                + "\"descripcionProyecto\": \"" + nuevoProyecto.getDescripcionProyecto() + "\","
                + "\"maximoMesesProyecto\": " + nuevoProyecto.getMaximoMesesProyecto() + ","
                + "\"presupuestoProyecto\": " + nuevoProyecto.getPresupuestoProyecto() + ","
                + "\"resumenProyecto\": \"" + nuevoProyecto.getResumenProyecto() + "\","
                + "\"objetivoProyecto\": \"" + nuevoProyecto.getObjetivoProyecto() + "\","
                + "\"fechaPublicacionProyecto\": \"" + nuevoProyecto.getFechaPublicacionProyecto() + "\""
                + "}";

        try {

            String token = TokenManager.getToken();

            // Crear la URL y la conexión HTTP
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setConnectTimeout(5000); // Timeout de conexión
            connection.setReadTimeout(5000); // Timeout de lectura
            connection.setDoOutput(true); // Indica que se enviarán datos
            connection.setRequestProperty("Content-Type", "application/json");

            // Enviar los datos JSON al microservicio
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Obtener la respuesta del microservicio
            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) { // Si la respuesta es 201 Created
                return true;
            } else {
                // Si la respuesta no es exitosa, manejar el error
                System.out.println("Error al guardar el proyecto: " + status);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Proyecto> listarProyectos(String nitEmpresa) {
        try {

            String token = TokenManager.getToken();

            URL url = new URL(BASE_URL + "/empresas/" + URLEncoder.encode(nitEmpresa, "UTF-8") + "/proyectos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    Type listType = new TypeToken<List<Proyecto>>() {
                    }.getType();
                    return gson.fromJson(reader, listType);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar proyectos: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public List<ProyectTable> listarProyectosGeneral() {
        try {

            String token = TokenManager.getToken();

            URL url = new URL(BASE_URL + "/proyectos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                    List<ProyectTable> proyectos = new ArrayList<>();

                    for (JsonElement elem : jsonArray) {
                        JsonObject obj = elem.getAsJsonObject();
                        ProyectTable proyecto = new ProyectTable();

                        proyecto.setIdProyecto(obj.has("id") && !obj.get("id").isJsonNull() ? obj.get("id").getAsInt() : 0);
                        proyecto.setNombreProyecto(obj.has("nombre") && !obj.get("nombre").isJsonNull() ? obj.get("nombre").getAsString() : "");
                        proyecto.setResumenProyecto(obj.has("resumen") && !obj.get("resumen").isJsonNull() ? obj.get("resumen").getAsString() : "");
                        proyecto.setObjetivoProyecto(obj.has("objetivo") && !obj.get("objetivo").isJsonNull() ? obj.get("objetivo").getAsString() : "");
                        proyecto.setDescripcionProyecto(obj.has("descripcion") && !obj.get("descripcion").isJsonNull() ? obj.get("descripcion").getAsString() : "");
                        proyecto.setMaximoMesesProyecto(obj.has("duracionMeses") && !obj.get("duracionMeses").isJsonNull() ? obj.get("duracionMeses").getAsString() : "0");
                        proyecto.setPresupuestoProyecto(obj.has("presupuesto") && !obj.get("presupuesto").isJsonNull() ? obj.get("presupuesto").getAsString() : "0.0");
                        proyecto.setFechaPublicacionProyecto(obj.has("fechaPublicacion") && !obj.get("fechaPublicacion").isJsonNull() ? obj.get("fechaPublicacion").getAsString() : "");

                        // Empresa
                        if (obj.has("empresa") && !obj.get("empresa").isJsonNull()) {
                            JsonObject empresaJson = obj.getAsJsonObject("empresa");
                            Empresa empresa = new Empresa();
                            empresa.setNitEmpresa(getSafeString(empresaJson, "nitEmpresa"));
                            empresa.setNombreEmpresa(getSafeString(empresaJson, "nombreEmpresa"));
                            empresa.setEmailEmpresa(getSafeString(empresaJson, "emailEmpresa"));
                            empresa.setSectorEmpresa(getSafeString(empresaJson, "sectorEmpresa"));
                            empresa.setContactoEmpresa(getSafeString(empresaJson, "telefonoContactoEmpresa"));
                            empresa.setNombreContactoEmpresa(getSafeString(empresaJson, "nombreContactoEmpresa"));
                            empresa.setApellidoContactoEmpresa(getSafeString(empresaJson, "apellidoContactoEmpresa"));
                            empresa.setCargoContactoEmpresa(getSafeString(empresaJson, "cargoContactoEmpresa"));

                            proyecto.setEmpresa(empresa);
                            proyecto.setNombreEmpresa(empresa.getNombreEmpresa());
                        }

                        // Estado
                        String estado = obj.has("estado") && !obj.get("estado").isJsonNull() ? obj.get("estado").getAsString() : "RECIBIDO";
                        IEstadoProyecto estadoProyecto = obtenerEstadoProyecto(estado);
                        proyecto.setEstadoProyecto(estadoProyecto);

                        // Este campo no viene del microservicio, por ahora se deja en falso
                        proyecto.setCorreoEnviado(false);

                        proyectos.add(proyecto);
                    }

                    return proyectos;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar proyectos general: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<ProyectTable> listarProyectosGeneralEstudiantes() {
        try {

            String token = TokenManager.getToken();

            URL url = new URL(BASE_URL + "/proyectos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                    List<ProyectTable> proyectos = new ArrayList<>();

                    for (JsonElement elem : jsonArray) {
                        JsonObject obj = elem.getAsJsonObject();
                        ProyectTable proyecto = new ProyectTable();

                        proyecto.setIdProyecto(obj.has("id") && !obj.get("id").isJsonNull() ? obj.get("id").getAsInt() : 0);
                        proyecto.setNombreProyecto(obj.has("nombre") && !obj.get("nombre").isJsonNull() ? obj.get("nombre").getAsString() : "");
                        proyecto.setResumenProyecto(obj.has("resumen") && !obj.get("resumen").isJsonNull() ? obj.get("resumen").getAsString() : "");
                        proyecto.setObjetivoProyecto(obj.has("objetivo") && !obj.get("objetivo").isJsonNull() ? obj.get("objetivo").getAsString() : "");
                        proyecto.setDescripcionProyecto(obj.has("descripcion") && !obj.get("descripcion").isJsonNull() ? obj.get("descripcion").getAsString() : "");
                        proyecto.setMaximoMesesProyecto(obj.has("duracionMeses") && !obj.get("duracionMeses").isJsonNull() ? obj.get("duracionMeses").getAsString() : "0");
                        proyecto.setPresupuestoProyecto(obj.has("presupuesto") && !obj.get("presupuesto").isJsonNull() ? obj.get("presupuesto").getAsString() : "0.0");
                        proyecto.setFechaPublicacionProyecto(obj.has("fechaPublicacion") && !obj.get("fechaPublicacion").isJsonNull() ? obj.get("fechaPublicacion").getAsString() : "");

                        // Empresa
                        if (obj.has("empresa") && !obj.get("empresa").isJsonNull()) {
                            JsonObject empresaJson = obj.getAsJsonObject("empresa");
                            Empresa empresa = new Empresa();
                            empresa.setNitEmpresa(getSafeString(empresaJson, "nitEmpresa"));
                            empresa.setNombreEmpresa(getSafeString(empresaJson, "nombreEmpresa"));
                            empresa.setEmailEmpresa(getSafeString(empresaJson, "emailEmpresa"));
                            empresa.setSectorEmpresa(getSafeString(empresaJson, "sectorEmpresa"));
                            empresa.setContactoEmpresa(getSafeString(empresaJson, "telefonoContactoEmpresa"));
                            empresa.setNombreContactoEmpresa(getSafeString(empresaJson, "nombreContactoEmpresa"));
                            empresa.setApellidoContactoEmpresa(getSafeString(empresaJson, "apellidoContactoEmpresa"));
                            empresa.setCargoContactoEmpresa(getSafeString(empresaJson, "cargoContactoEmpresa"));

                            proyecto.setEmpresa(empresa);
                            proyecto.setNombreEmpresa(empresa.getNombreEmpresa());
                        }

                        // Estado
                        String estado = obj.has("estado") && !obj.get("estado").isJsonNull() ? obj.get("estado").getAsString() : "RECIBIDO";
                        IEstadoProyecto estadoProyecto = obtenerEstadoProyecto(estado);
                        proyecto.setEstadoProyecto(estadoProyecto);

                        // Este campo no viene del microservicio, por ahora se deja en falso
                        proyecto.setCorreoEnviado(false);
                        if (proyecto.getEstadoProyecto().toString().equals("ACEPTADO")) {
                            proyectos.add(proyecto);
                        }
                    }

                    return proyectos;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar proyectos general: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

// Método de utilidad para extraer strings de forma segura
    private String getSafeString(JsonObject obj, String key) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsString() : "";
    }

    @Override
    public IEstadoProyecto obtenerEstadoProyecto(String estado) {

        switch (estado) {
            case "RECIBIDO":
                return new EstadoRecibido();
            case "RECHAZADO":
                return new EstadoRechazado();
            case "EN_EJECUCION":
                return new EstadoEnEjecucion();
            case "CERRADO":
                return new EstadoCerrado();
            case "ACEPTADO":
                return new EstadoAceptado();
            default:
                throw new IllegalArgumentException("Estado no reconocido: " + estado);
        }
    }

    @Override
    public boolean actualizarEstadoProyecto(Proyecto proyecto, String nuevoEstado) {
        try {

            String token = TokenManager.getToken();

            URL url = new URL(BASE_URL + "/proyectos/" + proyecto.getIdProyecto() + "/estado");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);

            // Enviar el string plano entre comillas
            String json = "\"" + nuevoEstado + "\"";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            System.err.println("Error al actualizar estado del proyecto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Proyecto encontrarPorId(String idProyectoStr) {
        try {

            String token = TokenManager.getToken();

            Long idProyecto = Long.parseLong(idProyectoStr);
            String endpoint = BASE_URL + "/proyectos/" + idProyecto + "/def";
            System.out.println("Consultando: " + endpoint); // Debug URL

            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = con.getResponseCode();
            System.out.println("Código HTTP: " + responseCode); // Debug status

            if (responseCode != 200) {
                // Leer mensaje de error si existe
                try (BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(con.getErrorStream()))) {
                    String error = errorReader.lines().collect(Collectors.joining());
                    System.out.println("Error del servidor: " + error);
                }
                return null;
            }

            // Leer respuesta
            String jsonResponse;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                jsonResponse = reader.lines().collect(Collectors.joining());
                System.out.println("JSON recibido: " + jsonResponse); // Debug JSON
            }

            // Parsear JSON
            JsonObject json = JsonParser.parseString(jsonResponse).getAsJsonObject();

            // Crear objeto Proyecto
            Proyecto proyecto = new Proyecto();

            // Mapear campos básicos (con verificaciones)
            proyecto.setIdProyecto(json.get("idProyecto").getAsInt());
            proyecto.setNombreProyecto(json.get("nombreProyecto").getAsString());

            // Campos opcionales con valores por defecto
            proyecto.setResumenProyecto(json.has("resumenProyecto") && !json.get("resumenProyecto").isJsonNull()
                    ? json.get("resumenProyecto").getAsString() : "");
            proyecto.setObjetivoProyecto(json.has("objetivoProyecto") && !json.get("objetivoProyecto").isJsonNull()
                    ? json.get("objetivoProyecto").getAsString() : "");
            proyecto.setDescripcionProyecto(json.has("descripcionProyecto") && !json.get("descripcionProyecto").isJsonNull()
                    ? json.get("descripcionProyecto").getAsString() : "");

            // Campos numéricos como strings
            proyecto.setMaximoMesesProyecto(json.has("maximoMesesProyecto") && !json.get("maximoMesesProyecto").isJsonNull()
                    ? json.get("maximoMesesProyecto").getAsString() : "0");
            proyecto.setPresupuestoProyecto(json.has("presupuestoProyecto") && !json.get("presupuestoProyecto").isJsonNull()
                    ? json.get("presupuestoProyecto").getAsString() : "0.0");

            // Fecha
            proyecto.setFechaPublicacionProyecto(json.has("fechaPublicacionProyecto") && !json.get("fechaPublicacionProyecto").isJsonNull()
                    ? json.get("fechaPublicacionProyecto").getAsString() : "");

            // Estado
            String estadoStr = json.get("estadoProyecto").getAsString();
            IEstadoProyecto estadoProyecto = obtenerEstadoProyecto(estadoStr);
            proyecto.setEstadoProyecto(estadoProyecto);

            // Empresa
            if (json.has("empresa") && !json.get("empresa").isJsonNull()) {
                JsonObject empresaJson = json.getAsJsonObject("empresa");
                Empresa empresa = new Empresa();

                empresa.setNitEmpresa(empresaJson.get("nitEmpresa").getAsString());
                empresa.setNombreEmpresa(empresaJson.get("nombreEmpresa").getAsString());
                empresa.setEmailEmpresa(empresaJson.get("emailEmpresa").getAsString());

                // Campos opcionales de empresa
                empresa.setSectorEmpresa(empresaJson.has("sectorEmpresa") && !empresaJson.get("sectorEmpresa").isJsonNull()
                        ? empresaJson.get("sectorEmpresa").getAsString() : "");
                empresa.setContactoEmpresa(empresaJson.has("contactoEmpresa") && !empresaJson.get("contactoEmpresa").isJsonNull()
                        ? empresaJson.get("contactoEmpresa").getAsString() : "");
                empresa.setNombreContactoEmpresa(empresaJson.has("nombreContactoEmpresa") && !empresaJson.get("nombreContactoEmpresa").isJsonNull()
                        ? empresaJson.get("nombreContactoEmpresa").getAsString() : "");
                empresa.setApellidoContactoEmpresa(empresaJson.has("apellidoContactoEmpresa") && !empresaJson.get("apellidoContactoEmpresa").isJsonNull()
                        ? empresaJson.get("apellidoContactoEmpresa").getAsString() : "");
                empresa.setCargoContactoEmpresa(empresaJson.has("cargoContactoEmpresa") && !empresaJson.get("cargoContactoEmpresa").isJsonNull()
                        ? empresaJson.get("cargoContactoEmpresa").getAsString() : "");

                proyecto.setEmpresa(empresa);
            }

            // Historial de estados
            if (json.has("historial") && json.get("historial").isJsonArray()) {
                JsonArray historialArray = json.getAsJsonArray("historial");
                for (JsonElement element : historialArray) {
                    JsonObject estadoObj = element.getAsJsonObject();
                    if (estadoObj.has("estado")) {
                        String estadoHistorial = estadoObj.get("estado").getAsString();
                        IEstadoProyecto estado = obtenerEstadoProyecto(estadoHistorial);
                        proyecto.getCaretaker().addMemento(proyecto.saveToMemento(estado));
                    }
                }
            }

            return proyecto;

        } catch (NumberFormatException e) {
            System.err.println("Error: ID de proyecto no es un número válido");
        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        } catch (JsonParseException e) {
            System.err.println("Error parseando JSON: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int cantProyectoporEstado(String estado, String periodoAcademico) {
        try {

            String token = TokenManager.getToken();

            // Construir la URL con ambos parámetros correctamente codificados
            String urlStr = BASE_URL + "/proyectos/contador2?estado="
                    + URLEncoder.encode(estado, "UTF-8")
                    + "&periodo=" + URLEncoder.encode(periodoAcademico, "UTF-8");

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    return Integer.parseInt(reader.readLine());
                }
            } else {
                System.err.println("Respuesta HTTP inesperada: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            System.err.println("Error al contar proyectos por estado: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int cantProyectosEvaluados(String periodoAcademico) {
        try {
            int proyectosAceptados = cantProyectoporEstado("aceptado", periodoAcademico);
            int proyectosRechazados = cantProyectoporEstado("rechazado", periodoAcademico);
            int proyectosEnEjecucion = cantProyectoporEstado("en_ejecucion", periodoAcademico);
            int proyectosCerrados = cantProyectoporEstado("cerrado", periodoAcademico);

            return (proyectosAceptados + proyectosCerrados + proyectosEnEjecucion + proyectosRechazados);
        } catch (Exception e) {
            System.err.println("Error " + e.getMessage());

        }
        return 0;
    }

    @Override
    public int cantTasaAceptacion(String periodoAcademico) {
        try {
            int proyectosAceptados = cantProyectoporEstado("aceptado", periodoAcademico);
            int proyectosRechazados = cantProyectoporEstado("rechazado", periodoAcademico);
            if (proyectosAceptados == 0 && proyectosRechazados == 0) {
                return 0;
            }

            return (proyectosAceptados * 100 / (proyectosAceptados + proyectosRechazados));
        } catch (Exception e) {
            System.err.println("Error al obtener la tasa de aceptación: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int avgProyectoDiasEnAceptar(String periodoAcademico) {
        try {
            
            String token = TokenManager.getToken();
            
            String urlStr = BASE_URL + "/proyectos/estadisticas/promedio-aceptacion?periodo=" + URLEncoder.encode(periodoAcademico, "UTF-8");
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
            System.err.println("Error al obtener promedio de días en aceptar: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

}
