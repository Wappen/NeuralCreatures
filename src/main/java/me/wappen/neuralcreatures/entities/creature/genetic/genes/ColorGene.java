package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.genetic.Gene;
import processing.core.PVector;

public class ColorGene implements Gene<CreaturePrototype> {
    PVector color = PVector.random3D().add(1, 1, 1).mult(0.5f).mult(255);

    @Override
    public void applyTo(CreaturePrototype prototype) {
        prototype.setColor(color);
    }

    @Override
    public int deserialize(long[] arr, int index) {
        color.x = arr[index];
        color.y = arr[index + 1];
        color.z = arr[index + 2];
        return 3;
    }

    @Override
    public long[] serialize() {
        return new long[] {(long) color.x, (long) color.y, (long) color.z};
    }
}
