package app.model;

import java.util.List;

public class Orden {
    private final int numero;
    private final List<ItemCarrito> items;
    private final double totalBase;
    private final double totalDescuentos;
    private final double totalFinal;

    public Orden(int numero, List<ItemCarrito> items, double totalBase, double totalDescuentos, double totalFinal) {
        this.numero = numero;
        this.items = items;
        this.totalBase = totalBase;
        this.totalDescuentos = totalDescuentos;
        this.totalFinal = totalFinal;
    }

    public int getNumero() { return numero; }
    public List<ItemCarrito> getItems() { return items; }
    public double getTotalBase() { return totalBase; }
    public double getTotalDescuentos() { return totalDescuentos; }
    public double getTotalFinal() { return totalFinal; }
}
