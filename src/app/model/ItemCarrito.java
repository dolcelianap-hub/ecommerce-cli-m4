package app.model;

public class ItemCarrito {
    private final Producto producto;
    private int cantidad;

    public ItemCarrito(Producto producto, int cantidad) {
        if (producto == null) throw new IllegalArgumentException("producto null");
        setCantidad(cantidad);
        this.producto = producto;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("cantidad debe ser > 0");
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return String.format("%s x%d = $%.2f", producto.getNombre(), cantidad, getSubtotal());
    }
}
