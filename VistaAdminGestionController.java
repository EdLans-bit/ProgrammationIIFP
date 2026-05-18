package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import model.DatosGlobales;
import model.NodoProducto;
import model.Producto;

public class VistaAdminGestionController implements Initializable {

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    
    @FXML private TextField txtNombreNuevo;
    @FXML private TextField txtPrecioNuevo;
    @FXML private TextField txtImagenNueva;
    @FXML private TextArea txtEspecificacionNueva;
    
    @FXML private Label lblTotalProductos;
    @FXML private Label lblStockBajo;
    @FXML private Label lblValorInventario;

    private ObservableList<Producto> listaVisual = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        actualizarTabla();
    }

    private void actualizarTabla() {
        listaVisual.clear();
        int total = 0;
        int bajoStock = 0;
        double valorTotal = 0;

        NodoProducto aux = DatosGlobales.listaEnlazada.getInicio();
        while (aux != null) {
            Producto p = aux.getProducto();
            listaVisual.add(p);
            
           
            total++;
            if (p.getStock() < 5) bajoStock++;
            valorTotal += (p.getPrecio() * p.getStock());
            
            aux = aux.siguiente;
        }

        tablaProductos.setItems(listaVisual);
        
       
        lblTotalProductos.setText(String.valueOf(total));
        lblStockBajo.setText(String.valueOf(bajoStock));
        lblValorInventario.setText(String.format("$%.2f", valorTotal));
    }

    @FXML
    private void seleccionarImagen(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) txtImagenNueva.setText(file.toURI().toString());
    }



@FXML
private void agregarProducto(ActionEvent event) {
    try {
        String nom = txtNombreNuevo.getText().trim();
        String precioTexto = txtPrecioNuevo.getText().trim();
        String espec = txtEspecificacionNueva.getText(); 
        String img = txtImagenNueva.getText();

        if (nom.isEmpty() || precioTexto.isEmpty()) {
            System.out.println("Error: Campos vacíos");
            return;
        }

        double pre = Double.parseDouble(precioTexto);

      
        boolean encontrado = false;
        model.NodoProducto aux = DatosGlobales.listaEnlazada.getInicio();
        while (aux != null) {
            if (aux.getProducto().getNombre().equalsIgnoreCase(nom)) {
                aux.getProducto().setStock(aux.getProducto().getStock() + 1);
                encontrado = true;
                break;
            }
            aux = aux.getSiguiente();
        }

        if (!encontrado) {
            
            model.Producto nuevo = new model.Producto(nom, pre, 1, espec, img);
            DatosGlobales.listaEnlazada.agregar(nuevo);
        }

       
        txtNombreNuevo.clear();
        txtPrecioNuevo.clear();
        txtEspecificacionNueva.clear();
        actualizarTabla();

    } catch (Exception e) {
        
        System.out.println("Error real: " + e.getMessage());
        e.printStackTrace(); 
    }
}

    @FXML
    private void eliminarProducto(ActionEvent event) {
        Producto sel = tablaProductos.getSelectionModel().getSelectedItem();
        if (sel != null) {
            DatosGlobales.listaEnlazada.eliminar(sel.getNombre());
            actualizarTabla();
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/Vistalogin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
 @FXML
private void irAlCatalogo(ActionEvent event) {
  
    DatosGlobales.esAdmin = true; 
    
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/VistaCatalogo.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        System.out.println("Error al abrir el catálogo: " + e.getMessage());
        e.printStackTrace();
    }
}
  
}