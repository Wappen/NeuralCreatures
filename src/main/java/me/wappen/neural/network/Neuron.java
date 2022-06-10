package me.wappen.neural.network;

import java.util.function.Function;

public class Neuron {
    private double bias;
    private double[] weights;
    private Function<Double, Double> activation;

    public Neuron(double bias, double[] weights, Function<Double, Double> activation) {
        this.bias = bias;
        this.weights = weights;
        this.activation = activation;
    }

    public double process(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < inputs.length; i++)
            sum += bias + inputs[i] * weights[i];
        return activation.apply(sum);
    }
}
