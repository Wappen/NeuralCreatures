package me.wappen.neuralcreature.neural;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Network implements Processable {
    final List<Neuron> inputs;
    final List<Neuron> outputs;

    public Network() {
        inputs = new LinkedList<>();
        outputs = new LinkedList<>();
    }

    public Network(List<Neuron> inputs, List<Neuron> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public void process(Supplier<double[]> inputSupplier, Consumer<double[]> outputConsumer) {
        outputConsumer.accept(process(inputSupplier.get()));
    }

    @Override
    public double[] process(double[] inputValues) {
        if (inputValues.length != inputs.size())
            throw new IllegalArgumentException("inputValues' length does not match input layer's size");

        Map<Neuron, Double> traversed = new HashMap<>();
        {
            int i = 0;
            for (Neuron input : inputs)
                traversed.put(input, input.bias() + input.activation().apply(inputValues[i++]));
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
                double val = getValue(cachedValues, inputAxon.previous()) * inputAxon.weight();
                cachedValues.put(inputAxon.previous(), val);
                sum += val;
            }
        }

        return neuron.activation().apply(sum);
    }
}
