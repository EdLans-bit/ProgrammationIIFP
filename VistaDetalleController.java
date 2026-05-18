package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.DatosGlobales;
import model.ItemCompra;
import model.Producto;
import java.io.IOException;

public class VistaDetalleController {

    @FXML private Label lblNombre, lblPrecio, lblStock, lblEspecificaciones;
    @FXML private ImageView imgProducto;
    @FXML private TextField txtCantidad; 

    private Producto productoActual;

    public void setProducto(Producto p) {
        this.productoActual = p;
        lblNombre.setText(p.getNombre());
        lblPrecio.setText("$" + p.getPrecio() + " usd");
        lblStock.setText("Stock Disponible: " + p.getStock() + " unidades");
        lblEspecificaciones.setText(p.getEspecificacion());
        
        try {
            if (p.getImagen() != null && !p.getImagen().isEmpty()) {
                imgProducto.setImage(new Image(p.getImagen()));
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen del detalle.");
        }
    }

    @FXML
    private void disminuirCantidad(ActionEvent event) {
        int cantidad = Integer.parseInt(txtCantidad.getText());
        if (cantidad > 1) {
            txtCantidad.setText(String.valueOf(cantidad - 1));
        }
    }

    @FXML
    private void aumentarCantidad(ActionEvent event) {
        int cantidad = Integer.parseInt(txtCantidad.getText());
       
        if (cantidad < productoActual.getStock()) {
            txtCantidad.setText(String.valueOf(cantidad + 1));
        }
    }

    @FXML
    private void volverAlCatalogo(ActionEvent event) {
        abrirVista(event, "/vistas/VistaCatalogo.fxml");
    }

    @FXML
    private void irFavoritos(ActionEvent event) {
        abrirVista(event, "/vistas/VistaFavoritos.fxml");
    }

    @FXML
    private void irCarrito(ActionEvent event) {
        abrirVista(event, "/vistas/VistaCarrito.fxml");
    }

    @FXML
    private void agregarAlCarrito(ActionEvent event) {
        int cantidad = obtenerCantidad();

        if (productoActual == null) {
            return;
        }

        if (cantidad > productoActual.getStock()) {
            mostrarAlerta("Stock insuficiente", "No hay suficientes unidades disponibles.", Alert.AlertType.WARNING);
            return;
        }

        DatosGlobales.carritoCompras.encolar(new ItemCompra(productoActual, cantidad));
        mostrarAlerta("Carrito", productoActual.getNombre() + " fue agregado al carrito.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void agregarAFavoritos(ActionEvent event) {
        if (productoActual == null) {
            return;
        }

        DatosGlobales.favoritos.apilar(productoActual);
        mostrarAlerta("Favoritos", productoActual.getNombre() + " fue agregado a favoritos.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void comprarAhora(ActionEvent event) {
        int cantidad = obtenerCantidad();

        if (productoActual == null) {
            return;
        }

        if (cantidad > productoActual.getStock()) {
            mostrarAlerta("Stock insuficiente", "No hay suficientes unidades disponibles.", Alert.AlertType.WARNING);
            return;
        }

        productoActual.setStock(productoActual.getStock() - cantidad);
        DatosGlobales.historialCompras.encolar(new ItemCompra(productoActual, cantidad));
        lblStock.setText("Stock Disponible: " + productoActual.getStock() + " unidades");
        mostrarAlerta("Compra realizada", "La compra fue agregada al historial.", Alert.AlertType.INFORMATION);
    }

    private int obtenerCantidad() {
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad < 1) {
                txtCantidad.setText("1");
                return 1;
            }
            return cantidad;
        } catch (NumberFormatException e) {
            txtCantidad.setText("1");
            return 1;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void abrirVista(ActionEvent event, String ruta) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(ruta));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la vista solicitada.", Alert.AlertType.ERROR);
        }
    }
}
