package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.IProyectoRepositorio;
import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.infra.IObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
    private Repositorio repositorio;
    private List<Empresa> empresas;
    private Estudiante estudiante;

    public ListaProyectoEstudiantePaneController(Repositorio repositorio, List<Empresa> empresas, Estudiante estudiante) {
        this.repositorio = repositorio;
        this.empresas = empresas;
        this.estudiante = estudiante;
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
        List<Proyecto> proyectos = new ArrayList<>();
        for (Empresa empresa : empresas) {
            proyectos.addAll(empresa.listarProyectos());
        }
        return proyectos;
    }

    private void configurarColumnaCorreo() {
        colEnviarCorreo.setCellFactory(tc -> new TableCell<Proyecto, Void>() {
            private final Button btn = new Button();

            {
                Image image = new Image(getClass().getResourceAsStream("/co/edu/unicauca/managesoft/resources/emailVector.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                btn.setGraphic(imageView);
                btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                btn.setOnAction(event -> {
                    Proyecto proyecto = getTableView().getItems().get(getIndex());
                    abrirVistaContactarCoordinador(proyecto, estudiante);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().get(getIndex()).isCorreoEnviado()) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                    setAlignment(Pos.CENTER);
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
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            tableViewProyectos.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
