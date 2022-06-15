package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.entities.creature.genetic.Gene;
import processing.core.PVector;

public class ColorGene implements Gene {
    @Override
    public void applyTo(CreaturePrototype prototype) {
        prototype.setColor(PVector.random3D().add(1, 1, 1).mult(0.5f).mult(255));
    }

    @Override
    public int deserialize(long[] arr, int index) {
        return 0;
    }

    @Override
    public long[] serialize() {
        return new long[0];
    }
}
