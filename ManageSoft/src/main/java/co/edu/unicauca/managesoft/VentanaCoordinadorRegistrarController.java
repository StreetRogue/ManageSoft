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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class VentanaCoordinadorRegistrarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private UserRegisterController userRegisterController;
    private LogInServices loginServices;

    public VentanaCoordinadorRegistrarController(LogInServices loginServices) {
        this.loginServices = loginServices;
    }

    public void setUserRegisterController(UserRegisterController userRegisterController) {
        this.userRegisterController = userRegisterController;
    }

    @FXML
    private TextField txtNombreCoordinador;

    @FXML
    private TextField txtApellidoCoordinador;

    @FXML
    private TextField txtTelefonoCoordinador;

    @FXML
    private TextField txtEmailCoordinador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void capturarDatos() {
        // Obtener valores de los TextFields
        String nombre = txtNombreCoordinador.getText().trim();
        String apellido = txtApellidoCoordinador.getText().trim();
        String telefono = txtTelefonoCoordinador.getText().trim();
        String email = txtEmailCoordinador.getText().trim();

        

        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Correo Electrónico: " + email);

    }
}
