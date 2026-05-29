package Main;

import controller.LoginController;
import controller.CatalogoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author UNICORDOBA
 */
public class mainsito extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Ejecutamos las pruebas de consola aquí para verlas al abrir la ventana
        ejecutarPruebasConsola();

        // Cargamos la interfaz gráfica
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/Vistalogin.fxml"));
        Scene scene = new Scene(root);
        
        stage.setTitle("Sistema de Ventas - Login");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void ejecutarPruebasConsola() {
        System.out.println("--- Iniciando Pruebas de Lógica ---");
        
        // Prueba del Login
        LoginController login = new LoginController();
        String rol = login.validarLogin("elans12@gmail.com", "adminShop");
        System.out.println("Rol detectado: " + rol);

        // Prueba del Catalogo
        CatalogoController catalogo = new CatalogoController();
        catalogo.agregarProducto("Televisor", 2500000, 10, "4K UHD", "tv.png");
        catalogo.agregarProducto("Mouse Gamer", 150000, 20, "RGB", "mouse.png");

        catalogo.mostrarProductos();
        catalogo.eliminarProducto("Mouse Gamer");
        catalogo.actualizarStock("Televisor", 4);
        
        System.out.println("Después de Cambios:");
        catalogo.mostrarProductos();
        System.out.println("--- Fin de Pruebas. Abriendo Interfaz... ---");
    }

    public static void main(String[] args) {
        // launch(args) siempre debe ser lo último o lo único en el main de Application
        launch(args);
    }
    
}