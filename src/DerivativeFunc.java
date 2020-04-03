import exception.NotImplementedMethodException;

public interface DerivativeFunc extends Function{
    @Override
    default Function get2Derivative() throws NotImplementedMethodException {
        throw new NotImplementedMethodException();
    }

    @Override
    default Function get4Derivative() throws NotImplementedMethodException {
        throw new NotImplementedMethodException();
    }
}
