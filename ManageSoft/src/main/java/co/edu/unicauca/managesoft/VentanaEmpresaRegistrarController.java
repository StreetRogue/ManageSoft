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
public class VentanaEmpresaRegistrarController implements Initializable {
    private UserRegisterController userRegisterController;
    private LogInServices loginServices;

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtNitEmpresa;
    @FXML
    private TextField txtNombreEmpresa;
    @FXML
    private TextField txtEmailEmpresa;
    @FXML
    private TextField txtSectorEmpresa;
    @FXML
    private TextField txtTelRepresentante;
    @FXML
    private TextField txtCargoRepresentante;
    @FXML
    private TextField txtNombreRepresentante;
    @FXML
    private TextField txtApellidoRepresentante;
    
    // Constructor
    public VentanaEmpresaRegistrarController(LogInServices loginServices) {
        this.loginServices = loginServices;
    }

    public void setUserRegisterController(UserRegisterController controller) {
        this.userRegisterController = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void capturarDatos() {
        String nit = txtNitEmpresa.getText().trim();
        String nombre = txtNombreEmpresa.getText().trim();
        String email = txtEmailEmpresa.getText().trim();
        String sector = txtSectorEmpresa.getText().trim();
        String telefono = txtTelRepresentante.getText().trim();
        String cargo = txtCargoRepresentante.getText().trim();
        String nombreRep = txtNombreRepresentante.getText().trim();
        String apellidoRep = txtApellidoRepresentante.getText().trim();

        // Enviar datos al UserRegisterController si está disponible
        if (userRegisterController != null) {
            userRegisterController.recibirDatosEmpresa(nit, nombre, email, sector, telefono, cargo, nombreRep, apellidoRep);

        } else {
            System.out.println("Error: userRegisterController es null.");
        }

        // Cerrar la ventana después de capturar los datos
        Stage stage = (Stage) txtNitEmpresa.getScene().getWindow();
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
