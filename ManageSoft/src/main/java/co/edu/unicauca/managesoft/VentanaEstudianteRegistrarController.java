/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.services.LogInServices;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class VentanaEstudianteRegistrarController implements Initializable {
    private UserRegisterController userRegisterController;
    private LogInServices loginServices;
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtNombreEstudiante;
    @FXML
    private TextField txtApellidoEstudiante;
    @FXML
    private TextField txtCodigoSIMCA;
    @FXML
    private TextField txtEmailEstudiante;
    
    
    
    // Constructor
    public VentanaEstudianteRegistrarController(LogInServices loginServices) {
        this.loginServices = loginServices;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setUserRegisterController(UserRegisterController controller) {
        this.userRegisterController = controller;
    }
    
     @FXML
    private void capturarDatos() {
        String nombre = txtNombreEstudiante.getText().trim();
        String apellido = txtApellidoEstudiante.getText().trim();
        String codigoSIMCA = txtCodigoSIMCA.getText().trim();
        String email = txtEmailEstudiante.getText().trim();

        // Enviar datos al UserRegisterController si está disponible
        if (userRegisterController != null) {
            userRegisterController.recibirDatosEstudiante(nombre, apellido, codigoSIMCA, email);
        }

        // Cerrar la ventana
        Stage stage = (Stage) txtNombreEstudiante.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
