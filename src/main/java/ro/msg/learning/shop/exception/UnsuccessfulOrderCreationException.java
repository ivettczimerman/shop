package ro.msg.learning.shop.exception;

public class UnsuccessfulOrderCreationException extends RuntimeException {
    public UnsuccessfulOrderCreationException() {
        super("Unsuccessful order creation");
    }
}
