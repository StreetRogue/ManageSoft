/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.infra.MyException;
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
        String nombreRep = txtNombreRepresentante.getText().trim();
        String apellidoRep = txtApellidoRepresentante.getText().trim();
        String cargo = txtCargoRepresentante.getText().trim();

        // Enviar datos al UserRegisterController si está disponible
        if (userRegisterController != null) {
            try {
                Empresa empresaRegistrada = new Empresa(nit, nombre, email, sector, telefono, nombreRep, apellidoRep, cargo);
                boolean empresaGuardada = loginServices.guardarEmpresa(empresaRegistrada);
                if (empresaGuardada) {
                    userRegisterController.guardarEmpresa(empresaRegistrada);
                    // Cerrar la ventana después de capturar los datos
                    Stage stage = (Stage) txtNitEmpresa.getScene().getWindow();
                    stage.close();
                }
            } catch (MyException e) {
                mostrarAlerta("Atencion", e.getMessage(), Alert.AlertType.WARNING);
            }
        } else {
            System.out.println("Error: userRegisterController es null.");
        }

    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
