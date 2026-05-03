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
    
    //Método para agregar productos
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
    
    //Método para buscar producto
    public Producto buscarProducto (String nombre){
        
        NodoProducto aux = lista.getInicio();
        
        while (aux != null){
            if (aux.dato.getNombre().equalsIgnoreCase(nombre)){
                return aux.dato;
            } 
            aux = aux.siguiente;
        }
        
        return null;
    }
    
    //Método para eliminar primer producto encontrado
    public boolean eliminarProducto(String nombre){
          NodoProducto aux = lista.getInicio();
          NodoProducto anterior = null;

        while (aux != null) {

            if (aux.dato.getNombre().equalsIgnoreCase(nombre)) {

                if (anterior == null) {
                    lista.setInicio(aux.siguiente);
                } else {
                    anterior.siguiente = aux.siguiente;
                }

                return true;
            }

            anterior = aux;
            aux = aux.siguiente;
        }

        return false;
    }

    //Método para actualizar stock
    public boolean actualizarStock(String nombre, int nuevoStock) {

        Producto p = buscarProducto(nombre);

        if (p != null) {
            p.setStock(nuevoStock);
            return true;
        }

        return false;
    }
    }
