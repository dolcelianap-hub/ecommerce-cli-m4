package app.service;

import app.exceptions.ProductoNoExisteException;
import app.model.Producto;

import java.util.*;
import java.util.stream.Collectors;

public class Catalogo {
    private final Map<Integer, Producto> productos = new HashMap<>();
    private int nextId = 1;

    public Producto crear(String nombre, String categoria, double precio) {
        int id = nextId++;
        Producto p = new Producto(id, nombre, categoria, precio);
        productos.put(id, p);
        return p;
    }

    public List<Producto> listarOrdenadoPorNombre() {
        return productos.values().stream()
                .sorted(Comparator.comparing(Producto::getNombre))
                .collect(Collectors.toList());
    }

    public List<Producto> listarOrdenadoPorPrecio() {
        return productos.values().stream()
                .sorted(Comparator.comparingDouble(Producto::getPrecio))
                .collect(Collectors.toList());
    }

    public Producto buscarPorId(int id) {
        Producto p = productos.get(id);
        if (p == null) throw new ProductoNoExisteException("No existe producto con id " + id);
        return p;
    }

    public List<Producto> buscar(String texto) {
        if (texto == null) texto = "";
        String t = texto.trim().toLowerCase();
        return productos.values().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(t)
                        || p.getCategoria().toLowerCase().contains(t))
                .sorted(Comparator.comparing(Producto::getNombre))
                .collect(Collectors.toList());
    }

    public void editar(int id, String nombre, String categoria, Double precio) {
        Producto p = buscarPorId(id);
        if (nombre != null && !nombre.trim().isEmpty()) p.setNombre(nombre);
        if (categoria != null && !categoria.trim().isEmpty()) p.setCategoria(categoria);
        if (precio != null) p.setPrecio(precio);
    }

    public void eliminar(int id) {
        if (!productos.containsKey(id)) throw new ProductoNoExisteException("No existe producto con id " + id);
        productos.remove(id);
    }

    public boolean existe(int id) {
        return productos.containsKey(id);
    }
}
