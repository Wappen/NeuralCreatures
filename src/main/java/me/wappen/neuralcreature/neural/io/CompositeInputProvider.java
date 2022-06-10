package me.wappen.neuralcreature.neural.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class CompositeInputProvider implements InputProvider, Supplier<double[]> {
    final List<InputProvider> inputs;

    public CompositeInputProvider() {
        inputs = new LinkedList<>();
    }

    public CompositeInputProvider(List<InputProvider> inputs) {
        this.inputs = inputs;
    }

    public void addInputProvider(InputProvider input) {
        inputs.add(input);
    }

    @Override
    public double[] getInput() {
        List<double[]> values = new ArrayList<>();

        for (InputProvider input : inputs)
            values.add(input.getInput());

        return values.stream().flatMapToDouble(Arrays::stream).toArray(); // Return values as single double[]
    }

    @Override
    public double[] get() {
        return getInput();
    }

    @Override
    public int getLength() {
        return inputs.stream().mapToInt(InputProvider::getLength).sum();
    }
}
