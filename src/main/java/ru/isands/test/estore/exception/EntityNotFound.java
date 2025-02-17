package ru.isands.test.estore.exception;

public class EntityNotFound extends RuntimeException {
  public EntityNotFound(String message) {
    super(message);
  }
}
