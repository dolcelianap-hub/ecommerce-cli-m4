package app.service;

import app.exceptions.CantidadInvalidaException;
import app.exceptions.ProductoNoExisteException;
import app.model.ItemCarrito;
import app.model.Producto;

import java.util.*;

public class Carrito {
    private final Map<Integer, ItemCarrito> itemsPorProductoId = new HashMap<>();

    public void agregar(Producto producto, int cantidad) {
        if (cantidad <= 0) throw new CantidadInvalidaException("cantidad debe ser > 0");
        int id = producto.getId();

        if (itemsPorProductoId.containsKey(id)) {
            ItemCarrito item = itemsPorProductoId.get(id);
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            itemsPorProductoId.put(id, new ItemCarrito(producto, cantidad));
        }
    }

    public void quitar(int productoId) {
        if (!itemsPorProductoId.containsKey(productoId)) {
            throw new ProductoNoExisteException("Ese producto no está en el carrito");
        }
        itemsPorProductoId.remove(productoId);
    }

    public List<ItemCarrito> getItems() {
        return new ArrayList<>(itemsPorProductoId.values());
    }

    public boolean estaVacio() {
        return itemsPorProductoId.isEmpty();
    }

    public double totalBase() {
        double sum = 0;
        for (ItemCarrito i : itemsPorProductoId.values()) sum += i.getSubtotal();
        return sum;
    }

    public void vaciar() {
        itemsPorProductoId.clear();
    }
}
