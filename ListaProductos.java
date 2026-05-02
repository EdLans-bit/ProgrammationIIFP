/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import model.Producto;
/**
 *
 * @author UNICORDOBA
 */
public class ListaProductos {
    
    private NodoProducto inicio;
    
    public ListaProductos(){
        inicio = null;
    }
    //Método para crear productos.
    public void agregar(Producto producto){
        
        NodoProducto nuevoNodo = new NodoProducto(producto);
        
        if (inicio == null){
            inicio = nuevoNodo;
        } else {
            NodoProducto aux = inicio;
            
            while (aux.siguiente != null){
                aux = aux.siguiente;
            }
            aux.siguiente = nuevoNodo;
        }
    }
    
    public NodoProducto getInicio() {
        return inicio;
    }

}
