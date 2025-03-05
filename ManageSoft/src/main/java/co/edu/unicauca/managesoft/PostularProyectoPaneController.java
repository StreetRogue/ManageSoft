/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class PostularProyectoPaneController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public TextField txtObjetivo;


    private static final int MAX_CARACTERES = 50;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        limitarCaracteres(txtObjetivo, MAX_CARACTERES);
    }

    private void limitarCaracteres(TextField textField, int maxLength) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.isContentChange()) {
                int newLength = textField.getText().length() + change.getControlNewText().length() - change.getControlText().length();
                if (newLength > maxLength) {
                    return null; 
                }
            }
            return change;
        };
        textField.setTextFormatter(new TextFormatter<>(filter));
    }
}
