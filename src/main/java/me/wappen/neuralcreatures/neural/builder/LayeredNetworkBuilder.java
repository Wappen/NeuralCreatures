package me.wappen.neuralcreatures.neural.builder;

import me.wappen.neuralcreatures.misc.Graph;
import me.wappen.neuralcreatures.neural.Axon;
import me.wappen.neuralcreatures.neural.NeuralNetwork;
import me.wappen.neuralcreatures.neural.Neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LayeredNetworkBuilder implements NetworkBuilder {
    private final Graph<Neuron, Axon> graph;
    private final List<Neuron> inputNeurons;
    private final List<Neuron> outputNeurons;
    private final List<List<Neuron>> hiddenNeurons;

    public LayeredNetworkBuilder() {
        graph = new Graph<>();
        inputNeurons = new ArrayList<>();
        outputNeurons = new ArrayList<>();
        hiddenNeurons = new ArrayList<>();
    }

    public LayeredNetworkBuilder addInputLayer(int size, Function<Double, Double> activation) {
        for (int i = 0; i < size; i++) {
            Neuron neuron = new Neuron(Math.random(), activation);
            graph.addObj(neuron);
            inputNeurons.add(neuron);
        }

        hiddenNeurons.add(inputNeurons);
        return this;
    }

    public LayeredNetworkBuilder addOutputLayer(int size, Function<Double, Double> activation) {
        for (int i = 0; i < size; i++) {
            Neuron neuron = new Neuron(Math.random(), activation);
            graph.addObj(neuron);
            outputNeurons.add(neuron);
        }

        hiddenNeurons.add(outputNeurons);
        return this;
    }

    public LayeredNetworkBuilder addHiddenLayer(int size, Function<Double, Double> activation) {
        List<Neuron> layer = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Neuron neuron = new Neuron(Math.random(), activation);
            graph.addObj(neuron);
            layer.add(neuron);
        }

        hiddenNeurons.add(layer);
        return this;
    }

    @Override
    public NeuralNetwork build() {
        Graph<Neuron, Axon> connectedGraph = new Graph<>(graph);

        List<Neuron> prevLayer = null;
        for (List<Neuron> layer : hiddenNeurons) {
            if (prevLayer == null) {
                prevLayer = layer;
                continue; // Skip first iteration
            }

            for (Neuron neuron : layer) {
                for (Neuron prevNeuron : prevLayer) {
                    connectedGraph.addEdge(prevNeuron, neuron, new Axon(Math.random()));
                }
            }

            prevLayer = layer;
        }

        return new NeuralNetwork(connectedGraph, inputNeurons, outputNeurons);
    }
}
