package me.wappen.neuralcreature.neural;

import java.util.List;
import java.util.function.Function;

public record Neuron(double bias, List<Axon> inputAxons, Function<Double, Double> activation) {
}