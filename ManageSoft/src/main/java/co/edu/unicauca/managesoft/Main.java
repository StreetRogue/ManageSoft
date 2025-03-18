package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.Factory;
import co.edu.unicauca.managesoft.access.IEmpresaRepositorio;
import co.edu.unicauca.managesoft.access.IUsuarioRepositorio;
import co.edu.unicauca.managesoft.services.LoginServices;
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
        IUsuarioRepositorio repositorioUsuarios = Factory.getInstancia().getRepositorioUsuario("POSTGRES");
        LoginServices loginServices = new LoginServices(repositorioUsuarios);
        
        // Crear una instancia del controlador personalizado
        UserLoginController userLoginController = new UserLoginController(loginServices);

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
    }
}
