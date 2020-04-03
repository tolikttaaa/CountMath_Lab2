package exception;

import java.io.InvalidObjectException;

public class InvalidIntervalException extends InvalidObjectException {
    public InvalidIntervalException(String reason) {
        super(reason);
    }
}
