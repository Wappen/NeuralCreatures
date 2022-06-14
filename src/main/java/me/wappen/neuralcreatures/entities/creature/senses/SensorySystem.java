package me.wappen.neuralcreatures.entities.creature.senses;

import me.wappen.neuralcreatures.entities.creature.Creature;
import me.wappen.neuralcreatures.entities.creature.muscles.Muscle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SensorySystem implements Sense {

    final List<Sense> senses;

    public SensorySystem() {
        this.senses = new LinkedList<>();
    }

    public SensorySystem(List<Sense> senses) {
        this.senses = senses;
    }

    public SensorySystem(SensorySystem sensorySystem) {
        this.senses = new LinkedList<>();
        for (Sense sense : sensorySystem.senses) {
            this.senses.add(sense.copy());
        }
    }

    public void addSense(Sense input) {
        senses.add(input);
    }

    @Override
    public double[] get(Creature creature) {
        List<double[]> values = new ArrayList<>();

        for (Sense sense : senses)
            values.add(sense.get(creature));

        return values.stream().flatMapToDouble(Arrays::stream).toArray(); // Return values as single double[]
    }

    @Override
    public int getResolution() {
        return senses.stream().mapToInt(Sense::getResolution).sum();
    }

    @Override
    public Sense copy() {
        return new SensorySystem(this);
    }
}
