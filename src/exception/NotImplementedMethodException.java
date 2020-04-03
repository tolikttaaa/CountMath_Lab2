package exception;

public class NotImplementedMethodException extends Exception {
    public NotImplementedMethodException(String s) {
        super(s);
    }

    public NotImplementedMethodException() {
        super("This method does not implement yet!");
    }
}
