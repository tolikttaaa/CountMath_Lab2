import exception.NotAllowedScopeException;
import exception.NotImplementedMethodException;

public interface Function {
    double EPS = 1e-9d;
    double DOUBLE_MAX_VALUE = 1e30d;

    default double getValue(double argument) throws NotAllowedScopeException {
        double res = get(argument);

        if (isCountableValue(res)) {
            return res;
        }

        res = (get(argument - EPS) + get(argument + EPS)) / 2;

        if (isCountableValue(res)) {
            return res;
        }

        res = get(argument - EPS);

        if (isCountableValue(res)) {
            return res;
        }

        res = get(argument + EPS);

        if (isCountableValue(res)) {
            return res;
        }

        throw new NotAllowedScopeException();
    }

    double get(double argument);

    default boolean isCountableValue(double value) {
        if (Double.isNaN(value)) {
            return false;
        }

        if (Math.abs(value) > DOUBLE_MAX_VALUE) {
            return false;
        }

        return true;
    }

    Interval[] getNotAllowedScope();
    Function get2Derivative() throws NotImplementedMethodException;
    Function get4Derivative() throws NotImplementedMethodException;

    default double getMaxValue(Bounds bounds) throws NotAllowedScopeException {
        int countOfSections = Math.min((int) (Math.abs(bounds.getLength()) * 10000), 1_000_000);

        double maximum = 0d;
        double step = bounds.getLength() / countOfSections;
        double curLeftBound = bounds.getLeftBound();

        for (int i = 0; i < countOfSections; i++) {
            maximum = Math.max(maximum, Math.abs(getValue(curLeftBound)));
            curLeftBound += step;
        }

        return maximum;
    }
}
