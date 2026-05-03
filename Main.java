/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import controller.LoginController;
import controller.CatalogoController;
import model.Producto;
/**
 *
 * @author UNICORDOBA
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Prueba del Login
        LoginController login = new LoginController();

        String rol = login.validarLogin(
                "elans12@gmail.com",
                "adminShop"
        );

        System.out.println("Rol detectado: " + rol);

        //Prueba del Catalogo
        CatalogoController catalogo = new CatalogoController();

        catalogo.agregarProducto(
                "Televisor",
                2500000,
                10,
                "4K UHD",
                "tv.png"
        );

        catalogo.agregarProducto(
                "Mouse Gamer",
                150000,
                20,
                "RGB",
                "mouse.png"
        );

        catalogo.mostrarProductos();
        catalogo.eliminarProducto("Mouse Gamer");
        catalogo.actualizarStock("Televisor", 4);
        
        System.out.println("Despues de Cambios");
        catalogo.mostrarProductos();
    } 
  
    
}
    

