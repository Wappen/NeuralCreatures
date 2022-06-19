package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.entities.creature.senses.VisionSense;
import me.wappen.neuralcreatures.genetic.Gene;

public class EyeGene implements Gene<CreaturePrototype> {

    @Override
    public void applyTo(CreaturePrototype prototype) {
        prototype.getSenses().addSense(new VisionSense(20, 2, 1));
        prototype.getSenses().addSense(new VisionSense(40, 2, 1));
        prototype.getSenses().addSense(new VisionSense(60, 3, 1));
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
