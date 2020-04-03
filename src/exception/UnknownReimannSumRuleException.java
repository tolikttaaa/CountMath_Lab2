package exception;

public class UnknownReimannSumRuleException extends Exception {
    public UnknownReimannSumRuleException() {
        super("The proposed method does not exist!");
    }
}
