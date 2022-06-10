package me.wappen.neuralcreature.neural.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class CompositeOutputHandler implements OutputHandler, Consumer<double[]> {
    final List<OutputHandler> outputs;

    public CompositeOutputHandler() {
        outputs = new LinkedList<>();
    }

    public CompositeOutputHandler(List<OutputHandler> outputs) {
        this.outputs = outputs;
    }

    public void addOutputHandler(OutputHandler output) {
        outputs.add(output);
    }

    @Override
    public void handle(double[] output) {
        List<Double> toHandle = new ArrayList<>(Arrays.stream(output).boxed().toList());

        for (OutputHandler o : outputs) {
            List<Double> values = toHandle.subList(0, o.getLength());
            o.handle(values.stream().mapToDouble(v -> v).toArray());
            values.clear();
        }
    }

    @Override
    public int getLength() {
        return outputs.stream().mapToInt(OutputHandler::getLength).sum();
    }

    @Override
    public void accept(double[] doubles) {
        handle(doubles);
    }

    @Override
    public Consumer<double[]> andThen(Consumer<? super double[]> after) {
        return Consumer.super.andThen(after);
    }
}
