package ro.msg.learning.shop.exception;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException() {
        super("Location not found");
    }
}
