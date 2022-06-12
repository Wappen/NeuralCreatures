package me.wappen.neuralcreatures.entities.creature.muscles;

import me.wappen.neuralcreatures.entities.creature.Creature;

public record MoveMuscle(Creature creature) implements Muscle {

    @Override
    public void accept(double[] output) {
        float scale = 20; // Scale for output because of many very high values

        float left = (Math.min(Math.max((float) output[0] / scale, -1), 1) + 1);
        float right = (Math.min(Math.max((float) output[1] / scale, -1), 1) + 1);
        float speed = Math.min(Math.max((float) output[2] / scale, 0), 1);

        creature.getTransform().rotate((float) ((left - right) / Math.PI));
        creature.move(creature.getTransform().getDir().copy().mult(speed));
    }

    @Override
    public int getResolution() {
        return 3;
    }
}
