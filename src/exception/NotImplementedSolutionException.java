package exception;

public class NotImplementedSolutionException extends NotImplementedMethodException {
    public NotImplementedSolutionException() {
        super("Solution by this rule does not implement yet!");
    }
}
