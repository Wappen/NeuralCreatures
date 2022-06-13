package me.wappen.neuralcreatures.entities.creature.senses;

import me.wappen.neuralcreatures.entities.creature.Creature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Senses implements Sense {

    final List<Sense> inputs;

    public Senses() {
        this.inputs = new LinkedList<>();
    }

    public Senses(List<Sense> inputs) {
        this.inputs = inputs;
    }

    public void addSense(Sense input) {
        inputs.add(input);
    }

    @Override
    public double[] get(Creature creature) {
        List<double[]> values = new ArrayList<>();

        for (Sense input : inputs)
            values.add(input.get(creature));

        return values.stream().flatMapToDouble(Arrays::stream).toArray(); // Return values as single double[]
    }

    @Override
    public int getResolution() {
        return inputs.stream().mapToInt(Sense::getResolution).sum();
    }
}
