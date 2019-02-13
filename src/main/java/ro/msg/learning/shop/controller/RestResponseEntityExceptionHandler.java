package ro.msg.learning.shop.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.msg.learning.shop.exception.LocationWithRequiredProductsNotFoundException;
import ro.msg.learning.shop.exception.UnsuccessfulOrderCreationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = LocationWithRequiredProductsNotFoundException.class)
    protected ResponseEntity<Object> handleLocationNotFound(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "No location was found for all required products";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value
            = UnsuccessfulOrderCreationException.class)
    protected ResponseEntity<Object> handleUnsuccessfulOrderCreation(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Order creation failed";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
