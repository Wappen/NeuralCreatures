package me.wappen.neuralcreature.entities.creature;

import me.wappen.neuralcreature.neural.io.InputProvider;

public class VisionSense implements InputProvider {
    final Creature creature;

    public VisionSense(Creature creature) {
        this.creature = creature;
    }

    @Override
    public double[] getInput() {
        return new double[] { creature.getTransform().getPos().x / 1200 - 0.5, creature.getTransform().getPos().y / 800 - 0.5 };
    }

    @Override
    public int getLength() {
        return 2;
    }
}
