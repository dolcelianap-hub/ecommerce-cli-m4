package app.service;

import app.model.ItemCarrito;
import app.model.Orden;
import app.service.descuentos.ReglaDescuento;

import java.util.ArrayList;
import java.util.List;

public class Tienda {
    private final Catalogo catalogo;
    private final Carrito carrito;
    private final List<ReglaDescuento> reglas;
    private int nextOrden = 1;

    public Tienda(Catalogo catalogo, Carrito carrito, List<ReglaDescuento> reglas) {
        this.catalogo = catalogo;
        this.carrito = carrito;
        this.reglas = reglas;
    }

    public Catalogo getCatalogo() { return catalogo; }
    public Carrito getCarrito() { return carrito; }
    public List<ReglaDescuento> getReglas() { return reglas; }

    public Orden confirmarCompra() {
        if (carrito.estaVacio()) {
            throw new IllegalStateException("No puedes confirmar con el carrito vacío");
        }

        double totalBase = carrito.totalBase();
        List<ItemCarrito> items = carrito.getItems();

        // detalle de descuentos
        double totalDescuentos = 0;
        for (ReglaDescuento r : reglas) {
            totalDescuentos += r.calcularDescuento(items, totalBase);
        }

        // evita que el descuento deje negativo el total
        if (totalDescuentos > totalBase) totalDescuentos = totalBase;

        double totalFinal = totalBase - totalDescuentos;

        // copiamos items a una lista nueva (para que la orden "congele" lo comprado)
        List<ItemCarrito> copia = new ArrayList<>(items);

        Orden orden = new Orden(nextOrden++, copia, totalBase, totalDescuentos, totalFinal);

        carrito.vaciar();
        return orden;
    }
}
