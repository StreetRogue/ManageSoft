package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.EmpresaRepositorioMicroservicio;
import co.edu.unicauca.managesoft.access.EstudianteRepositorioMicroservicio;
import co.edu.unicauca.managesoft.access.Factory;
import co.edu.unicauca.managesoft.access.ICoordinadorRepositorio;
import co.edu.unicauca.managesoft.access.IEmpresaRepositorio;
import co.edu.unicauca.managesoft.access.IEstudianteRepositorio;
import co.edu.unicauca.managesoft.access.INotificacionRepositorio;
import co.edu.unicauca.managesoft.access.IProyectoRepositorio;
import co.edu.unicauca.managesoft.access.IUsuarioRepositorio;
import co.edu.unicauca.managesoft.access.NotificacionRepositorioMicroservicio;
import co.edu.unicauca.managesoft.access.ProyectoRepositorioMicroservicio;
import co.edu.unicauca.managesoft.access.Repositorio;
import static co.edu.unicauca.managesoft.access.Repositorio.repositorioCorreo;
import co.edu.unicauca.managesoft.access.UsuarioRepositorioMicroservicio;
import co.edu.unicauca.managesoft.services.LogInServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX Main
 */

public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        
        //IUsuarioRepositorio repositorioUsuarios = Factory.getInstancia().getRepositorioUsuario("NEONDB");
        IUsuarioRepositorio repositorioUsuarios = new UsuarioRepositorioMicroservicio();
        //IEmpresaRepositorio repositorioEmpresa = Factory.getInstancia().getRepositorioEmpresa("NEONDB");
        IEmpresaRepositorio repositorioEmpresa = new EmpresaRepositorioMicroservicio();
        ICoordinadorRepositorio repositorioCoordinador = Factory.getInstancia().getRepositorioCoordinador("NEONDB");
//        IEstudianteRepositorio repositorioEstudiante = Factory.getInstancia().getRepositorioEstudiante("NEONDB");
        IEstudianteRepositorio repositorioEstudiante = new EstudianteRepositorioMicroservicio();
        //IProyectoRepositorio repositorioProyectos = Factory.getInstancia().getRepositorioProyecto("NEONDB");
        IProyectoRepositorio repositorioProyectos = new ProyectoRepositorioMicroservicio();
        //INotificacionRepositorio repositorioCorreo = Factory.getInstancia().getNotificacionRepositorio("NEONDB");
        INotificacionRepositorio repositorioCorreo = new NotificacionRepositorioMicroservicio(); // Mientras tanto
        
        Repositorio repositorio = new Repositorio(repositorioUsuarios, repositorioEmpresa, repositorioCoordinador, repositorioEstudiante,repositorioCorreo);
        
        repositorioUsuarios.setRepositorioEmpresa(repositorioEmpresa);
        repositorioUsuarios.setRepositorioCoordinador(repositorioCoordinador);
        repositorioUsuarios.setRepositorioEstudiante(repositorioEstudiante);
        repositorioEmpresa.setRepositorioProyecto(repositorioProyectos);
        repositorioUsuarios.setRepositorioCorreo(repositorioCorreo);
        
        LogInServices loginServices = new LogInServices(repositorioUsuarios);
        
        // Crear una instancia del controlador personalizado
        UserLoginController userLoginController = new UserLoginController(repositorio, loginServices);

        // Cargar la vista con el controlador configurado
        Parent root = loadFXML("UserLoginVista", userLoginController);
        scene = new Scene(root); // Inicializar la escena

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    static void setRoot(String fxml, Object controller) throws IOException {
        Parent root = loadFXML(fxml, controller); // Llamar al método con un controlador
        scene.setRoot(root); // Establecer el nuevo root
    }

    private static Parent loadFXML(String fxml, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        if (controller != null) {
            fxmlLoader.setController(controller);
        }
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
        //DATOS DE PARA INICIAR SESION
        /*
        ESTUDIANTE:
        Usuario: estudiante1
        Contraseña: contra123
        EMPRESA:
        Usuario: empresa1
        Contraseña: contra123
        Coordinador
        Usuario: coord1
        Contraseña: contrasegura1
        */
    }
}
