package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DatosGlobales;
import model.ItemCompra;
import model.NodoCompra;
import model.Producto;

public class VistaCarritoController implements Initializable {

    @FXML private VBox contenedorCarrito;
    @FXML private Label lblSubtotal;
    @FXML private Label lblTotal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCarrito();
    }

    private void cargarCarrito() {
        contenedorCarrito.getChildren().clear();
        NodoCompra aux = DatosGlobales.carritoCompras.getFrente();

        if (aux == null) {
            Label vacio = new Label("No hay productos en el carrito.");
            vacio.setStyle("-fx-font-size: 18; -fx-padding: 30;");
            contenedorCarrito.getChildren().add(vacio);
        }

        while (aux != null) {
            contenedorCarrito.getChildren().add(crearFila(aux.dato));
            aux = aux.siguiente;
        }

        double total = DatosGlobales.carritoCompras.calcularTotal();
        lblSubtotal.setText("$" + String.format("%.0f", total) + " usd");
        lblTotal.setText("$" + String.format("%.0f", total) + " usd");
    }

    private HBox crearFila(ItemCompra item) {
        Producto producto = item.getProducto();

        HBox fila = new HBox(16);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setPrefHeight(120);
        fila.setStyle("-fx-border-color: transparent transparent #9ca3af transparent; -fx-padding: 14;");

        ImageView imagen = new ImageView(new Image(producto.getImagen()));
        imagen.setFitWidth(130);
        imagen.setFitHeight(78);
        imagen.setPreserveRatio(true);

        VBox datos = new VBox(14);
        datos.setPrefWidth(160);
        datos.getChildren().addAll(new Label(producto.getNombre()), new Label("Precio: $" + String.format("%.0f", producto.getPrecio()) + " usd"));

        HBox cantidad = new HBox(0);
        cantidad.setAlignment(Pos.CENTER);
        Button menos = new Button("-");
        Label numero = new Label(String.valueOf(item.getCantidad()));
        numero.setMinWidth(44);
        numero.setAlignment(Pos.CENTER);
        Button mas = new Button("+");
        menos.setOnAction(event -> cambiarCantidad(item, -1));
        mas.setOnAction(event -> cambiarCantidad(item, 1));
        cantidad.getChildren().addAll(menos, numero, mas);

        Label subtotal = new Label("Subtotal: $" + String.format("%.0f", item.getSubtotal()) + " usd");

        Button eliminar = new Button("x Eliminar");
        eliminar.setStyle("-fx-background-color: #d7301f; -fx-text-fill: white; -fx-background-radius: 8;");
        eliminar.setOnAction(event -> {
            DatosGlobales.carritoCompras.eliminar(producto.getNombre());
            cargarCarrito();
        });

        fila.getChildren().addAll(imagen, datos, cantidad, subtotal, eliminar);
        return fila;
    }

    private void cambiarCantidad(ItemCompra item, int cambio) {
        int nuevaCantidad = item.getCantidad() + cambio;

        if (nuevaCantidad < 1 || nuevaCantidad > item.getProducto().getStock()) {
            return;
        }

        DatosGlobales.carritoCompras.cambiarCantidad(item.getProducto().getNombre(), nuevaCantidad);
        cargarCarrito();
    }

    @FXML
    private void comprarAhora(ActionEvent event) {
        if (DatosGlobales.carritoCompras.estaVacia()) {
            mostrarAlerta("Carrito vacio", "Agrega productos antes de comprar.", Alert.AlertType.WARNING);
            return;
        }

        while (!DatosGlobales.carritoCompras.estaVacia()) {
            ItemCompra item = DatosGlobales.carritoCompras.desencolar();
            Producto producto = item.getProducto();

            if (producto.getStock() >= item.getCantidad()) {
                producto.setStock(producto.getStock() - item.getCantidad());
                DatosGlobales.historialCompras.encolar(item);
            }
        }

        cargarCarrito();
        mostrarAlerta("Compra realizada", "La compra fue agregada al historial.", Alert.AlertType.INFORMATION);
    }

    @FXML private void volverCatalogo(ActionEvent event) throws IOException { cambiarVista(event, "/vistas/VistaCatalogo.fxml"); }
    @FXML private void irFavoritos(ActionEvent event) throws IOException { cambiarVista(event, "/vistas/VistaFavoritos.fxml"); }
    @FXML private void irCarrito(ActionEvent event) throws IOException { cargarCarrito(); }

    private void cambiarVista(ActionEvent event, String ruta) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(ruta));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
