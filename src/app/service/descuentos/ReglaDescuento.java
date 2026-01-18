package app.service.descuentos;

import app.model.ItemCarrito;

import java.util.List;

public interface ReglaDescuento {
    String nombre();
    String condicion();
    double calcularDescuento(List<ItemCarrito> items, double totalBase);
}
