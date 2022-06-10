package me.wappen.neuralcreature.entities.creature;

import me.wappen.neuralcreature.neural.io.InputProvider;

public record VisionSense(Creature creature) implements InputProvider {

    @Override
    public double[] getInput() {
        return new double[]{
                creature.getTransform().getPos().x / 1200 - 0.5,
                creature.getTransform().getPos().y / 800 - 0.5
        };
    }

    @Override
    public int getLength() {
        return 2;
    }
}
