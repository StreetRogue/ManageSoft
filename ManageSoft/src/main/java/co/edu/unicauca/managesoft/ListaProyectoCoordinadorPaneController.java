    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
     */
    package co.edu.unicauca.managesoft;

    import co.edu.unicauca.managesoft.access.IProyectoRepositorio;
    import co.edu.unicauca.managesoft.access.Repositorio;
    import co.edu.unicauca.managesoft.entities.Coordinador;
    import co.edu.unicauca.managesoft.infra.ProyectTable;
    import co.edu.unicauca.managesoft.entities.Proyecto;
    import co.edu.unicauca.managesoft.infra.IObserver;
import co.edu.unicauca.managesoft.services.ProyectoServices;
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

        private Repositorio repositorio;
        private Coordinador coordinador;
        private ProyectoServices proyectoServices;

        public ListaProyectoCoordinadorPaneController(Coordinador coordinador) {
            this.coordinador = coordinador;
            this.proyectoServices = new ProyectoServices(this.repositorio.getRepositorioEmpresa().getRepositorioProyecto());
        }

        public void setRepositorio(Repositorio repositorio) {
            this.repositorio = repositorio;
        }

        @FXML
        private TableView<ProyectTable> tableViewProyectos;
        @FXML
        private TableColumn<ProyectTable, Integer> colId;
        @FXML
        private TableColumn<ProyectTable, String> colNombre;
        @FXML
        private TableColumn<ProyectTable, String> colEstado;
        @FXML
        private TableColumn<ProyectTable, String> colEmpresa;
        @FXML
        private TableColumn<ProyectTable, String> colObjetivo;
        @FXML
        private TableColumn<ProyectTable, Double> colPresupuesto;
        @FXML
        private TableColumn<ProyectTable, String> colTiempoEstipulado;
        @FXML
        private TableColumn<ProyectTable, Void> colSetEstado;

        private ObservableList<ProyectTable> proyectosEmpresaList;

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
            List<ProyectTable> proyectos = obtenerProyectosDesdeBaseDeDatos();
            proyectosEmpresaList.clear();
            proyectosEmpresaList.addAll(proyectos);

            for (ProyectTable proyecto : proyectos) {
                proyecto.agregarObservador(this);
            }
        }

        @Override
        public void actualizar() {
            tableViewProyectos.refresh();
        }

        private List<ProyectTable> obtenerProyectosDesdeBaseDeDatos() {
            return proyectoServices.listarProyectosGeneral();
        }

        private void configurarColumnaEstado() {
            colSetEstado.setCellFactory(tc -> new TableCell<ProyectTable, Void>() {
                private final Button btn = new Button();

                {
                    Image image = new Image(getClass().getResourceAsStream("/co/edu/unicauca/managesoft/resources/modificarVector.png"));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(20);
                    imageView.setFitHeight(20);
                    btn.setGraphic(imageView);
                    btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                    btn.setOnAction(event -> {
                        ProyectTable proyecto = getTableView().getItems().get(getIndex());
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

        private void abrirVistaModificarProyecto(ProyectTable proyecto, Coordinador coordinador) {
            try {
                ModificarProyectoController controller = new ModificarProyectoController(repositorio.getRepositorioEmpresa().getRepositorioProyecto(), repositorio.getRepositorioCorreo(), coordinador, proyecto);
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
