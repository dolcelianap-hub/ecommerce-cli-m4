package app.service.descuentos;

import app.model.ItemCarrito;

import java.util.List;

public class DescuentoPorCategoria implements ReglaDescuento {
    private final String categoria;
    private final double porcentaje;

    public DescuentoPorCategoria(String categoria, double porcentaje) {
        this.categoria = categoria.trim().toLowerCase();
        this.porcentaje = porcentaje;
    }

    @Override
    public String nombre() {
        return "DescuentoPorCategoria";
    }

    @Override
    public String condicion() {
        return String.format("Si el carrito contiene categoría '%s', descuenta %.0f%%", categoria, porcentaje * 100);
    }

    @Override
    public double calcularDescuento(List<ItemCarrito> items, double totalBase) {
        boolean existe = items.stream()
                .anyMatch(i -> i.getProducto().getCategoria().trim().toLowerCase().equals(categoria));
        if (existe) return totalBase * porcentaje;
        return 0;
    }
}
