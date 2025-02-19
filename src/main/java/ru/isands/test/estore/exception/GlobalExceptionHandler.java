package ru.isands.test.estore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.isands.test.estore.model.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({GenderException.class})
    public ErrorResponse handleException(GenderException e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JobTitleNotFoundException.class)
    public ErrorResponse handleException(JobTitleNotFoundException e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShopException.class)
    public ErrorResponse handleException(ShopException e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFound.class)
    public ErrorResponse handleException(EntityNotFound e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityExist.class)
    public ErrorResponse handleException(EntityExist e) {
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
}
