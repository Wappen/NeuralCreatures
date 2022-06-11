package me.wappen.neuralcreature.entities.creature;

import processing.core.PVector;

public record MoveMuscle(Creature creature) implements Muscle {

    @Override
    public void accept(double[] output) {
        float x = Math.min(Math.max((float) output[0], -1), 1);
        float y = Math.min(Math.max((float) output[1], -1), 1);

        creature.move(new PVector(x, y).normalize());
    }

    @Override
    public int getResolution() {
        return 2;
    }
}
