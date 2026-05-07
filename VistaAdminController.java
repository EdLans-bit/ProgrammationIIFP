package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VistaAdminController implements Initializable {

    /**
     * Este método se ejecuta automáticamente cuando la ventana carga.
     * Por ahora lo dejamos vacío, pero aquí puedes configurar cosas iniciales.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializaciones futuras aquí
    }

    /**
     * Método para regresar a la vista de Login de Cliente
     */
    @FXML
    private void volverAlLogin(ActionEvent event) {
        try {
            // 1. Cargamos el archivo FXML de la vista original (Login)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Vistalogin.fxml"));
            Parent root = loader.load();
            
            // 2. Obtenemos el "Stage" (la ventana actual) desde el enlace que se hizo clic
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 3. Creamos la escena y actualizamos la ventana
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la vista de Login");
        }
    }
}