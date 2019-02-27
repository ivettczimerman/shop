package ro.msg.learning.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(LocationWithRequiredProductsNotFoundException.class)
    protected ResponseEntity handleLocationNotFound(
            LocationWithRequiredProductsNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UnsuccessfulOrderCreationException.class)
    protected ResponseEntity handleUnsuccessfulOrderCreation(
            UnsuccessfulOrderCreationException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
