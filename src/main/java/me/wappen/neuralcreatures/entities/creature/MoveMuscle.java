package me.wappen.neuralcreatures.entities.creature;

public record MoveMuscle(Creature creature) implements Muscle {

    @Override
    public void accept(double[] output) {
        float x = Math.min(Math.max((float) output[0], -1), 1);
        float y = Math.min(Math.max((float) output[1], 0), 1);

        creature.getTransform().rotate((float) (x / Math.PI));
        creature.move(creature.getTransform().getDir().copy().mult(y));
    }

    @Override
    public int getResolution() {
        return 2;
    }
}
