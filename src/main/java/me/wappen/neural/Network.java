package me.wappen.neural;

import me.wappen.neural.io.InputProvider;
import me.wappen.neural.io.OutputHandler;

import java.util.*;

public class Network implements Processable {
    final List<Neuron> inputs;
    final List<Neuron> outputs;

    public Network() {
        inputs = new LinkedList<>();
        outputs = new LinkedList<>();
    }

    public void process(InputProvider provider, OutputHandler handler) {
        handler.handle(process(provider.getInput()));
    }

    @Override
    public double[] process(double[] inputValues) {
        if (inputValues.length != inputs.size())
            throw new IllegalArgumentException("inputValues' length does not match input layer's size");

        Map<Neuron, Double> traversed = new HashMap<>();
        {
            int i = 0;
            for (Neuron input : inputs)
                traversed.put(input, inputValues[i++]);
        }

        double[] outputValues = new double[outputs.size()];
        {
            int i = 0;
            for (Neuron output : outputs)
                outputValues[i++] = getValue(traversed, output);
        }
        return outputValues;
    }

    private double getValue(Map<Neuron, Double> cachedValues, Neuron neuron) {
        double sum = neuron.bias();

        for (Axon inputAxon : neuron.inputAxons()) {
            if (cachedValues.containsKey(inputAxon.previous())) {
                sum += cachedValues.get(inputAxon.previous());
            }
            else {
                double val = getValue(cachedValues, inputAxon.previous());
                cachedValues.put(inputAxon.previous(), val * inputAxon.weight());
                sum += val;
            }
        }

        return neuron.activation().apply(sum);
    }
}
