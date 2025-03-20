package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.INotificacionRepositorio;
import co.edu.unicauca.managesoft.access.IProyectoRepositorio;
import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.infra.IObserver;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class ListaProyectoEstudiantePaneController implements IObserver {

    private IProyectoRepositorio repositorioProyecto;
    private Repositorio repositorio;
    private Estudiante estudiante;

    public ListaProyectoEstudiantePaneController(IProyectoRepositorio repositorioProyecto, Estudiante estudiante) {
        this.repositorioProyecto = repositorioProyecto;
        this.estudiante = estudiante;
    }

    public void setRepositorio(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Proyecto> tableViewProyectos;
    @FXML
    private TableColumn<Proyecto, Integer> colId;
    @FXML
    private TableColumn<Proyecto, String> colNombre;
    @FXML
    private TableColumn<Proyecto, String> colEstado;
    @FXML
    private TableColumn<Proyecto, String> colEmpresa;
    @FXML
    private TableColumn<Proyecto, String> colResumen;
    @FXML
    private TableColumn<Proyecto, Double> colPresupuesto;
    @FXML
    private TableColumn<Proyecto, String> colTiempoEstipulado;
    @FXML
    private TableColumn<Proyecto, Void> colEnviarCorreo;

    private ObservableList<Proyecto> proyectosEmpresaList;

    @FXML
    public void initialize() {
        proyectosEmpresaList = FXCollections.observableArrayList();
        cargarProyectos();
        tableViewProyectos.setItems(proyectosEmpresaList);
        colId.setCellValueFactory(new PropertyValueFactory<>("idProyecto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProyecto"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoProyecto"));
        colEmpresa.setCellValueFactory(new PropertyValueFactory<>("nombreEmpresa"));
        colResumen.setCellValueFactory(new PropertyValueFactory<>("resumenProyecto"));
        colPresupuesto.setCellValueFactory(new PropertyValueFactory<>("presupuestoProyecto"));
        colTiempoEstipulado.setCellValueFactory(new PropertyValueFactory<>("maximoMesesProyecto"));

        configurarColumnaCorreo();
    }

    public void cargarProyectos() {
        List<Proyecto> proyectos = obtenerProyectosDesdeBaseDeDatos();
        proyectosEmpresaList.clear();
        proyectosEmpresaList.addAll(proyectos);

        for (Proyecto proyecto : proyectos) {
            proyecto.agregarObservador(this);
        }
    }

    @Override
    public void actualizar() {
        tableViewProyectos.refresh();
    }

    private List<Proyecto> obtenerProyectosDesdeBaseDeDatos() {
        return repositorioProyecto.listarProyectosGeneral();
    }

    private void configurarColumnaCorreo() {
        colEnviarCorreo.setCellFactory(tc -> new TableCell<Proyecto, Void>() {
            private final ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(
                    "/co/edu/unicauca/managesoft/resources/emailVector.png"
            )));

            private final Button btn = new Button();

            {
                // Configurar la imagen como ícono del botón
                Image image = new Image(getClass().getResourceAsStream("/co/edu/unicauca/managesoft/resources/emailVector.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                btn.setGraphic(imageView);

                // Hacer que el botón sea transparente
                btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                // Agregar evento de clic
                btn.setOnAction(event -> {
                    Proyecto proyecto = getTableView().getItems().get(getIndex());
                    System.out.println("VAGINAAAAA:  "+ estudiante.getCodigoSimcaEstudiante());
                    abrirVistaContactarCoordinador(proyecto, estudiante);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                    setAlignment(Pos.CENTER); // Centrar el botón en la celda
                }
            }
        });
    }

    private void abrirVistaContactarCoordinador(Proyecto proyecto, Estudiante estudiante) {
        try {
            ContactarCoordinadorPaneController controller = new ContactarCoordinadorPaneController(repositorio.getRepositorioCorreo(), estudiante, proyecto);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("contactarCoordinadorPane.fxml"));
            loader.setController(controller);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initOwner(tableViewProyectos.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);  // Bloquea la interacción con la ventana principal
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
