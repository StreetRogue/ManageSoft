/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Usuario;
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;
import co.edu.unicauca.managesoft.services.LogInServices;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;

import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class UserLoginController implements Initializable {
    private LogInServices loginServices;
    
    public UserLoginController(LogInServices loginServices) {
        this.loginServices = loginServices;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtUsuario; // Campo de contraseña

    @FXML
    private PasswordField txtPassword; // Campo de contraseña

    @FXML
    private ImageView eyeIcon; // Ícono del ojo

    @FXML
    private TextField txtVisiblePassword;

    @FXML
    private TextFlow lblRegistrarUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtVisiblePassword.setVisible(false);

    }
    
    public void setLoginServices(LogInServices loginServices) {
        this.loginServices = loginServices;
    }

    @FXML
    private void showPassword() {
        txtVisiblePassword.setText(txtPassword.getText()); // Copiar el texto
        txtVisiblePassword.setVisible(true);  // Mostrar el campo de texto
        txtPassword.setVisible(false);  // Ocultar el PasswordField
    }

    @FXML
    private void showRegistrarForm(MouseEvent event) throws IOException {
        UserRegisterController userRegisterController = new UserRegisterController(loginServices);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userRegisterVista.fxml"));
        loader.setController(userRegisterController);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void hidePassword() {
        txtPassword.setText(txtVisiblePassword.getText()); // Restaurar el texto en el PasswordField
        txtVisiblePassword.setVisible(false); // Ocultar el TextField
        txtPassword.setVisible(true); // Mostrar el PasswordField
    }

    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {
        String usuario = txtUsuario.getText();
        String contrasena = txtPassword.getText();

        Usuario usuarioInicio = loginServices.iniciarSesion(usuario, contrasena);

        if (usuarioInicio != null) {
            Map<enumTipoUsuario, String> paginas = new HashMap<>();
            paginas.put(enumTipoUsuario.EMPRESA, "DashboardEmpresa.fxml");
            paginas.put(enumTipoUsuario.COORDINADOR, "DashboardCoordinador.fxml");
            // paginas.put(enumTipoUsuario.ESTUDIANTE, "DashboardEmpresa.fxml");

            FXMLLoader loader = new FXMLLoader(getClass().getResource(paginas.get(usuarioInicio.getTipoUsuario())));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof DashboardEmpresaController) {
                ((DashboardEmpresaController) controller).setUsuario((Empresa) usuarioInicio);
                ((DashboardEmpresaController) controller).setLoginServices(loginServices);
                ((DashboardEmpresaController) controller).inicializarVista();
            } else if (controller instanceof DashboardCoordinadorController) {
                ((DashboardCoordinadorController) controller).setUsuario(usuarioInicio);
                ((DashboardCoordinadorController) controller).setLoginServices(loginServices);
                ((DashboardCoordinadorController) controller).inicializarVista();
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
            stage.setFullScreenExitHint(""); // Oculta el mensaje de salida (Escape)
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); // Deshabilita Escape para salir

            // Vuelve a pantalla completa si el usuario intenta salir
            stage.fullScreenProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue) {
                    stage.setFullScreen(true); // Forzar pantalla completa de nuevo
                }
            });

            stage.show();
        } else {
            System.out.println("Usuario no registrado");
        }

    }

}
