package me.wappen.neuralcreatures.entities.creature.muscles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Muscles implements Muscle, Consumer<double[]> {
    final List<Muscle> outputs;

    public Muscles() {
        outputs = new LinkedList<>();
    }

    public Muscles(List<Muscle> outputs) {
        this.outputs = outputs;
    }

    public void addMuscle(Muscle output) {
        outputs.add(output);
    }

    @Override
    public void accept(double[] output) {
        List<Double> toHandle = new ArrayList<>(Arrays.stream(output).boxed().toList());

        for (Muscle o : outputs) {
            List<Double> values = toHandle.subList(0, o.getResolution());
            o.accept(values.stream().mapToDouble(v -> v).toArray());
            values.clear();
        }
    }

    @Override
    public int getResolution() {
        return outputs.stream().mapToInt(Muscle::getResolution).sum();
    }

    @Override
    public Consumer<double[]> andThen(Consumer<? super double[]> after) {
        return Consumer.super.andThen(after);
    }
}
