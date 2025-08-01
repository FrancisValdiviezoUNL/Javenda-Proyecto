package edu.unl.cc.javenda.exception;

public class EntityNotFoundException extends Exception{

    public EntityNotFoundException() {
        super("Entidad no encontrada.");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
