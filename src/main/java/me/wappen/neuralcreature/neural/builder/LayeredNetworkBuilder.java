package me.wappen.neuralcreature.neural.builder;

import me.wappen.neuralcreature.neural.Functions;
import me.wappen.neuralcreature.neural.Network;
import me.wappen.neuralcreature.neural.Neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LayeredNetworkBuilder implements NetworkBuilder {

    private record Layer(List<Neuron> neurons) {}

    private List<Layer> layers;

    public LayeredNetworkBuilder() {
        layers = new ArrayList<>();
    }

    public void addLayer(int size, Function<Double, Double> activation) {
        Layer prevLayer = (layers.isEmpty()) ? new Layer(new ArrayList<>()) : layers.get(layers.size() - 1);
        Layer layer = new Layer(new ArrayList<>());

        for (int i = 0; i < size; i++) {
            layer.neurons.add(Functions.randomlyConnected(prevLayer.neurons, activation));
        }

        layers.add(layer);
    }

    public void addLayer(int size) {
        addLayer(size, Math::tanh);
    }

    @Override
    public Network build() {
        Layer inputs = layers.get(0);
        Layer outputs = layers.get(layers.size() - 1);

        return new Network(inputs.neurons, outputs.neurons);
    }
}
