package me.wappen.neuralcreatures.neural;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public final class NNUtils {
    private static final Random rng = new Random();

    public static double reLU(double val) {
        return (val > 0) ? val : 0;
    }

    public static double map11(double val) {
        return map(val, 0, 1, -1, 1);
    }

    public static double map01(double val) {
        return map(val, -1, 1, 0, 1);
    }

    public static double map(double val, double minA, double maxA, double minB, double maxB) {
        return ((val - minA) / (maxA - minA)) * (maxB - minB) + minB;
    }

    public static double randomDouble() {
        return rng.nextDouble() * 2.0 - 1.0;
    }
}
