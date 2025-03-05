/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class UserLoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private PasswordField txtPassword; // Campo de contraseña

    @FXML
    private ImageView eyeIcon; // Ícono del ojo

    @FXML
    private TextField txtVisiblePassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtVisiblePassword.setVisible(false);
    }

    @FXML
    private void showPassword() {
        txtVisiblePassword.setText(txtPassword.getText()); // Copiar el texto
        txtVisiblePassword.setVisible(true);  // Mostrar el campo de texto
        txtPassword.setVisible(false);  // Ocultar el PasswordField
    }

    @FXML
    private void hidePassword() {
        txtPassword.setText(txtVisiblePassword.getText()); // Restaurar el texto en el PasswordField
        txtVisiblePassword.setVisible(false); // Ocultar el TextField
        txtPassword.setVisible(true); // Mostrar el PasswordField
    }

    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardEmpresa.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint(""); // Oculta el mensaje de salida (Escape)
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); // Deshabilita Escape para salir

        // 🔹 Vuelve a pantalla completa si el usuario intenta salir
        stage.fullScreenProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue) {
                stage.setFullScreen(true); // Forzar pantalla completa de nuevo
            }
        });

        stage.show();
    }

}
