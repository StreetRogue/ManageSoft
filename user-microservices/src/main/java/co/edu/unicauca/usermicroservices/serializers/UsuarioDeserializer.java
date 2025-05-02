package co.edu.unicauca.usermicroservices.serializers;

import co.edu.unicauca.usermicroservices.entities.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class UsuarioDeserializer extends JsonDeserializer<Usuario> {

    @Override
    public Usuario deserialize(JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
        // Acceder al JSON como un árbol de nodos
        JsonNode node = p.getCodec().readTree(p);

        // Obtener el tipo de usuario con verificación de null
        String tipoUsuario = getNodeText(node, "tipoUsuario");
        System.out.println("Tipo de usuario deserializado: " + tipoUsuario);
        System.out.println("Cuerpo del JSON recibido desde el deserializer: " + node.toString());

        // Convertir el tipo de usuario a enum
        enumTipoUsuario tipoUsuarioEnum = enumTipoUsuario.valueOf(tipoUsuario);

        // Crear el objeto Usuario según el tipo de usuario
        Usuario usuario = null;

        switch (tipoUsuario) {
            case "ESTUDIANTE":
                usuario = new Estudiante();
                ((Estudiante) usuario).setNombreUsuario(getNodeText(node, "nombreUsuario"));
                ((Estudiante) usuario).setContrasenaUsuario(getNodeText(node, "contrasenaUsuario"));
                ((Estudiante) usuario).setCodigoSimcaEstudiante(getNodeLong(node, "codigoSimcaEstudiante")); // Cambiado a codigoSimcaEstudiante
                ((Estudiante) usuario).setNombreEstudiante(getNodeText(node, "nombreEstudiante"));
                ((Estudiante) usuario).setApellidoEstudiante(getNodeText(node, "apellidoEstudiante"));
                ((Estudiante) usuario).setEmailEstudiante(getNodeText(node, "emailEstudiante"));
                ((Estudiante) usuario).setTipoUsuario(tipoUsuarioEnum);
                break;

            case "COORDINADOR":
                usuario = new Coordinador();
                ((Coordinador) usuario).setNombreUsuario(getNodeText(node, "nombreUsuario"));
                ((Coordinador) usuario).setContrasenaUsuario(getNodeText(node, "contrasenaUsuario"));
                ((Coordinador) usuario).setIdCoordinador(getNodeInt(node, "idCoordinador"));
                ((Coordinador) usuario).setNombreCoordinador(getNodeText(node, "nombreCoordinador"));
                ((Coordinador) usuario).setApellidoCoordinador(getNodeText(node, "apellidoCoordinador"));
                ((Coordinador) usuario).setEmailCoordinador(getNodeText(node, "emailCoordinador"));
                ((Coordinador) usuario).setTelefonoCoordinador(getNodeText(node, "telefonoCoordinador"));
                ((Coordinador) usuario).setTipoUsuario(tipoUsuarioEnum);
                break;

            case "EMPRESA":
                usuario = new Empresa();
                ((Empresa) usuario).setNombreUsuario(getNodeText(node, "nombreUsuario"));
                ((Empresa) usuario).setContrasenaUsuario(getNodeText(node, "contrasenaUsuario"));
                ((Empresa) usuario).setNitEmpresa(getNodeText(node, "nitEmpresa"));
                ((Empresa) usuario).setNombreEmpresa(getNodeText(node, "nombreEmpresa"));
                ((Empresa) usuario).setEmailEmpresa(getNodeText(node, "emailEmpresa"));
                ((Empresa) usuario).setSectorEmpresa(getNodeText(node, "sectorEmpresa"));
                ((Empresa) usuario).setContactoEmpresa(getNodeText(node, "contactoEmpresa"));
                ((Empresa) usuario).setNombreContactoEmpresa(getNodeText(node, "nombreContactoEmpresa"));
                ((Empresa) usuario).setApellidoContactoEmpresa(getNodeText(node, "apellidoContactoEmpresa"));
                ((Empresa) usuario).setCargoContactoEmpresa(getNodeText(node, "cargoContactoEmpresa"));
                ((Empresa) usuario).setTipoUsuario(tipoUsuarioEnum);
                break;

            default:
                throw new IllegalArgumentException("Tipo de usuario desconocido");
        }

        return usuario;
    }

    // Metodo auxiliar para obtener el texto de un nodo, con manejo de valores nulos
    private String getNodeText(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null && !fieldNode.isNull()) ? fieldNode.asText() : "";
    }

    // Metodo auxiliar para obtener el valor Long de un nodo, con manejo de valores nulos
    private Long getNodeLong(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null && !fieldNode.isNull()) ? fieldNode.asLong() : 0L;
    }

    // Metodo auxiliar para obtener el valor Integer de un nodo, con manejo de valores nulos
    private Integer getNodeInt(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null && !fieldNode.isNull()) ? fieldNode.asInt() : 0;
    }
}
