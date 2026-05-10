/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Emiliano Andres
 */
public class VistaRegistroController {
    
    @FXML
    private void volverAlLogin(ActionEvent event) {
        try {
         
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Vistalogin.fxml"));
            Parent root = loader.load();
            
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la vista de Login");
        }
    }
    
}
