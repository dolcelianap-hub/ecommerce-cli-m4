package app.service.descuentos;

import app.model.ItemCarrito;

import java.util.List;

public class DescuentoPorMontoMinimo implements ReglaDescuento {
    private final double min;
    private final double porcentaje; // ej: 0.10 = 10%

    public DescuentoPorMontoMinimo(double min, double porcentaje) {
        this.min = min;
        this.porcentaje = porcentaje;
    }

    @Override
    public String nombre() {
        return "DescuentoPorMontoMinimo";
    }

    @Override
    public String condicion() {
        return String.format("Si totalBase >= $%.2f, descuenta %.0f%%", min, porcentaje * 100);
    }

    @Override
    public double calcularDescuento(List<ItemCarrito> items, double totalBase) {
        if (totalBase >= min) return totalBase * porcentaje;
        return 0;
    }
}
