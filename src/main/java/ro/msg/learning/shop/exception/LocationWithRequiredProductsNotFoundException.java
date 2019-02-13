package ro.msg.learning.shop.exception;

public class LocationWithRequiredProductsNotFoundException extends RuntimeException {
    public LocationWithRequiredProductsNotFoundException() {
        super("Location not found");
    }
}
