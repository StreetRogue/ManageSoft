/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.entities.enumTipoUsuario;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class UserRegisterController implements Initializable {

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
    private boolean datosCargados = false;
    // Variables para almacenar los datos del estudiante
    private String nitEmpresa;
    private String nombreEmpresa;
    private String emailEmpresa;
    private String sectorEmpresa;
    private String telRepresentante;
    private String cargoRepresentante;
    private String nombreRepresentante;
    private String apellidoRepresentante;
    private boolean datosEmpresaCargados = false;

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
    private void showLoginForm(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLoginVista.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public void recibirDatosEstudiante(String nombre, String apellido, String codigo, String email) {
        this.nombreEstudiante = nombre;
        this.apellidoEstudiante = apellido;
        this.codigoSIMCA = codigo;
        this.emailEstudiante = email;
        this.datosCargados = true;

    }

    public void recibirDatosEmpresa(String nit, String nombre, String email, String sector,
            String telefono, String cargo, String nombreRep, String apellidoRep) {
        this.nitEmpresa = nit;
        this.nombreEmpresa = nombre;
        this.emailEmpresa = email;
        this.sectorEmpresa = sector;
        this.telRepresentante = telefono;
        this.cargoRepresentante = cargo;
        this.nombreRepresentante = nombreRep;
        this.apellidoRepresentante = apellidoRep;
        this.datosEmpresaCargados = true;

        System.out.println("Datos de la empresa recibidos:");
        System.out.println("NIT: " + nit);
        System.out.println("Nombre: " + nombre);
        System.out.println("Email: " + email);
        System.out.println("Sector: " + sector);
        System.out.println("Teléfono Representante: " + telefono);
        System.out.println("Cargo Representante: " + cargo);
        System.out.println("Nombre Representante: " + nombreRep);
        System.out.println("Apellido Representante: " + apellidoRep);
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
                controller = new VentanaEmpresaRegistrarController();
                ((VentanaEmpresaRegistrarController) controller).setUserRegisterController(this);

            } else if (selectedRol.equals(enumTipoUsuario.ESTUDIANTE)) {
                controller = new VentanaEstudianteRegistrarController();
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
        if (!datosCargados) {
            mostrarAlerta("Error", "Debe cargar los datos del estudiante antes de registrarse.");
            return;
        }

        String nombreUsuario = txtRegUsuario.getText();
        String contrasenaUsuario = txtRegPassword.getText();
        

        System.out.println("Usuario registrado con éxito:");
        System.out.println("Nombre de usuario: " + nombreUsuario);
        System.out.println("Contraseña: " + contrasenaUsuario);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
