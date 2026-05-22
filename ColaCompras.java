package model;

public class ColaCompras {

    private NodoCompra frente;
    private NodoCompra fin;

    public void encolar(ItemCompra item) {
        NodoCompra nuevo = new NodoCompra(item);

        if (frente == null) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
    }

    public ItemCompra desencolar() {
        if (frente == null) {
            return null;
        }

        ItemCompra item = frente.dato;
        frente = frente.siguiente;

        if (frente == null) {
            fin = null;
        }

        return item;
    }

    public boolean eliminar(String nombreProducto) {
        if (frente == null) {
            return false;
        }

        if (frente.dato.getProducto().getNombre().equalsIgnoreCase(nombreProducto)) {
            desencolar();
            return true;
        }

        NodoCompra aux = frente;
        while (aux.siguiente != null) {
            if (aux.siguiente.dato.getProducto().getNombre().equalsIgnoreCase(nombreProducto)) {
                if (aux.siguiente == fin) {
                    fin = aux;
                }
                aux.siguiente = aux.siguiente.siguiente;
                return true;
            }
            aux = aux.siguiente;
        }

        return false;
    }

    public boolean cambiarCantidad(String nombreProducto, int nuevaCantidad) {
        NodoCompra aux = frente;

        while (aux != null) {
            if (aux.dato.getProducto().getNombre().equalsIgnoreCase(nombreProducto)) {
                aux.dato.setCantidad(nuevaCantidad);
                return true;
            }
            aux = aux.siguiente;
        }

        return false;
    }

    public int contarCompras() {
        int total = 0;
        NodoCompra aux = frente;

        while (aux != null) {
            total++;
            aux = aux.siguiente;
        }

        return total;
    }

    public String ultimaCompra() {
        if (fin == null) {
            return "Sin compras";
        }

        return fin.dato.getProducto().getNombre();
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public NodoCompra getFrente() {
        return frente;
    }

    public int contarItems() {
        int total = 0;
        NodoCompra aux = frente;

        while (aux != null) {
            total += aux.dato.getCantidad();
            aux = aux.siguiente;
        }

        return total;
    }

    public double calcularTotal() {
        double total = 0;
        NodoCompra aux = frente;

        while (aux != null) {
            total += aux.dato.getSubtotal();
            aux = aux.siguiente;
        }

        return total;
    }

    public String resumen() {
        if (estaVacia()) {
            return "No hay productos para mostrar.";
        }

        StringBuilder sb = new StringBuilder();
        NodoCompra aux = frente;

        while (aux != null) {
            ItemCompra item = aux.dato;
            sb.append(item.getProducto().getNombre())
              .append(" x")
              .append(item.getCantidad())
              .append(" - $")
              .append(String.format("%.2f", item.getSubtotal()))
              .append("\n");
            aux = aux.siguiente;
        }

        sb.append("\nTotal: $").append(String.format("%.2f", calcularTotal()));
        return sb.toString();
    }
}
