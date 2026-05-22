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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DatosGlobales;
import model.ItemCompra;
import model.NodoProducto;
import model.Producto;

public class VistaCatalogoController implements Initializable {

    @FXML private FlowPane contenedorProductos;
    @FXML private Button btnSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (DatosGlobales.esAdmin) {
            btnSesion.setText("Volver");
            btnSesion.setStyle("-fx-background-color: #0066FF; -fx-text-fill: white; -fx-font-weight: bold;");
        } else {
            btnSesion.setText("Cerrar Sesion");
            btnSesion.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        cargarProductos();
    }

    private void cargarProductos() {
        contenedorProductos.getChildren().clear();

        NodoProducto aux = DatosGlobales.listaEnlazada.getInicio();

        while (aux != null) {
            Producto p = aux.getProducto();

            VBox tarjeta = new VBox(8);
            tarjeta.setAlignment(Pos.CENTER);
            tarjeta.setPrefSize(280, 175);
            tarjeta.setStyle("-fx-background-color: white; -fx-padding: 8 16 10 16; -fx-background-radius: 10; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.18), 4, 0, 2, 2);");

            Label lblNombre = new Label(p.getNombre());
            lblNombre.setStyle("-fx-font-size: 14; -fx-text-fill: #1f2937;");

            Button btnFavorito = new Button("♡");
            btnFavorito.setStyle("-fx-background-color: transparent; -fx-text-fill: #ef4444; -fx-font-size: 24; -fx-padding: 0;");
            btnFavorito.setOnAction(event -> {
                agregarAFavoritos(p);
                mostrarAlerta("Favoritos", p.getNombre() + " fue agregado a favoritos.");
            });

            HBox encabezado = new HBox();
            encabezado.setAlignment(Pos.CENTER);
            encabezado.getChildren().addAll(new Region(), lblNombre, new Region(), btnFavorito);
            HBox.setHgrow(encabezado.getChildren().get(0), javafx.scene.layout.Priority.ALWAYS);
            HBox.setHgrow(encabezado.getChildren().get(2), javafx.scene.layout.Priority.ALWAYS);

            ImageView imgView = crearImagenProducto(p);

            Label lblPrecio = new Label("$" + String.format("%.0f", p.getPrecio()) + " usd");

            Button btnComprar = new Button("Comprar");
            btnComprar.setPrefWidth(110);
            btnComprar.setStyle("-fx-background-color: #0066FF; -fx-text-fill: white;");
            btnComprar.setOnAction(event -> abrirDetalles(p));

            Button btnCarrito = new Button("🛒");
            btnCarrito.setStyle("-fx-background-color: #2096c7; -fx-text-fill: white; -fx-background-radius: 20;");
            btnCarrito.setOnAction(event -> {
                agregarAlCarrito(p, 1);
                mostrarAlerta("Carrito", p.getNombre() + " fue agregado al carrito.");
            });

            HBox acciones = new HBox(36);
            acciones.setAlignment(Pos.CENTER);
            acciones.getChildren().addAll(btnComprar, btnCarrito);

            tarjeta.getChildren().addAll(encabezado, imgView, lblPrecio, acciones);
            tarjeta.setOnMouseClicked(event -> {
                if (clickVieneDeBoton(event.getTarget())) {
                    return;
                }
                abrirDetalles(p);
            });
            contenedorProductos.getChildren().add(tarjeta);

            aux = aux.siguiente;
        }
    }

    private ImageView crearImagenProducto(Producto p) {
        ImageView imgView = new ImageView();
        imgView.setFitHeight(120);
        imgView.setFitWidth(150);
        imgView.setPreserveRatio(true);

        try {
            String ruta = p.getImagen();
            if (ruta != null && !ruta.isEmpty()) {
                Image img = new Image(ruta);
                if (!img.isError()) {
                    imgView.setImage(img);
                    return imgView;
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen del producto.");
        }

        imgView.setImage(new Image("https://dummyimage.com/150x150/e0e0e0/003399.png&text=Sin+Foto"));
        return imgView;
    }

    private void agregarAlCarrito(Producto producto, int cantidad) {
        if (producto.getStock() <= 0) {
            mostrarAlerta("Sin stock", "Este producto no tiene unidades disponibles.");
            return;
        }

        DatosGlobales.carritoCompras.encolar(new ItemCompra(producto, cantidad));
    }

    private void agregarAFavoritos(Producto producto) {
        DatosGlobales.favoritos.apilar(producto);
    }

    @FXML
    public void irCarrito(ActionEvent event) {
        abrirVista(event, "/vistas/VistaCarrito.fxml");
    }

    @FXML
    public void irFavoritos(ActionEvent event) {
        abrirVista(event, "/vistas/VistaFavoritos.fxml");
    }

    @FXML
    public void irHistorial(ActionEvent event) {
        abrirVista(event, "/vistas/VistaHistorial.fxml");
    }

    @FXML
    public void manejarAccionSesion(ActionEvent event) {
        try {
            String rutaDestino;

            if (DatosGlobales.esAdmin) {
                rutaDestino = "/vistas/VistaAdminGestion.fxml";
            } else {
                rutaDestino = "/vistas/Vistalogin.fxml";
            }

            cambiarVista(event, rutaDestino);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirDetalles(Producto p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/VistaDetalle.fxml"));
            Parent root = loader.load();

            VistaDetalleController controller = loader.getController();
            controller.setProducto(p);

            Stage stage = (Stage) contenedorProductos.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cambiarVista(ActionEvent event, String ruta) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(ruta));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void abrirVista(ActionEvent event, String ruta) {
        try {
            cambiarVista(event, ruta);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la vista solicitada.");
        }
    }

    private boolean clickVieneDeBoton(Object target) {
        if (!(target instanceof Node)) {
            return false;
        }

        Node nodo = (Node) target;
        while (nodo != null) {
            if (nodo instanceof Button) {
                return true;
            }
            nodo = nodo.getParent();
        }

        return false;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
