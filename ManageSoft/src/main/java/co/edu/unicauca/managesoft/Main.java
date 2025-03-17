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
        Parent root = loadFXML("UserLoginVista");
        Scene scene = new Scene(root); 

        stage.setScene(scene);
        stage.centerOnScreen();

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
