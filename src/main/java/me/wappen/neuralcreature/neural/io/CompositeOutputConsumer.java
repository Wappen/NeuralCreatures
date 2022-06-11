package me.wappen.neuralcreature.neural.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class CompositeOutputConsumer implements OutputConsumer, Consumer<double[]> {
    final List<OutputConsumer> outputs;

    public CompositeOutputConsumer() {
        outputs = new LinkedList<>();
    }

    public CompositeOutputConsumer(List<OutputConsumer> outputs) {
        this.outputs = outputs;
    }

    public void addConsumer(OutputConsumer output) {
        outputs.add(output);
    }

    @Override
    public void accept(double[] output) {
        List<Double> toHandle = new ArrayList<>(Arrays.stream(output).boxed().toList());

        for (OutputConsumer o : outputs) {
            List<Double> values = toHandle.subList(0, o.getLength());
            o.accept(values.stream().mapToDouble(v -> v).toArray());
            values.clear();
        }
    }

    @Override
    public int getLength() {
        return outputs.stream().mapToInt(OutputConsumer::getLength).sum();
    }

    @Override
    public Consumer<double[]> andThen(Consumer<? super double[]> after) {
        return Consumer.super.andThen(after);
    }
}
