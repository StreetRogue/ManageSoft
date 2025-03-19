package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.IProyectoRepositorio;
import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.EstadoRecibido;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.infra.MyException;
import co.edu.unicauca.managesoft.services.ProyectoServices;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class PostularProyectoPaneController implements Initializable {
    private IProyectoRepositorio repositorio;
    private Proyecto proyectoRegistrado;
    private Empresa empresa;
    

    public PostularProyectoPaneController(IProyectoRepositorio repositorio, Empresa empresa) {
        this.repositorio = repositorio;
        this.empresa = empresa;
    }

    @FXML
    private TextField txtNombreVacante;
    @FXML
    private TextArea txtDescripcionVacante;
    @FXML
    private TextField txtObjetivo;
    @FXML
    private TextField txtPresupuestoVacante;
    @FXML
    private TextField txtTiempoEstipulado;
    @FXML
    private TextArea txtResumenVacante;
    @FXML
    private Button btnPostularProyecto;

    private static final int MAX_CARACTERES = 50;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Limitar caracteres en el campo de Objetivo
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

    // Método para capturar los datos del formulario
    @FXML
    private void postularProyecto(ActionEvent event) throws IOException {
        
        // Obtener los valores de los campos
        String nombreVacante = txtNombreVacante.getText();
        String descripcionVacante = txtDescripcionVacante.getText();
        String objetivo = txtObjetivo.getText();
        String presupuesto = txtPresupuestoVacante.getText();
        String resumenVacante = txtResumenVacante.getText();
        String tiempoEstipulado = txtTiempoEstipulado.getText();

        try {
            empresa.setIdUsuario(1); // Debe borrarse
            boolean guardar = empresa.agregarProyecto(repositorio, nombreVacante, resumenVacante, objetivo, descripcionVacante, presupuesto, presupuesto);

            if (guardar) {
                mostrarAlerta("Exito", "El proyecto se postulo correctamente", Alert.AlertType.CONFIRMATION);
            }

        } catch (MyException e) {
            mostrarAlerta("Atencion", e.getMessage(), Alert.AlertType.WARNING);
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
