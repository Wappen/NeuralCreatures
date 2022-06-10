package me.wappen.neural.network;

import me.wappen.neural.Processable;

import java.util.ArrayList;
import java.util.List;

public class Layer implements Processable {
    final List<Neuron> neurons;

    public Layer() {
        this.neurons = new ArrayList<>();
    }

    @Override
    public double[] process(double[] inputs) {
        if (inputs.length != neurons.size())
            throw new IllegalArgumentException("inputs' length does not match first layers size");

        double[] outputs = new double[inputs.length];

        for (int i = 0; i < inputs.length; i++) {
            Neuron neuron = neurons.get(i);
            outputs[i] = neuron.process(inputs);
        }

        return outputs;
    }

    public int size() {
        return neurons.size();
    }
}
