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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DatosGlobales;
import model.ItemCompra;
import model.NodoProducto;
import model.Producto;

public class VistaFavoritosController implements Initializable {

    @FXML private VBox contenedorFavoritos;
    @FXML private Label lblGuardados;
    @FXML private Label lblPromedio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarFavoritos();
    }

    private void cargarFavoritos() {
        contenedorFavoritos.getChildren().clear();
        NodoProducto aux = DatosGlobales.favoritos.getCima();

        if (aux == null) {
            Label vacio = new Label("No hay productos favoritos.");
            vacio.setStyle("-fx-font-size: 18; -fx-padding: 30;");
            contenedorFavoritos.getChildren().add(vacio);
        }

        while (aux != null) {
            contenedorFavoritos.getChildren().add(crearFila(aux.getProducto()));
            aux = aux.getSiguiente();
        }

        lblGuardados.setText(String.valueOf(DatosGlobales.favoritos.contar()));
        lblPromedio.setText("$" + String.format("%.0f", DatosGlobales.favoritos.precioPromedio()) + " usd");
    }

    private HBox crearFila(Producto producto) {
        HBox fila = new HBox(16);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setPrefHeight(122);
        fila.setStyle("-fx-border-color: transparent transparent #9ca3af transparent; -fx-padding: 14;");

        ImageView imagen = new ImageView(new Image(producto.getImagen()));
        imagen.setFitWidth(130);
        imagen.setFitHeight(78);
        imagen.setPreserveRatio(true);

        VBox datos = new VBox(14);
        datos.setPrefWidth(320);
        datos.getChildren().addAll(
                new Label(producto.getNombre() + "   Precio: $" + String.format("%.0f", producto.getPrecio()) + " usd"),
                new Label("Guardado!!!  ♥")
        );

        VBox acciones = new VBox(16);
        Button agregar = new Button("Agregar al Carrito");
        agregar.setStyle("-fx-background-color: #1f3f90; -fx-text-fill: white; -fx-background-radius: 8;");
        agregar.setOnAction(event -> DatosGlobales.carritoCompras.encolar(new ItemCompra(producto, 1)));

        Button quitar = new Button("Quitar");
        quitar.setStyle("-fx-background-color: #d7301f; -fx-text-fill: white; -fx-background-radius: 8;");
        quitar.setOnAction(event -> {
            DatosGlobales.favoritos.quitar(producto.getNombre());
            cargarFavoritos();
        });

        acciones.getChildren().addAll(agregar, quitar);
        fila.getChildren().addAll(imagen, datos, acciones);
        return fila;
    }

    @FXML private void volverCatalogo(ActionEvent event) throws IOException { cambiarVista(event, "/vistas/VistaCatalogo.fxml"); }
    @FXML private void irFavoritos(ActionEvent event) { cargarFavoritos(); }
    @FXML private void irCarrito(ActionEvent event) throws IOException { cambiarVista(event, "/vistas/VistaCarrito.fxml"); }

    private void cambiarVista(ActionEvent event, String ruta) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(ruta));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
