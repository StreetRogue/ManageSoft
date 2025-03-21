package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.INotificacionRepositorio;
import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Correo;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.infra.MyException;
import co.edu.unicauca.managesoft.services.NotificacionServices;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ContactarCoordinadorPaneController {

    /**
     * Initializes the controller class.
     */
    private NotificacionServices notificacionServicio;
    private Estudiante estudiante;
    private Proyecto proyecto;
    private INotificacionRepositorio repositorioNotificacion;
    private boolean correoEnviado = false;

    public ContactarCoordinadorPaneController(INotificacionRepositorio repositorioNotificacion, Estudiante estudiante, Proyecto proyecto) {
        this.repositorioNotificacion = repositorioNotificacion;
        this.notificacionServicio = new NotificacionServices(this.repositorioNotificacion);
        this.estudiante = estudiante;
        this.proyecto = proyecto;
    }

    public void setNotificacionServicio(NotificacionServices notificacionServicio) {
        this.notificacionServicio = notificacionServicio;
    }

    @FXML
    private TextField txtEmailContacto;

    @FXML
    private TextField txtAsuntoContacto;

    @FXML
    private TextArea txtMotivoContacto; // Ahora es un TextArea

    @FXML
    private Button btnCargarDatos;

    @FXML
    public void initialize() {
        // TODO

    }

    @FXML
    private void capturarDatos(ActionEvent event) {
        String emailDestinatario = txtEmailContacto.getText();
        String asunto = txtAsuntoContacto.getText();
        String motivo = txtMotivoContacto.getText();

        System.out.println("Email: " + emailDestinatario);
        System.out.println("Asunto: " + asunto);
        System.out.println("Motivo: " + motivo);

        Correo correo = new Correo(emailDestinatario, asunto, motivo);

        try {

            boolean enviarCorreo = notificacionServicio.guardarCorreo(correo, estudiante, proyecto);
            if (enviarCorreo) {
                proyecto.setCorreoEnviado(true);
                mostrarAlerta("Exito", "Correo enviado exitosamente", Alert.AlertType.CONFIRMATION, event);
                cerrarVentana(event);
            }
        } catch (MyException e) {
            mostrarAlerta("Error", "No se pudo enviar el correo", Alert.AlertType.CONFIRMATION, event);
        }

    }

        private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipoAlerta, ActionEvent event) {
            // Obtener la ventana actual (Contactar Coordinador)

            Alert alert = new Alert(tipoAlerta);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensaje);

            // Obtener la ventana actual (Contactar Coordinador)
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Asegurar que la alerta se muestre sobre esta ventana
            alert.initOwner(stage);
            alert.showAndWait().ifPresent(response -> {
                if (tipoAlerta == Alert.AlertType.CONFIRMATION) {
                    cerrarVentana(event);
                }
            });
        }

        private void cerrarVentana(ActionEvent event) {
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        }

}
