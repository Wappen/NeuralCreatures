package me.wappen.neuralcreature.entities.creature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class Senses implements Sense, Supplier<double[]> {
    final List<Sense> inputs;

    public Senses() {
        inputs = new LinkedList<>();
    }

    public Senses(List<Sense> inputs) {
        this.inputs = inputs;
    }

    public void addSense(Sense input) {
        inputs.add(input);
    }

    @Override
    public double[] get() {
        List<double[]> values = new ArrayList<>();

        for (Sense input : inputs)
            values.add(input.get());

        return values.stream().flatMapToDouble(Arrays::stream).toArray(); // Return values as single double[]
    }

    @Override
    public int getResolution() {
        return inputs.stream().mapToInt(Sense::getResolution).sum();
    }
}
