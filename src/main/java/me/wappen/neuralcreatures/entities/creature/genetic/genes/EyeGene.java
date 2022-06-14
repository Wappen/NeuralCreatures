package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.Gene;
import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.entities.creature.senses.VisionSense;

public class EyeGene implements Gene {

    @Override
    public void applyTo(CreaturePrototype prototype) {
        prototype.getSenses().addSense(new VisionSense(20, 3, 1));
    }

    @Override
    public int deserialize(final long[] arr, int index) {
        return 0;
    }
}
