package me.wappen.neuralcreatures.neural;

import me.wappen.neuralcreatures.misc.Graph;

import java.util.*;

public class NeuralNetwork {
    private final Graph<Neuron, Axon> neurons;

    private final List<Neuron> sensingNeurons;
    private final List<Neuron> outputNeurons;

    public NeuralNetwork(NeuralNetwork other) {
        neurons = new Graph<>(other.neurons);
        sensingNeurons = new ArrayList<>(other.sensingNeurons);
        outputNeurons = new ArrayList<>(other.outputNeurons);
    }

    public NeuralNetwork(Graph<Neuron, Axon> neurons, List<Neuron> sensingNeurons, List<Neuron> outputNeurons) {
        this.neurons = neurons;
        this.sensingNeurons = sensingNeurons;
        this.outputNeurons = outputNeurons;
    }

    public double[] process(double[] inputs) {
        if (inputs.length != sensingNeurons.size())
            throw new IllegalArgumentException("inputValues' length does not match input layer's size");

        Map<Neuron, Double> traversed = new HashMap<>();
        {
            int i = 0;
            for (Neuron neuron : sensingNeurons)
                traversed.put(neuron, neuron.bias() + neuron.activation().apply(inputs[i++]));
        }

        double[] outputValues = new double[outputNeurons.size()];
        {
            int i = 0;
            for (Neuron output : outputNeurons)
                outputValues[i++] = getValue(traversed, output);
        }
        return outputValues;
    }

    private double getValue(Map<Neuron, Double> cachedValues, Neuron neuron) {
        double sum = neuron.bias();

        for (Map.Entry<Axon, Neuron> connections : neurons.getPrevious(neuron).entrySet()) {
            Neuron prev = connections.getValue();
            Axon axon = connections.getKey();

            if (cachedValues.containsKey(prev)) {
                sum += cachedValues.get(prev);
            }
            else {
                double val = getValue(cachedValues, prev) * axon.weight();
                cachedValues.put(prev, val);
                sum += val;
            }
        }

        return neuron.activation().apply(sum);
    }
}
