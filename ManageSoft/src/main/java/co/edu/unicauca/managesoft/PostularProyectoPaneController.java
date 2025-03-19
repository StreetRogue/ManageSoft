package co.edu.unicauca.managesoft;

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

public class PostularProyectoPaneController implements Initializable {

    private ProyectoServices proyectoServices;
    //private Proyecto proyectoRegistrado;

    public PostularProyectoPaneController(ProyectoServices proyectoServices) {
        this.proyectoServices = proyectoServices;
        
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

    public void setProyectoServices(ProyectoServices proyectoServices) {
        this.proyectoServices = proyectoServices;
    }

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
        Proyecto proyectoRegistrado = new Proyecto();
        // Obtener los valores de los campos
        String nombreVacante = txtNombreVacante.getText();
        String descripcionVacante = txtDescripcionVacante.getText();
        String objetivo = txtObjetivo.getText();
        String presupuesto = txtPresupuestoVacante.getText();
        String resumenVacante = txtResumenVacante.getText();
        String tiempoEstipulado = txtTiempoEstipulado.getText();

        try {
            proyectoRegistrado.setNombreProyecto(nombreVacante);
            proyectoRegistrado.setDescripcionProyecto(descripcionVacante);
            proyectoRegistrado.setObjetivoProyecto(objetivo);
            proyectoRegistrado.setPresupuestoProyecto(presupuesto);
            proyectoRegistrado.setResumenProyecto(resumenVacante);
            proyectoRegistrado.setMaximoMesesProyecto(tiempoEstipulado);
            // Obtener fecha actual del computador y formatearla
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            proyectoRegistrado.setFechaPublicacionProyecto(formato.format(fechaActual));
            proyectoRegistrado.setEstadoProyecto(new EstadoRecibido());
            proyectoRegistrado.setIdEmpresa(2);

            boolean proyectoGuardado = proyectoServices.guardarProyecto(proyectoRegistrado);

            if (proyectoGuardado) {
                // Aquí puedes procesar estos datos, por ejemplo, enviarlos a una base de datos
                System.out.println("Nombre Vacante: " + nombreVacante);
                System.out.println("Descripción Vacante: " + descripcionVacante);
                System.out.println("Objetivo: " + objetivo);
                System.out.println("Presupuesto: " + presupuesto);
                System.out.println("Resumen Vacante: " + resumenVacante);
                System.out.println("Tiempo Estipulado: " + proyectoRegistrado.getMaximoMesesProyecto());
            }

        } catch (MyException e) {

        }
    }
}
