package me.wappen.neuralcreature.entities.creature;

import me.wappen.neuralcreature.neural.io.OutputHandler;
import processing.core.PVector;

public class MoveMuscle implements OutputHandler {
    final Creature creature;

    public MoveMuscle(Creature creature) {
        this.creature = creature;
    }

    @Override
    public void handle(double[] output) {
        float x = Math.min(Math.max((float)output[0], -1), 1);
        float y = Math.min(Math.max((float)output[1], -1), 1);
        
        creature.move(new PVector(x, y).normalize());
    }

    @Override
    public int getLength() {
        return 2;
    }
}
