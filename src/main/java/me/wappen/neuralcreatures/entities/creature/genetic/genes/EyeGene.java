package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.entities.creature.genetic.Gene;
import me.wappen.neuralcreatures.entities.creature.senses.VisionSense;

public class EyeGene implements Gene {

    @Override
    public void applyTo(CreaturePrototype prototype) {
        prototype.getSenses().addSense(new VisionSense(20, 2, 1));
        prototype.getSenses().addSense(new VisionSense(40, 3, 1));
        prototype.getSenses().addSense(new VisionSense(60, 5, 1));
    }

    @Override
    public int deserialize(final long[] arr, int index) {
        return 0;
    }

    @Override
    public long[] serialize() {
        return new long[0];
    }
}
