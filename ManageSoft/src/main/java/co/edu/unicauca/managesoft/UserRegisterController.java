/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Usuario;
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;
import co.edu.unicauca.managesoft.infra.MyException;
import co.edu.unicauca.managesoft.services.LogInServices;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class UserRegisterController implements Initializable {

    private LogInServices loginServices;
    private Usuario usuarioRegistrado;
    private boolean datosCargados = false;

    /**
     * Initializes the controller class.
     */
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

    // Variables para almacenar los datos del estudiante
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String codigoSIMCA;
    private String emailEstudiante;
    private Repositorio repositorio;

    // Variables para almacenar los datos del estudiante
    // Constructor
    public UserRegisterController(Repositorio repositorio, LogInServices loginServices) {
        this.repositorio = repositorio;
        this.loginServices = loginServices;
        this.usuarioRegistrado = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Llenar el ComboBox con los valores del enum
        //cboRolUser.getItems().setAll(enumTipoUsuario.values());
        cboRolUser.getItems().setAll(
                Arrays.stream(enumTipoUsuario.values())
                        .filter(tipo -> tipo != enumTipoUsuario.COORDINADOR)
                        .collect(Collectors.toList())
        );
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

    @FXML
    private void showLoginForm(MouseEvent event) throws IOException {
        UserLoginController userLoginController = new UserLoginController(repositorio, loginServices);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLoginVista.fxml"));
        loader.setController(userLoginController);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
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
            } else {
                throw new IllegalArgumentException("Rol no soportado: " + selectedRol);
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
                loader.setController(controller);
                Parent root = loader.load();

                // Crear una nueva ventana sin cerrar la actual
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.setTitle("Registro de " + selectedRol);
                newStage.setResizable(false);
                newStage.centerOnScreen();
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
            usuarioRegistrado.setNombreUsuario(nombreUsuario);
            usuarioRegistrado.setContrasenaUsuario(contrasenaUsuario);
            usuarioRegistrado.setTipoUsuario(tipoUsuario);
            boolean usuarioGuardado = loginServices.registrarUsuario(usuarioRegistrado);
            //ProgressBar.setVisible(true);
            if (usuarioGuardado) {
                System.out.println("Usuario registrado con éxito:");
                System.out.println("Nombre de usuario: " + nombreUsuario);
                System.out.println("Contraseña: " + contrasenaUsuario);
                System.out.println("tipo: " + tipoUsuario);
                mostrarAlerta("Exito", "Usuario registrado correctamente", Alert.AlertType.CONFIRMATION);
                showLoginForm();
            }

        } catch (MyException e) {
            mostrarAlerta("Atencion", e.getMessage(), Alert.AlertType.WARNING);
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
