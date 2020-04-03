import exception.*;

public class ReimannSum {
    static private final long N_MAX_VALUE = 100_000_000L;
    static private final double DOUBLE_MAX_VALUE = 1e30d;

    public ReimannSumAnswer getReimannSum(
            Function function,
            Bounds bounds,
            double accuracy,
            ReimannSumRule rule,
            TypeOfSolution solutionType
    ) throws
            NotImplementedMethodException,
            UnknownReimannSumRuleException,
            NotAllowedScopeException,
            NotSolvableIntegralException {
        checkAllowedScope(function, bounds);

        switch (solutionType) {
            case SOLUTION_BY_FORMULAS:
                return getSumByFormulasSolution(function, bounds, accuracy, rule);
            case SOLUTION_BY_RUNGE:
                return getSumByRungeSolution(function, bounds, accuracy, rule);
            default:
                throw new NotImplementedSolutionException();
        }
    }

    public ReimannSumAnswer getReimannSum(
            Function function,
            Bounds bounds,
            double accuracy
    ) throws
            NotImplementedMethodException,
            UnknownReimannSumRuleException,
            NotAllowedScopeException,
            NotSolvableIntegralException {
        return getReimannSum(
                function,
                bounds,
                accuracy,
                ReimannSumRule.TRAPEZOIDAL_RULE,
                TypeOfSolution.SOLUTION_BY_RUNGE
        );
    }

    private void checkAllowedScope(Function function, Bounds bounds)
            throws NotAllowedScopeException {
        for (Interval notAllowedScope : function.getNotAllowedScope()) {
            if (notAllowedScope.isPoint()) continue;
            if (notAllowedScope.isIntersect(bounds)) {
                throw new NotAllowedScopeException();
            }
        }
    }

    private ReimannSumAnswer getSumByFormulasSolution(
            Function function,
            Bounds bounds,
            double accuracy,
            ReimannSumRule rule
    ) throws
            NotImplementedMethodException,
            UnknownReimannSumRuleException,
            NotAllowedScopeException {
        int n = getCountOfSections(function, bounds, accuracy, rule);

        return new ReimannSumAnswer(
                getSumByRuleByN(function, bounds, rule, n),
                Double.NaN,
                n
        );
    }

    private ReimannSumAnswer getSumByRungeSolution(
            Function function,
            Bounds bounds,
            double accuracy,
            ReimannSumRule rule
    ) throws
            NotImplementedSolutionException,
            UnknownReimannSumRuleException,
            NotSolvableIntegralException,
            NotAllowedScopeException {
        int n = 2;
        double curValue = getSumByRuleByN(function, bounds, rule, n);
        double prevValue;

        do {
            n <<= 1;

            prevValue = curValue;
            curValue = getSumByRuleByN(function, bounds, rule, n);

            if (n > N_MAX_VALUE || !isAvailableValue(curValue)) {
                throw new NotSolvableIntegralException();
            }
        } while (!(getMeasurementError(prevValue, curValue, rule) < accuracy));

        return new ReimannSumAnswer(
                curValue,
                getMeasurementError(prevValue, curValue, rule),
                n
        );
    }

    private boolean isAvailableValue(double curValue) {
        return Math.abs(curValue) < DOUBLE_MAX_VALUE;
    }

    private double getMeasurementError(double value1, double value2, ReimannSumRule rule)
            throws UnknownReimannSumRuleException {
        double res = Math.abs(value1 - value2);

        switch (rule) {
            case LEFT_RULE:
            case MIDPOINT_RULE:
            case RIGHT_RULE:
            case TRAPEZOIDAL_RULE:
                return res / 3;
            case SIMPSONS_RULE:
                return res / 15;
            default:
                throw new UnknownReimannSumRuleException();
        }
    }

    private double getSumByRuleByN(Function function, Bounds bounds, ReimannSumRule rule, int n)
            throws NotImplementedSolutionException, NotAllowedScopeException {
        double sum = 0d;
        double step = bounds.getLength() / n;
        double curLeftBound = bounds.getLeftBound();

        for (int i = 0; i < n; i++) {
            switch (rule) {
                case LEFT_RULE:
                    sum += function.getValue(curLeftBound) * step;
                    break;
                case RIGHT_RULE:
                    sum += function.getValue(curLeftBound + step) * step;
                    break;
                case MIDPOINT_RULE:
                    sum += function.getValue(
                            (curLeftBound * 2 + step) / 2
                    ) * step;
                    break;
                case TRAPEZOIDAL_RULE:
                    sum += (
                            function.getValue(curLeftBound) +
                            function.getValue(curLeftBound + step)
                    ) * step / 2;
                    break;
                case SIMPSONS_RULE:
                    //TODO: solve
                default:
                    throw new NotImplementedSolutionException();
            }

            curLeftBound += step;
        }

        return sum;
    }

    private int getCountOfSections(
            Function function,
            Bounds bounds,
            double accuracy,
            ReimannSumRule rule
    ) throws
            UnknownReimannSumRuleException,
            NotImplementedMethodException,
            NotAllowedScopeException {
        double res;

        switch (rule) {
            case LEFT_RULE:
            case RIGHT_RULE:
            case MIDPOINT_RULE:
                res = Math.sqrt(
                        Math.pow(bounds.getLength(), 3) *
                        function.get2Derivative().getMaxValue(bounds) / 24 / accuracy
                );
                return (int) (res + 1.0d);
            case SIMPSONS_RULE:
                res = Math.sqrt(
                        Math.sqrt(
                                Math.pow(bounds.getLength(), 5) *
                                function.get4Derivative().getMaxValue(bounds) / 180 / accuracy
                        )
                );
                return (int) (res + 1.0d);
            case TRAPEZOIDAL_RULE:
                res = Math.sqrt(
                        Math.pow(bounds.getLength(), 3) *
                        function.get2Derivative().getMaxValue(bounds) / 12 / accuracy
                );
                return (int) (res + 1.0d);
            default:
                throw new UnknownReimannSumRuleException();
        }
    }
}
