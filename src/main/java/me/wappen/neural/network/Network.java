package me.wappen.neural.network;

import me.wappen.neural.InputProvider;
import me.wappen.neural.OutputHandler;
import me.wappen.neural.Processable;

import java.util.List;

public class Network implements Processable {
    private final Layer[] layers;

    public Network(List<Layer> layers) {
        this.layers = new Layer[layers.size()];
        for (int i = 0; i < layers.size(); i++)
            this.layers[i] = layers.get(i);
    }

    public void process(InputProvider input, OutputHandler output) {
        output.handle(process(input.getInput()));
    }

    @Override
    public double[] process(double[] inputs) {
        if (inputs.length != layers[0].size())
            throw new IllegalArgumentException("inputs' length does not match first layers size");

        double[] outputs = inputs;
        for (Layer layer : layers) {
            outputs = layer.process(outputs);
        }
        return outputs;
    }
}
