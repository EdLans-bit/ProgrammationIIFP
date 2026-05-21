package model;

public class NodoCompra {

    public ItemCompra dato;
    public NodoCompra siguiente;

    public NodoCompra(ItemCompra dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
