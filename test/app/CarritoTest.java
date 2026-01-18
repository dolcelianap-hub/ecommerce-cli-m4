package test.app;

import app.exceptions.CantidadInvalidaException;
import app.model.Producto;
import app.service.Carrito;

public class CarritoTest {

    public static void main(String[] args) {
        testTotalBase();
        testCantidadInvalida();
        System.out.println("CarritoTest OK");
    }

    private static void testTotalBase() {
        Carrito carrito = new Carrito();
        Producto p = new Producto(1, "Test", "test", 1000);

        carrito.agregar(p, 2);

        double total = carrito.totalBase();
        if (Math.abs(total - 2000.0) > 0.0001) {
            throw new AssertionError(
                "Total base incorrecto. Esperado 2000, salió " + total
            );
        }
    }

    private static void testCantidadInvalida() {
        Carrito carrito = new Carrito();
        Producto p = new Producto(1, "Test", "test", 1000);

        try {
            carrito.agregar(p, 0);
            throw new AssertionError(
                "Se esperaba CantidadInvalidaException, pero no ocurrió"
            );
        } catch (CantidadInvalidaException e) {
            // OK
        }
    }
}
