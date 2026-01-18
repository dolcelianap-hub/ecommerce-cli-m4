package app.model;

import java.util.Objects;

public class Producto {
    private final int id;
    private String nombre;
    private String categoria;
    private double precio;

    public Producto(int id, String nombre, String categoria, double precio) {
        if (id <= 0) throw new IllegalArgumentException("id debe ser > 0");
        setNombre(nombre);
        setCategoria(categoria);
        setPrecio(precio);
        this.id = id;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("nombre vacío");
        this.nombre = nombre.trim();
    }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) throw new IllegalArgumentException("categoria vacía");
        this.categoria = categoria.trim();
    }

    public void setPrecio(double precio) {
        if (precio <= 0) throw new IllegalArgumentException("precio debe ser > 0");
        this.precio = precio;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | $%.2f", id, nombre, categoria, precio);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto p = (Producto) o;
        return id == p.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
