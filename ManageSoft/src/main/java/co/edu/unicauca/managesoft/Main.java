package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.CoordinadorRepositorioMicroservicio;
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

        //_______________________________________________________________________________________________________________________
        //REPOSITORIOS MICROSERVICIO
      IUsuarioRepositorio repositorioUsuarios = Factory.getInstancia().getRepositorioUsuario("MICROSERVICIO");
      IEmpresaRepositorio repositorioEmpresa = Factory.getInstancia().getRepositorioEmpresa("MICROSERVICIO");
      ICoordinadorRepositorio repositorioCoordinador = Factory.getInstancia().getRepositorioCoordinador("MICROSERVICIO");
      IEstudianteRepositorio repositorioEstudiante = Factory.getInstancia().getRepositorioEstudiante("MICROSERVICIO");
      IProyectoRepositorio repositorioProyectos = Factory.getInstancia().getRepositorioProyecto("MICROSERVICIO");
      INotificacionRepositorio repositorioCorreo = Factory.getInstancia().getNotificacionRepositorio("MICROSERVICIO"); 
        //_______________________________________________________________________________________________________________________
        //REPOSITORIOS NEON
//        IUsuarioRepositorio repositorioUsuarios = Factory.getInstancia().getRepositorioUsuario("NEONDB");
//        IEmpresaRepositorio repositorioEmpresa = Factory.getInstancia().getRepositorioEmpresa("NEONDB");
//        ICoordinadorRepositorio repositorioCoordinador = Factory.getInstancia().getRepositorioCoordinador("NEONDB");
//        IEstudianteRepositorio repositorioEstudiante = Factory.getInstancia().getRepositorioEstudiante("NEONDB");
//        IProyectoRepositorio repositorioProyectos = Factory.getInstancia().getRepositorioProyecto("NEONDB");
//        INotificacionRepositorio repositorioCorreo = Factory.getInstancia().getNotificacionRepositorio("NEONDB");
        //_______________________________________________________________________________________________________________________

        Repositorio repositorio = new Repositorio(repositorioUsuarios, repositorioEmpresa, repositorioCoordinador, repositorioEstudiante, repositorioCorreo);

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
        scene.setRoot(root); // Establecer el nuevo rootoord
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
        //DATOS DE PARA INICIAR SESION- NEONDB
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

        //DATOS DE PARA INICIAR SESION- Microservicio
        /*
        ESTUDIANTE:
        Usuario: estudiante1
        Contraseña: contra123 - clave123
        EMPRESA:
        Usuario: empresa1
        Contraseña: clave123
        Coordinador
        Usuario: coord1
        Contraseña: clavesita123
         */
    }
}
