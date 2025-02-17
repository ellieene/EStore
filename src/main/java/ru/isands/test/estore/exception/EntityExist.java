package ru.isands.test.estore.exception;

public class EntityExist extends RuntimeException {
    public EntityExist(String message) {
        super(message);
    }
}
