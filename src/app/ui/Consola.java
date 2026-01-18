package app.ui;

import app.model.Orden;
import app.model.Producto;
import app.service.Tienda;
import app.service.descuentos.ReglaDescuento;

import java.util.List;
import java.util.Scanner;

public class Consola {
    private final Scanner sc = new Scanner(System.in);
    private final Tienda tienda;

    public Consola(Tienda tienda) {
        this.tienda = tienda;
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1) ADMIN");
            System.out.println("2) USUARIO");
            System.out.println("0) Salir");
            int op = leerInt("Elige: ");

            if (op == 1) menuAdmin();
            else if (op == 2) menuUsuario();
            else if (op == 0) {
                System.out.println("Chao 👋");
                return;
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    private void menuAdmin() {
        while (true) {
            System.out.println("\n--- ADMIN (gestión de productos) ---");
            System.out.println("1) Listar productos (por nombre)");
            System.out.println("2) Listar productos (por precio)");
            System.out.println("3) Buscar (nombre/categoría)");
            System.out.println("4) Crear producto");
            System.out.println("5) Editar producto");
            System.out.println("6) Eliminar producto");
            System.out.println("0) Volver");
            int op = leerInt("Elige: ");

            try {
                if (op == 1) listar(tienda.getCatalogo().listarOrdenadoPorNombre());
                else if (op == 2) listar(tienda.getCatalogo().listarOrdenadoPorPrecio());
                else if (op == 3) {
                    String t = leerTexto("Buscar: ");
                    listar(tienda.getCatalogo().buscar(t));
                }
                else if (op == 4) crearProducto();
                else if (op == 5) editarProducto();
                else if (op == 6) eliminarProducto();
                else if (op == 0) return;
                else System.out.println("Opción inválida.");
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void menuUsuario() {
        while (true) {
            System.out.println("\n--- USUARIO (carrito, descuentos y compra) ---");
            System.out.println("1) Listar productos");
            System.out.println("2) Buscar productos");
            System.out.println("3) Agregar al carrito (id, cantidad)");
            System.out.println("4) Quitar del carrito (id)");
            System.out.println("5) Ver carrito (items, subtotales, TOTAL base)");
            System.out.println("6) Ver descuentos activos");
            System.out.println("7) Confirmar compra");
            System.out.println("0) Volver");
            int op = leerInt("Elige: ");

            try {
                if (op == 1) listar(tienda.getCatalogo().listarOrdenadoPorNombre());
                else if (op == 2) {
                    String t = leerTexto("Buscar: ");
                    listar(tienda.getCatalogo().buscar(t));
                }
                else if (op == 3) agregarCarrito();
                else if (op == 4) quitarCarrito();
                else if (op == 5) verCarrito();
                else if (op == 6) verDescuentos();
                else if (op == 7) confirmarCompra();
                else if (op == 0) return;
                else System.out.println("Opción inválida.");
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void crearProducto() {
        String nombre = leerTexto("Nombre: ");
        String categoria = leerTexto("Categoría: ");
        double precio = leerDouble("Precio (>0): ");
        Producto p = tienda.getCatalogo().crear(nombre, categoria, precio);
        System.out.println("Creado: " + p);
    }

    private void editarProducto() {
        int id = leerInt("ID a editar: ");
        String nombre = leerTexto("Nuevo nombre (enter para saltar): ", true);
        String categoria = leerTexto("Nueva categoría (enter para saltar): ", true);
        String precioTxt = leerTexto("Nuevo precio (enter para saltar): ", true);

        Double precio = null;
        if (!precioTxt.isBlank()) precio = Double.parseDouble(precioTxt);

        tienda.getCatalogo().editar(id,
                nombre.isBlank() ? null : nombre,
                categoria.isBlank() ? null : categoria,
                precio);

        System.out.println("Editado OK.");
    }

    private void eliminarProducto() {
        int id = leerInt("ID a eliminar: ");
        String conf = leerTexto("¿Seguro? (s/n): ");
        if (conf.trim().equalsIgnoreCase("s")) {
            tienda.getCatalogo().eliminar(id);
            System.out.println("Eliminado.");
        } else {
            System.out.println("Cancelado.");
        }
    }

    private void agregarCarrito() {
        int id = leerInt("ID producto: ");
        int cant = leerInt("Cantidad (>0): ");
        Producto p = tienda.getCatalogo().buscarPorId(id);
        tienda.getCarrito().agregar(p, cant);
        System.out.println("Agregado: " + p.getNombre() + " x" + cant);
    }

    private void quitarCarrito() {
        int id = leerInt("ID producto a quitar: ");
        tienda.getCarrito().quitar(id);
        System.out.println("Quitado.");
    }

    private void verCarrito() {
        var items = tienda.getCarrito().getItems();
        if (items.isEmpty()) {
            System.out.println("(Carrito vacío)");
            return;
        }
        System.out.println("=== CARRITO ===");
        for (var i : items) {
            System.out.println("- " + i);
        }
        System.out.printf("TOTAL base: $%.2f%n", tienda.getCarrito().totalBase());
    }

    private void verDescuentos() {
        System.out.println("=== DESCUENTOS ACTIVOS ===");
        for (ReglaDescuento r : tienda.getReglas()) {
            System.out.println("- " + r.nombre() + ": " + r.condicion());
        }
    }

    private void confirmarCompra() {
        double totalBase = tienda.getCarrito().totalBase();
        var items = tienda.getCarrito().getItems();

        // Mostramos detalle (igual que en Tienda) pero sin duplicar lógica rara:
        double totalDescuentos = 0;
        System.out.println("=== DETALLE DESCUENTOS ===");
        for (ReglaDescuento r : tienda.getReglas()) {
            double d = r.calcularDescuento(items, totalBase);
            if (d > 0) {
                System.out.printf("- %s: -$%.2f%n", r.nombre(), d);
            }
            totalDescuentos += d;
        }
        if (totalDescuentos == 0) System.out.println("(No aplica ningún descuento)");
        if (totalDescuentos > totalBase) totalDescuentos = totalBase;

        Orden o = tienda.confirmarCompra();
        System.out.printf("TOTAL base: $%.2f%n", o.getTotalBase());
        System.out.printf("Descuentos: -$%.2f%n", o.getTotalDescuentos());
        System.out.printf("TOTAL final: $%.2f%n", o.getTotalFinal());
        System.out.println("Orden creada: #" + o.getNumero());
        System.out.println("Carrito quedó vacío ✅");
    }

    private void listar(List<Producto> productos) {
        if (productos.isEmpty()) {
            System.out.println("(Sin resultados)");
            return;
        }
        for (Producto p : productos) System.out.println(p);
    }

    // --------- Lectores seguros ----------
    private int leerInt(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine();
            try {
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un número. Intenta de nuevo.");
            }
        }
    }

    private double leerDouble(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine();
            try {
                return Double.parseDouble(s.trim());
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un número con decimales. Intenta de nuevo.");
            }
        }
    }

    private String leerTexto(String msg) {
        return leerTexto(msg, false);
    }

    private String leerTexto(String msg, boolean permitirVacio) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine();
            if (permitirVacio) return s;
            if (!s.trim().isEmpty()) return s.trim();
            System.out.println("No puede estar vacío.");
        }
    }
}
