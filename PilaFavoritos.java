package model;

public class PilaFavoritos {

    private NodoProducto cima;

    public void apilar(Producto producto) {
        if (contiene(producto.getNombre())) {
            return;
        }

        NodoProducto nuevo = new NodoProducto(producto);
        nuevo.siguiente = cima;
        cima = nuevo;
    }

    public Producto desapilar() {
        if (cima == null) {
            return null;
        }

        Producto producto = cima.dato;
        cima = cima.siguiente;
        return producto;
    }

    public boolean quitar(String nombreProducto) {
        if (cima == null) {
            return false;
        }

        if (cima.dato.getNombre().equalsIgnoreCase(nombreProducto)) {
            cima = cima.siguiente;
            return true;
        }

        NodoProducto aux = cima;
        while (aux.siguiente != null) {
            if (aux.siguiente.dato.getNombre().equalsIgnoreCase(nombreProducto)) {
                aux.siguiente = aux.siguiente.siguiente;
                return true;
            }
            aux = aux.siguiente;
        }

        return false;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public boolean contiene(String nombreProducto) {
        NodoProducto aux = cima;

        while (aux != null) {
            if (aux.dato.getNombre().equalsIgnoreCase(nombreProducto)) {
                return true;
            }
            aux = aux.siguiente;
        }

        return false;
    }

    public int contar() {
        int total = 0;
        NodoProducto aux = cima;

        while (aux != null) {
            total++;
            aux = aux.siguiente;
        }

        return total;
    }

    public double precioPromedio() {
        int totalProductos = 0;
        double suma = 0;
        NodoProducto aux = cima;

        while (aux != null) {
            totalProductos++;
            suma += aux.dato.getPrecio();
            aux = aux.siguiente;
        }

        if (totalProductos == 0) {
            return 0;
        }

        return suma / totalProductos;
    }

    public NodoProducto getCima() {
        return cima;
    }

    public String resumen() {
        if (estaVacia()) {
            return "No hay productos favoritos.";
        }

        StringBuilder sb = new StringBuilder();
        NodoProducto aux = cima;

        while (aux != null) {
            sb.append(aux.dato.getNombre())
              .append(" - $")
              .append(String.format("%.2f", aux.dato.getPrecio()))
              .append("\n");
            aux = aux.siguiente;
        }

        return sb.toString();
    }
}
