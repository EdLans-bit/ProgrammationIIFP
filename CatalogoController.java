/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import estructuras.ListaProductos;
import estructuras.NodoProducto;
import model.Producto;
/**
 *
 * @author UNICORDOBA
 */
public class CatalogoController {
    
    private ListaProductos lista = new ListaProductos();

    public void agregarProducto(String nombre, double precio, int stock,
                                String especificacion, String imagen) {

        Producto nuevo = new Producto(nombre, precio, stock, especificacion, imagen);

        lista.agregar(nuevo);
    
    }
    
    //Método para mostrar el catalogo
    public void mostrarProductos(){
        
        NodoProducto aux = lista.getInicio();
        
        while (aux != null){
            
            System.out.println("Nombre: " + aux.dato.getNombre());
            System.out.println("Precio: " + aux.dato.getPrecio());
            System.out.println("Stock: " + aux.dato.getStock());
            System.out.println("Especificacion: " + aux.dato.getEspecificacion());
            System.out.println("Imagen: " + aux.dato.getImagen());
            
            aux = aux.siguiente;
        }
        
    }

}
