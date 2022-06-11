package me.wappen.neuralcreature.neural.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class CompositeInputSupplier implements InputSupplier, Supplier<double[]> {
    final List<InputSupplier> inputs;

    public CompositeInputSupplier() {
        inputs = new LinkedList<>();
    }

    public CompositeInputSupplier(List<InputSupplier> inputs) {
        this.inputs = inputs;
    }

    public void addInputProvider(InputSupplier input) {
        inputs.add(input);
    }

    @Override
    public double[] get() {
        List<double[]> values = new ArrayList<>();

        for (InputSupplier input : inputs)
            values.add(input.get());

        return values.stream().flatMapToDouble(Arrays::stream).toArray(); // Return values as single double[]
    }

    @Override
    public int getLength() {
        return inputs.stream().mapToInt(InputSupplier::getLength).sum();
    }
}
