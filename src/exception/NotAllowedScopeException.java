package exception;

public class NotAllowedScopeException extends Exception {
    public NotAllowedScopeException() {
        super("Can't get integral in case of not allowed scope!");
    }
}
