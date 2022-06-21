package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.entities.creature.senses.VisionSense;
import me.wappen.neuralcreatures.genetic.Gene;

public class EyeGene implements Gene<CreaturePrototype> {
    private int eyeDist = 100;
    private int res = 4;
    private float fov = 1f;

    @Override
    public void applyTo(CreaturePrototype prototype) {
        prototype.getSenses().addSense(new VisionSense(eyeDist, res, fov));
    }

    @Override
    public int deserialize(final long[] arr, int index) {
        eyeDist = (int) arr[index];
        res = (int) arr[index + 1];
        fov = (float) Double.longBitsToDouble(arr[index + 2]);
        return 3;
    }

    @Override
    public long[] serialize() {
        return new long[] {
                eyeDist,
                res,
                Double.doubleToLongBits(fov)
        };
    }
}
