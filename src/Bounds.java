public class Bounds {
    private double leftBound;
    private double rightBound;

    public Bounds(double leftBound, double rightBound) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public double getLength() {
        return rightBound - leftBound;
    }

    public double getLeftBound() {
        return leftBound;
    }

    public double getRightBound() {
        return rightBound;
    }
}
