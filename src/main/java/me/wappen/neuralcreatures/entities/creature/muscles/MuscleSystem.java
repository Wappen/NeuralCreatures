package me.wappen.neuralcreatures.entities.creature.muscles;

import me.wappen.neuralcreatures.entities.creature.Creature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MuscleSystem implements Muscle {
    final List<Muscle> muscles;

    public MuscleSystem() {
        muscles = new LinkedList<>();
    }

    public MuscleSystem(List<Muscle> muscles) {
        this.muscles = muscles;
    }

    public MuscleSystem(MuscleSystem muscleSystem) {
        this.muscles = new LinkedList<>();
        for (Muscle muscle : muscleSystem.muscles) {
            this.muscles.add(muscle.copy());
        }
    }

    public void addMuscle(Muscle muscle) {
        muscles.add(muscle);
    }

    @Override
    public void handle(double[] output, Creature creature) {
        List<Double> toHandle = new ArrayList<>(Arrays.stream(output).boxed().toList());

        for (Muscle o : muscles) {
            List<Double> values = toHandle.subList(0, o.getResolution());
            o.handle(values.stream().mapToDouble(v -> v).toArray(), creature);
            values.clear();
        }
    }

    @Override
    public int getResolution() {
        return muscles.stream().mapToInt(Muscle::getResolution).sum();
    }

    @Override
    public Muscle copy() {
        return new MuscleSystem(this);
    }
}
