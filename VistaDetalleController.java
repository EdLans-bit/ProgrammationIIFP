package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("/vistas/VistaCatalogo.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Error al regresar al catálogo.");
            e.printStackTrace();
        }
    }
}