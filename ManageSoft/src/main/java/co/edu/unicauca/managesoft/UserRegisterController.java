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
            } else if (selectedRol.equals(enumTipoUsuario.ESTUDIANTE)) {
                controller = new VentanaEstudianteRegistrarController();
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

    }

}
