/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Coordinador;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Usuario;
import co.edu.unicauca.managesoft.services.LogInServices;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class DashboardCoordinadorController implements Initializable {

//    private Usuario usuario;
    private LogInServices loginServices;
    private Coordinador coordinador;

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane contentPane;
    private Repositorio repositorio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void inicializarVista() {
        cargarDashboardPane();
    }

    public void setUsuario(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    public void setLoginServices(LogInServices loginServices) {
        this.loginServices = loginServices;
    }

    public void setRepositorio(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @FXML
    private void cargarDashboardPane() {
        try {
            
            System.out.println("Nombre: "+coordinador.getNombreUsuario());
            System.out.println("Email: "+coordinador.getEmail());
            System.out.println("Telefono: "+coordinador.getTelefono());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardCoordinadorPane.fxml"));
            Parent nuevaVista = loader.load();

            // Ajustar la vista al tamaño del contentPane
            contentPane.getChildren().setAll(nuevaVista);
            AnchorPane.setTopAnchor(nuevaVista, 0.0);
            AnchorPane.setBottomAnchor(nuevaVista, 0.0);
            AnchorPane.setLeftAnchor(nuevaVista, 0.0);
            AnchorPane.setRightAnchor(nuevaVista, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cargarProyectosCoordinador() {
        ListaProyectoCoordinadorPaneController listaProyectoCoordinador = new ListaProyectoCoordinadorPaneController(coordinador);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaProyectoCoordinadorPane.fxml"));
            loader.setController(listaProyectoCoordinador);
            Parent nuevaVista = loader.load();

            // Ajustar la vista al tamaño del contentPane
            contentPane.getChildren().setAll(nuevaVista);
            AnchorPane.setTopAnchor(nuevaVista, 0.0);
            AnchorPane.setBottomAnchor(nuevaVista, 0.0);
            AnchorPane.setLeftAnchor(nuevaVista, 0.0);
            AnchorPane.setRightAnchor(nuevaVista, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            UserLoginController userLoginController = new UserLoginController(repositorio, loginServices);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLoginVista.fxml"));
            loader.setController(userLoginController);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
