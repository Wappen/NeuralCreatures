package me.wappen.neuralcreature.neural;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Functions {
    private static final Random rng = new Random();

    public static Neuron randomlyConnected(List<Neuron> inputLayer, Function<Double, Double> activation, Supplier<Double> weight, Supplier<Double> bias) {
        List<Axon> axons = new ArrayList<>();
        for (Neuron neuron : inputLayer)
            axons.add(new Axon(neuron, weight.get()));

        return new Neuron(bias.get(), axons, activation);
    }

    public static Neuron randomlyConnected(List<Neuron> inputLayer, Function<Double, Double> activation) {
        return randomlyConnected(inputLayer, activation, Functions::randomDouble, Functions::randomDouble);
    }

    public static Neuron randomlyConnected(List<Neuron> inputLayer) {
        return randomlyConnected(inputLayer, Math::tanh);
    }

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
