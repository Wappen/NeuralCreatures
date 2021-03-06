package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.genetic.Gene;

public class SpeedGene implements Gene<CreaturePrototype> {
    float speed = 1f;

    @Override
    public void applyTo(CreaturePrototype prototype) {
        prototype.setSpeed(speed);
    }

    @Override
    public int deserialize(long[] arr, int index) {
        speed = (float)Double.longBitsToDouble(arr[index]);
        return 1;
    }

    @Override
    public long[] serialize() {
        return new long[] { Double.doubleToLongBits(speed) };
    }
}
