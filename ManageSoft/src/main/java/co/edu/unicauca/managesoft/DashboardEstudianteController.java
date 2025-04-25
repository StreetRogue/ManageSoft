package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Usuario;
import co.edu.unicauca.managesoft.services.LogInServices;
import co.edu.unicauca.managesoft.services.ProyectoServices;
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
public class DashboardEstudianteController implements Initializable {

    private Usuario usuario;
    private LogInServices loginServices;
    private Estudiante estudiante;
    private Repositorio repositorio;

    @FXML
    private AnchorPane contentPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializarVista() {
        cargarDashboardEstudiante();
    }

    public void setUsuario(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void setLoginServices(LogInServices loginServices) {
        this.loginServices = loginServices;
    }

    public void setRepositorio(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

//    public void setEstudiante(Estudiante estudiante) {
//        this.estudiante = estudiante;
//    }
//    
    @FXML
    private void cargarDashboardEstudiante() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardEstudiantePane.fxml"));
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
    private void cargarProyectosEstudiante() {
        System.out.println("Método cargarProyectosEstudiante() llamado correctamente");
        
        ListaProyectoEstudiantePaneController listaProyectosEstudianteControlador = new ListaProyectoEstudiantePaneController(repositorio, repositorio.getRepositorioEmpresa().listarEmpresas(), estudiante);
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("listaProyectoEstudiantePane.fxml"));
            loader.setController(listaProyectosEstudianteControlador);
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
