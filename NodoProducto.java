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
public class NodoProducto {
    
    public Producto dato;
    public NodoProducto siguiente;

    public NodoProducto(Producto dato) {
        this.dato = dato;
        this.siguiente = null;
    }
    
    
}
