package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.entities.creature.senses.HealthSense;
import me.wappen.neuralcreatures.genetic.Gene;

public class HealthSenseGene implements Gene<CreaturePrototype> {
    @Override
    public void applyTo(CreaturePrototype prototype) {
        prototype.getSenses().addSense(new HealthSense());
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
