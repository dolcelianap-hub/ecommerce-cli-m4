package app.exceptions;

public class CantidadInvalidaException extends RuntimeException {
    public CantidadInvalidaException(String msg) {
        super(msg);
    }
}
