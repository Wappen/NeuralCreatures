package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.Colorable;
import me.wappen.neuralcreatures.Entity;
import processing.core.PVector;

public record VisionSense(Creature creature) implements Sense {
    private static final float eyeDist = 20; // dist of the so called 'eyes' from the 'real' 'eye'
    private static final int res = 5; // num of 'eyes'
    private static final float fov = 1f; // radians

    @Override
    public double[] get() {
        double[] arr = new double[res * 3]; // 3 because rgb color

        PVector eyePos = creature.getEyePos();
        PVector dir = creature.getTransform().getDir().copy().mult(eyeDist);

        dir.rotate((-res * (fov / res)) / 2f);

        for (int i = 0; i < res; i++) {
            dir.rotate(fov / res);
            Entity hit = creature.getWorld().getEntityAtCoord(eyePos.copy().add(dir));

            if (hit instanceof Colorable entity) {
                arr[i * 3] = entity.getColor().x;
                arr[i * 3 + 1] = entity.getColor().y;
                arr[i * 3 + 2] = entity.getColor().z;
            }
            else {
                arr[i * 3] = 0;
                arr[i * 3 + 1] = 0;
                arr[i * 3 + 2] = 0;
            }

        }

        return arr;
    }

    @Override
    public int getResolution() {
        return res * 3;
    }
}
