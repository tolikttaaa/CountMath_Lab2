public interface ConstantFunc extends Function {
    Function ZERO_FUNCTION = new Function() {
        @Override
        public double get(double argument) {
            return 0d;
        }

        @Override
        public Interval[] getNotAllowedScope() {
            return new Interval[0];
        }

        @Override
        public Function get2Derivative() {
            return ZERO_FUNCTION;
        }

        @Override
        public Function get4Derivative() {
            return ZERO_FUNCTION;
        }
    };

    @Override
    default Interval[] getNotAllowedScope() {
        return new Interval[0];
    }

    @Override
    default Function get2Derivative() {
        return ZERO_FUNCTION;
    }

    @Override
    default Function get4Derivative() {
        return ZERO_FUNCTION;
    }
}
