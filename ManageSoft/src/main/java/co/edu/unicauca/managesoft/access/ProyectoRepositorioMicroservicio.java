package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.*;
import co.edu.unicauca.managesoft.infra.EstadoProyectoDeserializer;
import co.edu.unicauca.managesoft.infra.ProyectTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

public class ProyectoRepositorioMicroservicio implements IProyectoRepositorio {

    private static final String BASE_URL = "http://localhost:8082/api";
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
            // Crear la URL y la conexión HTTP
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
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
            URL url = new URL(BASE_URL + "/empresas/" + URLEncoder.encode(nitEmpresa, "UTF-8") + "/proyectos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

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
            URL url = new URL(BASE_URL + "/proyectos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

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
            URL url = new URL(BASE_URL + "/proyectos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

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
            URL url = new URL(BASE_URL + "/proyectos/" + proyecto.getIdProyecto() + "/estado");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

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

    @Override // Este ahora tambien tiene q devolver el historial
    public Proyecto encontrarPorId(String idProyecto) {
        try {
            URL url = new URL(BASE_URL + "/proyectos/" + idProyecto);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    Proyecto proyecto = gson.fromJson(reader, Proyecto.class);
                    return proyecto;
                }
            }

            return null;
        } catch (Exception e) {
            System.err.println("Error al actualizar estado del proyecto: " + e.getMessage());
            return null;
        }
    }

    @Override
    public int cantProyectoporEstado(String estado, String periodoAcademico) {
        try {
            // Construir la URL correctamente con el parámetro 'estado'
            URL url = new URL(BASE_URL + "/proyectos/contador?estado=" + URLEncoder.encode(estado, "UTF-8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    return Integer.parseInt(reader.readLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al contar proyectos por estado: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int cantProyectosEvaluados() {
//        try {
//           int proyectosAceptados = cantProyectoporEstado("aceptado");
//           int proyectosRechazados = cantProyectoporEstado("rechazado");
//           int proyectosEnEjecucion = cantProyectoporEstado("en_ejecucion");
//           int proyectosCerrados = cantProyectoporEstado("cerrado");
//           
//           return (proyectosAceptados+proyectosCerrados+proyectosEnEjecucion+proyectosRechazados);
//        } catch (Exception e) {
//            System.err.println("Error " + e.getMessage());
//            
//        }
        return 0;
    }

    @Override
    public int cantTasaAceptacion() {
//        try {
//           int proyectosAceptados = cantProyectoporEstado("aceptado");
//           int proyectosRechazados = cantProyectoporEstado("rechazado");
//           
//           return(proyectosAceptados*100/(proyectosAceptados + proyectosRechazados));
//        } catch (Exception e) {
//            System.err.println("Error al obtener la tasa de aceptación: " + e.getMessage());
//            e.printStackTrace();
//        }
        return 0;
    }

    @Override
    public int avgProyectoDiasEnAceptar() {
        return 0;
    }

}
