package me.wappen.neuralcreature.entities.creature;

import me.wappen.neuralcreature.neural.io.OutputConsumer;
import processing.core.PVector;

public record MoveMuscle(Creature creature) implements OutputConsumer {

    @Override
    public void accept(double[] output) {
        float x = Math.min(Math.max((float) output[0], -1), 1);
        float y = Math.min(Math.max((float) output[1], -1), 1);

        creature.move(new PVector(x, y).normalize());
    }

    @Override
    public int getLength() {
        return 2;
    }
}
