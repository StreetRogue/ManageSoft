package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.IProyectoRepositorio;
import co.edu.unicauca.managesoft.access.ProyectoRepositorioNeonDB;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.infra.IObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ListaProyectoPaneController implements IObserver {

    private Empresa empresa;
    private IProyectoRepositorio repositorio;

    public ListaProyectoPaneController( IProyectoRepositorio repositorio, Empresa empresa) {
        this.empresa = empresa;
        this.repositorio = repositorio;
    }
    
    @FXML
    private TableView<Proyecto> tableViewProyectos;
    @FXML
    private TableColumn<Proyecto, Integer> colNit;
    @FXML
    private TableColumn<Proyecto, String> colNombre;
    @FXML
    private TableColumn<Proyecto, String> colEstado;
    @FXML
    private TableColumn<Proyecto, String> colFechaCreacion;
    @FXML
    private TableColumn<Proyecto, String> colObjetivo;
    @FXML
    private TableColumn<Proyecto, Double> colPresupuesto;
    @FXML
    private TableColumn<Proyecto, String> colTiempoEstipulado;

    private ObservableList<Proyecto> proyectosList;

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
        cargarProyectos(empresa);
    }

    @FXML
    public void initialize() {
        proyectosList = FXCollections.observableArrayList();
        cargarProyectos(empresa);
        tableViewProyectos.setItems(proyectosList);
        //colNit.setCellValueFactory(new PropertyValueFactory<>("nombreProyecto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProyecto"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoProyecto"));
        colFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fechaPublicacionProyecto"));
        colObjetivo.setCellValueFactory(new PropertyValueFactory<>("objetivoProyecto"));
        colPresupuesto.setCellValueFactory(new PropertyValueFactory<>("presupuestoProyecto"));
        colTiempoEstipulado.setCellValueFactory(new PropertyValueFactory<>("maximoMesesProyecto"));
    }

    public void cargarProyectos(Empresa empresa) {
        List<Proyecto> proyectos = obtenerProyectosDesdeBaseDeDatos(empresa);
        
        proyectosList.clear();
        proyectosList.addAll(proyectos);

        for (Proyecto proyecto : proyectos) {
            proyecto.agregarObservador(this);
        }
    }

    @Override
    public void actualizar() {
        tableViewProyectos.refresh();
    }

    private List<Proyecto> obtenerProyectosDesdeBaseDeDatos(Empresa empresa) {
        return repositorio.listarProyectos(empresa);
    }
}
