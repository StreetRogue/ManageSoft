/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.IProyectoRepositorio;
import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Coordinador;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.infra.IObserver;
import java.io.IOException;
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
public class ListaProyectoCoordinadorPaneController implements IObserver {

    private IProyectoRepositorio repositorioProyecto;
    private Repositorio repositorio;
    private Coordinador coordinador;

    public ListaProyectoCoordinadorPaneController(IProyectoRepositorio repositorioProyecto, Coordinador coordinador) {
        this.repositorioProyecto = repositorioProyecto;
        this.coordinador = coordinador;
    }

    public void setRepositorio(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

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
    private TableColumn<Proyecto, String> colObjetivo;
    @FXML
    private TableColumn<Proyecto, Double> colPresupuesto;
    @FXML
    private TableColumn<Proyecto, String> colTiempoEstipulado;
    @FXML
    private TableColumn<Proyecto, Void> colSetEstado;

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
        colObjetivo.setCellValueFactory(new PropertyValueFactory<>("objetivoProyecto"));
        colPresupuesto.setCellValueFactory(new PropertyValueFactory<>("presupuestoProyecto"));
        colTiempoEstipulado.setCellValueFactory(new PropertyValueFactory<>("maximoMesesProyecto"));

        configurarColumnaEstado();
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

    private void configurarColumnaEstado() {
        colSetEstado.setCellFactory(tc -> new TableCell<Proyecto, Void>() {
            private final Button btn = new Button();

            {
                Image image = new Image(getClass().getResourceAsStream("/co/edu/unicauca/managesoft/resources/modificarVector.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                btn.setGraphic(imageView);
                btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                btn.setOnAction(event -> {
                    Proyecto proyecto = getTableView().getItems().get(getIndex());
                    abrirVistaModificarProyecto(proyecto, coordinador);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    return;
                }

                setGraphic(btn);
                setAlignment(Pos.CENTER);
            }
        });
    }

    private void abrirVistaModificarProyecto(Proyecto proyecto, Coordinador coordinador) {
        try {
            ModificarProyectoController controller = new ModificarProyectoController(repositorio.getRepositorioProyecto(), repositorio.getRepositorioCorreo(), coordinador, proyecto);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modificarProyecto.fxml"));
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
