import exception.*;

import java.util.Scanner;

public class Main {
    private static final String START_MESSAGE =
                    "Hello!\n" +
                    "This program calculates the integrals by the trapezoid method.";
    private static final String HELP_MESSAGE =
                    "Supported Commands:\n" +
                    "Use 'choose <number of function>' to select function\n" +
                    "Use 'exit' to quit\n" +
                    "Use 'help' to see this text\n" +
                    "Supported Functions:\n" +
                    "1)y = x\n" +
                    "2)y = sqrt(x)\n" +
                    "3)y = 0.1*x^4 + 0.2*x^2 - 7\n" +
                    "4)y = 0.01 / x\n" +
                    "5)y = sin(x) / x";
    private static final String EXIT_MESSAGE =
            "Good bye!!!";
    private static final String ASK_FOR_BOUNDS =
            "Enter the integration limits '<start_bound> <end_bound>' ('start_bound' can be more than 'end_bound'): ";
    private static final String ASK_FOR_ACCURACY =
            "Enter the accuracy. It should be more than 0.000001: ";

    private static final double EPS = 1e-6;

    public static void main(String[] args) {
        Function linearFunc = new Function() {
            @Override
            public double get(double argument) {
                return argument;
            }

            @Override
            public Interval[] getNotAllowedScope() {
                return new Interval[0];
            }

            @Override
            public Function get2Derivative() {
                return ConstantFunc.ZERO_FUNCTION;
            }

            @Override
            public Function get4Derivative() {
                return ConstantFunc.ZERO_FUNCTION;
            }

            @Override
            public String toString() {
                return "y = x";
            }
        };

        Function pow2DerivativeFunc = new DerivativeFunc() {
            @Override
            public double get(double argument) {
                return 1.2 * Math.pow(argument, 2) + 0.4;
            }

            @Override
            public Interval[] getNotAllowedScope() {
                return new Interval[0];
            }
        };
        Function pow4Func = new Function() {
            @Override
            public double get(double argument) {
                return 0.1 * Math.pow(argument, 4) + 0.2 * Math.pow(argument, 2) - 7;
            }

            @Override
            public Interval[] getNotAllowedScope() {
                return new Interval[0];
            }

            @Override
            public Function get2Derivative() {
                return pow2DerivativeFunc;
            }

            @Override
            public Function get4Derivative() {
                return (ConstantFunc) argument -> 2.4d;
            }

            @Override
            public String toString() {
                return "y = 0.1*x^4 + 0.2*x^2 - 7";
            }
        };

        Function sqrt = new Function() {
            @Override
            public double get(double argument) {
                return Math.sqrt(argument);
            }

            @Override
            public Interval[] getNotAllowedScope() {
                Interval[] res = new Interval[1];

                try {
                    res[0] = new Interval(-1e30d, false, 0d, false);
                } catch (InvalidIntervalException e) {
                    System.err.println(e);
                    System.err.println("Unavailable code!!!");
                }

                return res;
            }

            @Override
            public Function get2Derivative() {
                return new DerivativeFunc() {
                    @Override
                    public double get(double argument) {
                        return -1d / 4d / Math.pow(argument, 3d / 2d);
                    }

                    @Override
                    public Interval[] getNotAllowedScope() {
                        Interval[] res = new Interval[1];

                        try {
                            res[0] = new Interval(0d);
                        } catch (InvalidIntervalException e) {
                            System.err.println(e);
                            System.err.println("Unavailable code!!!");
                        }

                        return res;
                    }
                };
            }

            @Override
            public Function get4Derivative() {
                return new DerivativeFunc() {
                    @Override
                    public double get(double argument) {
                        return -15d / 16d / Math.pow(argument, 7d / 2d);
                    }

                    @Override
                    public Interval[] getNotAllowedScope() {
                        Interval[] res = new Interval[1];

                        try {
                            res[0] = new Interval(0d);
                        } catch (InvalidIntervalException e) {
                            System.err.println(e);
                            System.err.println("Unavailable code!!!");
                        }

                        return res;
                    }
                };
            }

            @Override
            public String toString() {
                return "y = sqrt(x)";
            }
        };

        Function hyperbola = new Function() {
            @Override
            public double get(double argument) {
                return 0.01d / argument;
            }

            @Override
            public Interval[] getNotAllowedScope() {
                Interval[] res = new Interval[1];

                try {
                    res[0] = new Interval(0d);
                } catch (InvalidIntervalException e) {
                    System.err.println(e);
                    System.err.println("Unavailable code!!!");
                }

                return res;
            }

            @Override
            public Function get2Derivative() {
                return new DerivativeFunc() {
                    @Override
                    public double get(double argument) {
                        return 0.02d / Math.pow(argument, 3);
                    }

                    @Override
                    public Interval[] getNotAllowedScope() {
                        Interval[] res = new Interval[1];

                        try {
                            res[0] = new Interval(0d);
                        } catch (InvalidIntervalException e) {
                            System.err.println(e);
                            System.err.println("Unavailable code!!!");
                        }

                        return res;
                    }
                };
            }

            @Override
            public Function get4Derivative() {
                return new DerivativeFunc() {
                    @Override
                    public double get(double argument) {
                        return 0.024d / Math.pow(argument, 5);
                    }

                    @Override
                    public Interval[] getNotAllowedScope() {
                        Interval[] res = new Interval[1];

                        try {
                            res[0] = new Interval(0d);
                        } catch (InvalidIntervalException e) {
                            System.err.println(e);
                            System.err.println("Unavailable code!!!");
                        }

                        return res;
                    }
                };
            }

            @Override
            public String toString() {
                return "y = 0.01/x";
            }
        };

        Function sinusDivX = new Function() {
            @Override
            public double get(double argument) {
                return Math.sin(argument) / argument;
            }

            @Override
            public Interval[] getNotAllowedScope() {
                Interval[] res = new Interval[1];

                try {
                    res[0] = new Interval(0d);
                } catch (InvalidIntervalException e) {
                    System.err.println(e);
                    System.err.println("Unavailable code!!!");
                }

                return res;
            }

            @Override
            public Function get2Derivative() throws NotImplementedMethodException {
                throw new NotImplementedMethodException();
            }

            @Override
            public Function get4Derivative() throws NotImplementedMethodException {
                throw new NotImplementedMethodException();
            }

            @Override
            public String toString() {
                return "y = sin(x)/x";
            }
        };

        ReimannSum reimannSum = new ReimannSum();

        Scanner in = new Scanner(System.in);
        boolean flag = true;

        System.out.println(START_MESSAGE);

        while (flag) {
            System.out.println(HELP_MESSAGE);
            System.out.flush();

            String input = in.nextLine();
            String cmd = input.split(" ")[0];

            switch (cmd) {
                case "c":
                case "choose":
                    Function curFunction;

                    if (input.split(" ").length != 2) {
                        printBadCommand();
                        break;
                    }

                    try {
                        int number = Integer.parseInt(input.split(" ")[1]);

                        switch (number) {
                            case 1:
                                curFunction = linearFunc;
                                break;
                            case 2:
                                curFunction = sqrt;
                                break;
                            case 3:
                                curFunction = pow4Func;
                                break;
                            case 4:
                                curFunction = hyperbola;
                                break;
                            case 5:
                                curFunction = sinusDivX;
                                break;
                            default:
                                throw new Exception("Bad command");
                        }
                    } catch (Exception e) {
                        printBadCommand();
                        break;
                    }

                    System.out.println("You choose function " + curFunction);

                    Bounds bounds = null;

                    boolean isCorrectInput = false;
                    while (!isCorrectInput) {
                        System.out.println(ASK_FOR_BOUNDS);
                        System.out.flush();

                        String[] stringBounds = in.nextLine().split(" ");

                        if (stringBounds.length != 2) {
                            printIncorrectInput();
                            continue;
                        }

                        try {
                            double leftBound = Double.parseDouble(stringBounds[0]);
                            double rightBound = Double.parseDouble(stringBounds[1]);

                            if (curFunction == hyperbola && leftBound * rightBound < 0) {
                                if (Math.abs(leftBound) < Math.abs(rightBound)) {
                                    leftBound = -leftBound;
                                } else {
                                    rightBound = -rightBound;
                                }
                            }

                            bounds = new Bounds(leftBound, rightBound);
                        } catch (Exception e) {
                            printIncorrectInput();
                            continue;
                        }

                        isCorrectInput = true;
                    }

                    double accuracy = 0d;

                    isCorrectInput = false;
                    while (!isCorrectInput) {
                        System.out.println(ASK_FOR_ACCURACY);
                        System.out.flush();

                        String[] stringAccuracy = in.nextLine().split(" ");

                        if (stringAccuracy.length != 1) {
                            printIncorrectInput();
                            continue;
                        }

                        try {
                            accuracy = Double.parseDouble(stringAccuracy[0]);
                        } catch (Exception e) {
                            printIncorrectInput();
                            continue;
                        }

                        if (accuracy < EPS) {
                            printIncorrectInput();
                            continue;
                        }

                        isCorrectInput = true;
                    }

                    try {
                        System.out.println(reimannSum.getReimannSum(curFunction, bounds, accuracy));
                    } catch (NotImplementedMethodException e) {
                        System.err.println("This method does not implement yet!");
                    } catch (NotSolvableIntegralException e) {
                        System.err.println("Can't count this integral at this bounds!");
                    } catch (NotAllowedScopeException e) {
                        System.err.println("Can't get integral in case of not allowed scope!");
                    } catch (UnknownReimannSumRuleException e) {
                        System.err.println("Solution by this Rule doesn't implement yet!");
                    }
                    System.err.flush();
                    break;
                case "e":
                case "exit":
                    System.out.println(EXIT_MESSAGE);
                    System.out.flush();
                    flag = false;
                    break;
                case "h":
                case "help":
                    System.out.println(HELP_MESSAGE);
                    System.out.flush();
                    break;
                default:
                    printBadCommand();
                    break;
            }
        }

        in.close();
    }

    private static void printBadCommand() {
        System.err.println("Bad command!!!");
        System.err.println("Try again!");
        System.err.flush();
        System.out.println("Use 'help' to get list of commands.");
        System.out.flush();
    }

    private static void printIncorrectInput() {
        System.err.println("Incorrect input!!!");
        System.err.println("Try again!");
        System.err.flush();
    }
}
