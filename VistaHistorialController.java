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
import model.NodoCompra;
import model.Producto;

public class VistaHistorialController implements Initializable {

    @FXML private VBox contenedorHistorial;
    @FXML private Label lblComprasTotales;
    @FXML private Label lblTotalGastado;
    @FXML private Label lblUltimaCompra;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarHistorial();
    }

    private void cargarHistorial() {
        contenedorHistorial.getChildren().clear();
        NodoCompra aux = DatosGlobales.historialCompras.getFrente();

        if (aux == null) {
            Label vacio = new Label("Todavia no hay compras realizadas.");
            vacio.setStyle("-fx-font-size: 18; -fx-padding: 30;");
            contenedorHistorial.getChildren().add(vacio);
        }

        while (aux != null) {
            contenedorHistorial.getChildren().add(crearFila(aux.dato));
            aux = aux.siguiente;
        }

        lblComprasTotales.setText(String.valueOf(DatosGlobales.historialCompras.contarCompras()));
        lblTotalGastado.setText("$" + String.format("%.0f", DatosGlobales.historialCompras.calcularTotal()) + " usd");
        lblUltimaCompra.setText(DatosGlobales.historialCompras.ultimaCompra());
    }

    private HBox crearFila(ItemCompra item) {
        Producto producto = item.getProducto();

        HBox fila = new HBox(16);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setPrefHeight(122);
        fila.setStyle("-fx-border-color: transparent transparent #9ca3af transparent; -fx-padding: 14;");

        ImageView imagen = new ImageView(new Image(producto.getImagen()));
        imagen.setFitWidth(130);
        imagen.setFitHeight(78);
        imagen.setPreserveRatio(true);

        VBox datos = new VBox(14);
        datos.setPrefWidth(145);
        datos.getChildren().addAll(new Label(producto.getNombre()), new Label("Cantidad: " + item.getCantidad()));

        Label total = new Label("Total: $" + String.format("%.0f", item.getSubtotal()) + " usd");

        Button detalle = new Button("Ver Detalles");
        detalle.setDisable(true);
        detalle.setStyle("-fx-background-color: #c4b5fd; -fx-text-fill: white; -fx-background-radius: 8;");

        fila.getChildren().addAll(imagen, datos, total, detalle);
        return fila;
    }

    @FXML private void volverCatalogo(ActionEvent event) throws IOException { cambiarVista(event, "/vistas/VistaCatalogo.fxml"); }
    @FXML private void irFavoritos(ActionEvent event) throws IOException { cambiarVista(event, "/vistas/VistaFavoritos.fxml"); }
    @FXML private void irCarrito(ActionEvent event) throws IOException { cambiarVista(event, "/vistas/VistaCarrito.fxml"); }

    private void cambiarVista(ActionEvent event, String ruta) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(ruta));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
