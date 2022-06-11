package me.wappen.neuralcreatures.entities.creature;

public record VisionSense(Creature creature) implements Sense {

    @Override
    public double[] get() {
        return new double[]{
                creature.getTransform().getPos().x / 1200,
                creature.getTransform().getPos().y / 800,
                creature.getTransform().getDir().x,
                creature.getTransform().getDir().y
        };
    }

    @Override
    public int getResolution() {
        return 4;
    }
}
