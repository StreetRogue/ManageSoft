package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.IUsuarioRepositorio;
import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.access.UsuarioRepositorioMicroservicio;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Usuario;
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;
import co.edu.unicauca.managesoft.infra.MyException;
import co.edu.unicauca.managesoft.services.LogInServices;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserRegisterController implements Initializable {

    private LogInServices loginServices;
    private Usuario usuarioRegistrado;
    private boolean datosCargados = false;

    // Campos FXML
    @FXML
    private TextField txtRegUsuario; // Campo de usuario
    @FXML
    private PasswordField txtRegPassword; // Campo de contraseña
    @FXML
    private ImageView eyeIcon; // Ícono del ojo
    @FXML
    private TextField txtVisiblePassword;
    @FXML
    private ComboBox<enumTipoUsuario> cboRolUser;
    @FXML
    private TextFlow lblLoginUser;

    // Variables para almacenar los datos del estudiante o empresa
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String codigoSIMCA;
    private String emailEstudiante;
    private Repositorio repositorio;

    // Constructor
    public UserRegisterController(Repositorio repositorio, LogInServices loginServices) {
        this.repositorio = repositorio;
        this.loginServices = loginServices;
        this.usuarioRegistrado = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cboRolUser.getItems().setAll(enumTipoUsuario.values());
    }

    @FXML
    private void showLoginForm() {
        try {
            UserLoginController userLoginController = new UserLoginController(repositorio, loginServices);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLoginVista.fxml"));
            loader.setController(userLoginController);
            Parent root = loader.load();

            Stage stage = (Stage) txtRegUsuario.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la página de inicio de sesión.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void guardarEstudiante(Estudiante estudiante) {
        usuarioRegistrado = estudiante;

        System.out.println("Datos del estudiante recibidos:");
        System.out.println("Nombre: " + ((Estudiante) usuarioRegistrado).getNombreEstudiante());
        System.out.println("Apellido: " + ((Estudiante) usuarioRegistrado).getApellidoEstudiante());
        System.out.println("Codigo de Simca: " + ((Estudiante) usuarioRegistrado).getCodigoSimcaEstudiante());
        System.out.println("email: " + ((Estudiante) usuarioRegistrado).getEmailEstudiante());
    }

    public void guardarEmpresa(Empresa empresa) {
        usuarioRegistrado = empresa;

        System.out.println("Datos de la empresa recibidos:");
        System.out.println("NIT: " + ((Empresa) usuarioRegistrado).getNitEmpresa());
        System.out.println("Nombre: " + ((Empresa) usuarioRegistrado).getNombreEmpresa());
        System.out.println("Email: " + ((Empresa) usuarioRegistrado).getEmailEmpresa());
        System.out.println("Sector: " + ((Empresa) usuarioRegistrado).getSectorEmpresa());
        System.out.println("Teléfono Representante: " + ((Empresa) usuarioRegistrado).getContactoEmpresa());
        System.out.println("Nombre Representante: " + ((Empresa) usuarioRegistrado).getNombreContactoEmpresa());
        System.out.println("Apellido Representante: " + ((Empresa) usuarioRegistrado).getApellidoContactoEmpresa());
        System.out.println("Cargo Representante: " + ((Empresa) usuarioRegistrado).getCargoContactoEmpresa());
    }

    @FXML
    private void handleRolSelection() throws IOException {
        enumTipoUsuario selectedRol = cboRolUser.getSelectionModel().getSelectedItem();
        if (selectedRol == null) {
            return; // No hacer nada si no hay selección
        }

        // Mapeo de roles a sus vistas correspondientes
        Map<enumTipoUsuario, String> paginas = new HashMap<>();
        paginas.put(enumTipoUsuario.EMPRESA, "ventanaEmpresaRegistrar.fxml");
        paginas.put(enumTipoUsuario.ESTUDIANTE, "ventanaEstudianteRegistrar.fxml");
        paginas.put(enumTipoUsuario.COORDINADOR, "ventanaCoordinadorRegistrar.fxml");

        // Obtener la vista según el rol seleccionado
        String vista = paginas.get(selectedRol);

        if (vista != null) {
            Object controller;

            if (selectedRol.equals(enumTipoUsuario.EMPRESA)) {
                controller = new VentanaEmpresaRegistrarController(loginServices);
                ((VentanaEmpresaRegistrarController) controller).setUserRegisterController(this);

            } else if (selectedRol.equals(enumTipoUsuario.ESTUDIANTE)) {
                controller = new VentanaEstudianteRegistrarController(loginServices);
                ((VentanaEstudianteRegistrarController) controller).setUserRegisterController(this);
            } else if (selectedRol.equals(enumTipoUsuario.COORDINADOR)) {
                controller = new VentanaCoordinadorRegistrarController(loginServices);
                ((VentanaCoordinadorRegistrarController) controller).setUserRegisterController(this);
            } else {
                throw new IllegalArgumentException("Rol no soportado: " + selectedRol);
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
                loader.setController(controller);
                Parent root = loader.load();

                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.setTitle("Registro de " + selectedRol);
                newStage.setResizable(false);
                newStage.centerOnScreen();
                newStage.initModality(Modality.APPLICATION_MODAL); // Evita que la ventana principal se bloquee
                newStage.show();

                System.out.println("Ventana de " + selectedRol + " mostrada correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void showPassword() {
        txtVisiblePassword.setText(txtRegPassword.getText()); // Copiar el texto
        txtVisiblePassword.setVisible(true);  // Mostrar el campo de texto
        txtRegPassword.setVisible(false);  // Ocultar el PasswordField
    }

    @FXML
    private void hidePassword() {
        txtRegPassword.setText(txtVisiblePassword.getText()); // Restaurar el texto en el PasswordField
        txtVisiblePassword.setVisible(false); // Ocultar el TextField
        txtRegPassword.setVisible(true); // Mostrar el PasswordField
    }

    @FXML
    private void registrarUsuario(ActionEvent event) throws IOException {
        String nombreUsuario = txtRegUsuario.getText();
        String contrasenaUsuario = txtRegPassword.getText();
        enumTipoUsuario tipoUsuario = cboRolUser.getSelectionModel().getSelectedItem();

        if (usuarioRegistrado == null) {
            mostrarAlerta("Atención", "Debe rellenar todos los campos", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Asignar el nombre de usuario, contraseña y tipo de usuario
            usuarioRegistrado.setNombreUsuario(nombreUsuario);
            usuarioRegistrado.setContrasenaUsuario(contrasenaUsuario);
            usuarioRegistrado.setTipoUsuario(tipoUsuario);

            // Llamar al repositorio para registrar el usuario (microservicio)
            IUsuarioRepositorio usuarioRepositorio = new UsuarioRepositorioMicroservicio();
            boolean usuarioGuardado = usuarioRepositorio.registrarUsuario(usuarioRegistrado);

            if (usuarioGuardado) {
                System.out.println("Usuario registrado con éxito:");
                System.out.println("Nombre de usuario: " + nombreUsuario);
                System.out.println("Contraseña: " + contrasenaUsuario);
                System.out.println("Tipo de usuario: " + tipoUsuario);
                mostrarAlerta("Éxito", "Usuario registrado correctamente", Alert.AlertType.CONFIRMATION);
                showLoginForm();
            } else {
                mostrarAlerta("Error", "Error en el registro del usuario.", Alert.AlertType.ERROR);
            }

        } catch (MyException e) {
            mostrarAlerta("Atención", e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initModality(Modality.NONE);
        alert.show();
    }
}
