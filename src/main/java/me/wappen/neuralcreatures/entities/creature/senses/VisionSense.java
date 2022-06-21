package me.wappen.neuralcreatures.entities.creature.senses;

import me.wappen.neuralcreatures.Colorable;
import me.wappen.neuralcreatures.Transformable;
import me.wappen.neuralcreatures.World;
import me.wappen.neuralcreatures.entities.Entity;
import me.wappen.neuralcreatures.entities.creature.Creature;
import processing.core.PVector;

import java.util.List;

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
        PVector dir = creature.getTransform().getDir().mult(eyeDist);

        dir.rotate(-fov / 2f);
        dir.rotate((fov / res) / 2f);

        for (int i = 0; i < res; i++) {
            Entity hit = getHit(creature.getWorld(), creature.getTransform().getPos(), dir);

            if (hit instanceof Colorable entity) {
                arr[i * 3] = entity.getColor().x / 255;
                arr[i * 3 + 1] = entity.getColor().y / 255;
                arr[i * 3 + 2] = entity.getColor().z / 255;
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

    private Entity getHit(World world, PVector pos, PVector dir) {
        List<Entity> hits = world.getEntitiesBetween(pos, pos.copy().add(dir));

        Entity closest = null;
        float closestDist = Float.MAX_VALUE;

        for (Entity hit : hits) {
            float dist = ((Transformable) hit).getTransform().getPos().dist(pos);
            if (dist < closestDist) {
                closest = hit;
                closestDist = dist;
            }
        }

        return closest;
    }

    @Override
    public int getResolution() {
        return res * 3; // 3 because rgb color
    }

    @Override
    public Sense copy() {
        return new VisionSense(eyeDist, res, fov);
    }
}
