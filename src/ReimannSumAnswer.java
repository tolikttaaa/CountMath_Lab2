public class ReimannSumAnswer {
    private double value;
    private double measurementError;
    private int iterationsCount;

    public ReimannSumAnswer(double value, double measurementError, int iterationsCount) {
        this.iterationsCount = iterationsCount;
        this.measurementError = measurementError;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Value of the integral is " + value + "\n" +
                "count of steps: " + iterationsCount + "\n" +
                (Double.isNaN(measurementError) ? "" : "measurement error: " + measurementError) + "\n";
    }
}
