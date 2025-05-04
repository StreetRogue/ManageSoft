/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Empresa;
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
public class DashboardEmpresaController implements Initializable {
    private Empresa empresa;
    private LogInServices loginServices;
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane contentPane; // Este es el Pane donde se cargarán las vistas
    private Repositorio repositorio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void inicializarVista() {
        cargarDashboardPane();
    }
    
    public void setUsuario(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public void setLoginServices(LogInServices loginServices) {
        this.loginServices = loginServices;
    }  

    public void setRepositorio(Repositorio repositorio) {
        this.repositorio = repositorio;
    }
    
    /**
     * Carga la vista dashboardPane.fxml dentro del contentPane.
     */
    @FXML
    private void cargarDashboardPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardEmpresaPane.fxml"));
            Parent nuevaVista = loader.load();
            
            
            if (empresa == null) {
                System.out.println("El usuario no ha sido inicializado.");
            } else {
                System.out.println(empresa.getNitEmpresa());
            }

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
        PostularProyectoPaneController proyectoControlador = new PostularProyectoPaneController(empresa);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("postularProyectoPane.fxml")); 
            loader.setController(proyectoControlador);
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
        ListaProyectoPaneController listaProyectosControlador = new ListaProyectoPaneController(empresa);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listaProyectoEmpresaPane.fxml"));
            loader.setController(listaProyectosControlador);
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
