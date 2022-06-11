package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.Entity;
import me.wappen.neuralcreatures.Main;
import processing.core.PVector;

public record VisionSense(Creature creature) implements Sense {
    private static final float eyeDist = 20;
    private static final int res = 5;
    private static final float theta = 0.2f;

    @Override
    public double[] get() {
        double[] arr = new double[res];

        PVector eyePos = creature.getEyePos();
        PVector dir = creature.getTransform().getDir().copy().mult(eyeDist);

        dir.rotate((-res * theta) / 2f);

        for (int i = 0; i < res; i++) {
            Entity hit = creature.getWorld().getEntityAtCoord(eyePos.copy().add(dir));
            arr[i] = hit == null ? -1 : 1;
            dir.rotate(theta);
        }


        return arr;
    }

    @Override
    public int getResolution() {
        return res;
    }
}
