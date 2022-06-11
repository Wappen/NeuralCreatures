package me.wappen.neuralcreature.entities.creature;

import me.wappen.neuralcreature.neural.io.InputSupplier;

public record VisionSense(Creature creature) implements InputSupplier {

    @Override
    public double[] get() {
        return new double[]{
                creature.getTransform().getPos().x / 1200,
                creature.getTransform().getPos().y / 800
        };
    }

    @Override
    public int getLength() {
        return 2;
    }
}
