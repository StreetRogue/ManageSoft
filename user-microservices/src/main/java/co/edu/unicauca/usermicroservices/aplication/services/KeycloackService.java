package co.edu.unicauca.usermicroservices.aplication.services;

import co.edu.unicauca.usermicroservices.domain.model.Coordinador;
import co.edu.unicauca.usermicroservices.domain.model.Empresa;
import co.edu.unicauca.usermicroservices.domain.model.Estudiante;
import co.edu.unicauca.usermicroservices.domain.model.Usuario;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.springframework.stereotype.Service;

import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
public class KeycloackService {
    private final String serverUrl = "http://localhost:8080";
    private final String realm = "ManageSoft";
    private final String clientId = "ManageSoft-Desktop";
    private final String clientSecret = "ZCwtERrCat7s126xLYsnZNjzDg9W8zXj";
    private final String username = "managesoft";
    private final String password = "oracle";

    private Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }

    public void createUserInKeycloak(Usuario usuario) {
        try {
            Keycloak keycloak = getInstance();
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            // Crear la contraseña
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setTemporary(false);
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(usuario.getContrasenaUsuario());

            // Obtener email según tipo usuario
            String email = switch (usuario.getTipoUsuario()) {
                case ESTUDIANTE -> ((Estudiante) usuario).getEmailEstudiante();
                case EMPRESA -> ((Empresa) usuario).getEmailEmpresa();
                case COORDINADOR -> ((Coordinador) usuario).getEmailCoordinador();
            };



            // Crear usuario
            UserRepresentation user = new UserRepresentation();
            user.setUsername(usuario.getNombreUsuario());
            user.setEnabled(true);
            user.setEmail(email);
            user.setEmailVerified(true);
            user.setCredentials(Collections.singletonList(credential));
            switch (usuario.getTipoUsuario()) {
                case ESTUDIANTE -> {
                    Estudiante est = (Estudiante) usuario;
                    user.setFirstName(est.getNombreEstudiante());
                    user.setLastName(est.getApellidoEstudiante());
                }
                case EMPRESA -> {
                    Empresa emp = (Empresa) usuario;
                    user.setFirstName(emp.getNombreContactoEmpresa());
                    user.setLastName(emp.getApellidoContactoEmpresa()); // Si no tienes apellido, puedes dejar vacío o algún valor
                }
                case COORDINADOR -> {
                    Coordinador coor = (Coordinador) usuario;
                    user.setFirstName(coor.getNombreCoordinador());
                    user.setLastName("Perez"); // Igual que empresa, ajusta si tienes apellido
                }
            }




            // Crear usuario en Keycloak
            Response response = usersResource.create(user);
            String responseBody = response.readEntity(String.class);
            System.out.println("Respuesta creación usuario: " + response.getStatus());
            System.out.println("Mensaje: " + responseBody);

            if (response.getStatus() != 201) {
                throw new RuntimeException("Error al crear usuario en Keycloak: " + responseBody);
            }

            // Obtener ID del usuario recién creado
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

            // Listar roles disponibles y mostrar en consola
            List<RoleRepresentation> roles = realmResource.roles().list();
            System.out.println("Roles disponibles en el realm:");
            roles.forEach(r -> System.out.println("- " + r.getName()));

            // Buscar el rol que corresponde al tipo de usuario (minusculas)
            String rolName = usuario.getTipoUsuario().toString().toUpperCase();


            boolean rolExiste = roles.stream().anyMatch(r -> r.getName().equals(rolName));
            if (!rolExiste) {
                throw new RuntimeException("Rol no encontrado en Keycloak: " + rolName);
            }

            RoleRepresentation role = realmResource.roles().get(rolName).toRepresentation();

            // Asignar rol al usuario
            realmResource.users().get(userId).roles().realmLevel().add(List.of(role));

            System.out.println("Rol asignado correctamente: " + rolName);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creando usuario en Keycloak: " + e.getMessage());
        }
    }

}
