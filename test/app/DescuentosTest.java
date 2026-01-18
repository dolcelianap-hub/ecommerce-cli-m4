package test.app;

import app.model.ItemCarrito;
import app.model.Producto;
import app.service.descuentos.DescuentoPorCategoria;
import app.service.descuentos.DescuentoPorMontoMinimo;
import app.service.descuentos.ReglaDescuento;

import java.util.List;

public class DescuentosTest {

    public static void main(String[] args) {
        testDescuentoMontoMinimo();
        testDescuentoCategoria();
        System.out.println("DescuentosTest OK");
    }

    private static void testDescuentoMontoMinimo() {
        // totalBase = 30000, 10% => 3000
        ReglaDescuento regla = new DescuentoPorMontoMinimo(30000, 0.10);

        double descuento = regla.calcularDescuento(List.of(), 30000);

        if (Math.abs(descuento - 3000.0) > 0.0001) {
            throw new AssertionError(
                "Descuento monto mínimo incorrecto. Esperado 3000, salió " + descuento
            );
        }
    }

    private static void testDescuentoCategoria() {
        // categoria "ropa", totalBase = 10000, 5% => 500
        Producto p = new Producto(1, "Polera", "ropa", 10000);
        ItemCarrito item = new ItemCarrito(p, 1);

        ReglaDescuento regla = new DescuentoPorCategoria("ropa", 0.05);

        double descuento = regla.calcularDescuento(List.of(item), 10000);

        if (Math.abs(descuento - 500.0) > 0.0001) {
            throw new AssertionError(
                "Descuento por categoría incorrecto. Esperado 500, salió " + descuento
            );
        }
    }
}

