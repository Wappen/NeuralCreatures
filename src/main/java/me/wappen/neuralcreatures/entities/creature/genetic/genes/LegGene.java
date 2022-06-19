package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.entities.creature.muscles.MoveMuscle;
import me.wappen.neuralcreatures.genetic.Gene;

public class LegGene implements Gene<CreaturePrototype> {
    @Override
    public void applyTo(CreaturePrototype prototype) {
        prototype.getMuscles().addMuscle(new MoveMuscle());
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
