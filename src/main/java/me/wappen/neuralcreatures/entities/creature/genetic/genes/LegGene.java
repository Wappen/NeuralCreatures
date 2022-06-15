package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.entities.creature.genetic.Gene;
import me.wappen.neuralcreatures.entities.creature.muscles.MoveMuscle;

public class LegGene implements Gene {
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
