package me.wappen.neuralcreature.neural.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CompositeOutputHandler implements OutputHandler {
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
}
