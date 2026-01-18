package app;

import app.service.*;
import app.service.descuentos.*;
import app.ui.Consola;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Catalogo catalogo = new Catalogo();
        Carrito carrito = new Carrito();

        // Productos de ejemplo (para que puedas probar rápido)
        catalogo.crear("Polera", "ropa", 12990);
        catalogo.crear("Audífonos", "tecnologia", 19990);
        catalogo.crear("Tazón", "hogar", 5990);
        catalogo.crear("Chaqueta", "ropa", 39990);

        List<ReglaDescuento> reglas = List.of(
                new DescuentoPorMontoMinimo(30000, 0.10), // 10% si total >= 30000
                new DescuentoPorCategoria("ropa", 0.05)   // 5% si hay ropa
        );

        Tienda tienda = new Tienda(catalogo, carrito, reglas);
        Consola consola = new Consola(tienda);
        consola.iniciar();
    }
}
