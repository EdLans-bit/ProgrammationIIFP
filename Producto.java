package model;

/**
 * @author UNICORDOBA
 */
public class Producto {
    
    private String nombre;
    private double precio;
    private int stock;
    private String especificacion;
    private String imagen;

    public Producto(String nombre, double precio, int stock, String especificacion, String imagen) {
       
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.especificacion = especificacion;
        this.imagen = imagen;
    }

    public Producto(String nom, String pre) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}