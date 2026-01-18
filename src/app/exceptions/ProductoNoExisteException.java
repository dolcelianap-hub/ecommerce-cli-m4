package app.exceptions;

public class ProductoNoExisteException extends RuntimeException {
    public ProductoNoExisteException(String msg) {
        super(msg);
    }
}
