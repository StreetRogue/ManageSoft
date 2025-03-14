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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class DashboardEmpresaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane contentPane; // Este es el Pane donde se cargarán las vistas

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cargarDashboardPane();
    }

    /**
     * Carga la vista dashboardPane.fxml dentro del contentPane.
     */
    @FXML
    private void cargarDashboardPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardEmpresaPane.fxml"));
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
    private void cargarPostularProyecto() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("postularProyectoPane.fxml"));
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
    private void cargarProyectosEmpresa() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listaProyectoEmpresaPane.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLoginVista.fxml"));
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
