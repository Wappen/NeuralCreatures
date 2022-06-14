package me.wappen.neuralcreatures.entities.creature.senses;

import me.wappen.neuralcreatures.Colorable;
import me.wappen.neuralcreatures.Main;
import me.wappen.neuralcreatures.entities.Entity;
import me.wappen.neuralcreatures.entities.creature.Creature;
import processing.core.PVector;

public class VisionSense implements Sense {
    private final float eyeDist; // dist of the so called 'eyes' from the 'real' 'eye'
    private final int res; // num of 'eyes'
    private final float fov; // radians

    public VisionSense(float eyeDist, int res, float fov) {
        this.eyeDist = eyeDist;
        this.res = res;
        this.fov = fov;
    }

    @Override
    public double[] get(Creature creature) {
        double[] arr = new double[getResolution()];

        PVector eyePos = creature.getEyePos();
        PVector dir = creature.getTransform().getDir().copy().mult(eyeDist);

        dir.rotate(-fov / 2f);
        dir.rotate((fov / res) / 2f);

        for (int i = 0; i < res; i++) {
            Entity hit = creature.getWorld().getEntityAtCoord(eyePos.copy().add(dir));

            PVector p = eyePos.copy().add(dir);
            Main.getInstance().noStroke();
            Main.getInstance().fill(255);
            Main.getInstance().ellipse(p.x, p.y, 1, 1);

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
            dir.rotate(fov / res);
        }

        return arr;
    }

    @Override
    public int getResolution() {
        return res * 3; // 3 because rgb color
    }

    @Override
    public Sense copy() {
        try {
            return (Sense) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
