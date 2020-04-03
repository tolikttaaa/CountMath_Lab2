import exception.InvalidIntervalException;

public class Interval extends Bounds {
    private boolean isLeftIncluded;
    private boolean isRightIncluded;

    private static final double EPS = 1e-14d;

    public Interval(
            double leftBound,
            boolean isLeftIncluded,
            double rightBound,
            boolean isRightIncluded
    ) throws InvalidIntervalException {
        super(leftBound, rightBound);
        this.isLeftIncluded = isLeftIncluded;
        this.isRightIncluded = isRightIncluded;

        validateInterval();
    }

    public Interval(double leftBound, double rightBound) throws InvalidIntervalException {
        this(leftBound, true, rightBound, true);
    }

    public Interval(double point) throws InvalidIntervalException {
        this(point, point);
    }

    public boolean isPoint() {
        return Math.abs(getRightBound() - getLeftBound()) < EPS;
    }

    public boolean isIntersect(Bounds that) {
        try {
            return isIntersect(
                    new Interval(
                            Math.min(that.getLeftBound(), that.getRightBound()), false,
                            Math.max(that.getLeftBound(), that.getRightBound()), false
                    )
            );
        } catch (InvalidIntervalException e) {
            System.err.println(e);
            System.err.println("Unavailable code!");
            return false;
        }
    }

    public boolean isIntersect(Interval that) {
        if (this.getLeftBound() + EPS < that.getLeftBound()) {
            if (this.getRightBound() + EPS < that.getLeftBound()) {
                return false;
            }

            if (Math.abs(this.getRightBound() - that.getLeftBound()) < EPS
                    && that.isLeftIncluded && this.isRightIncluded) {
                return true;
            }

            return this.getRightBound() + EPS > that.getLeftBound();
        } else if (Math.abs(this.getLeftBound() - that.getLeftBound()) < EPS) {
            if (this.isLeftIncluded && that.isLeftIncluded) {
                return true;
            }

            return (!this.isPoint() || that.isLeftIncluded)
                    && (!that.isPoint() || that.isRightIncluded);
        } else {
            if (that.getRightBound() + EPS < this.getLeftBound()) {
                return false;
            }

            if (Math.abs(that.getRightBound() - this.getLeftBound()) < EPS
                    && this.isLeftIncluded && that.isRightIncluded) {
                return true;
            }

            return that.getRightBound() + EPS > this.getLeftBound();
        }
    }

    private void validateInterval() throws InvalidIntervalException {
        if (getLeftBound() > getRightBound() + EPS) {
            throw new InvalidIntervalException("Right bound less than left bound!");
        }

        if (isPoint() && (!isRightIncluded || !isLeftIncluded)) {
            throw new InvalidIntervalException("Empty Interval");
        }
    }

    @Override
    public String toString() {
        String strLeftBound = isLeftIncluded ? "[" : "(";
        String strRightBound = isRightIncluded ? "]" : ")";

        String bounds = isPoint() ? String.format("%10.4f", getLeftBound())
                : String.format("%10.4f; %10.4f", getLeftBound(), getRightBound());

        return String.format("%s %s %s", strLeftBound, bounds, strRightBound);
    }
}
