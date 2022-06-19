package me.wappen.neuralcreatures.entities.creature.genetic;

import me.wappen.neuralcreatures.entities.creature.Creature;
import me.wappen.neuralcreatures.entities.creature.CreatureBuilder;

public class CreatureBirther implements CreatureBuilder {
    private final GenomeSerializer serializer;
    private final Genome mother;
    private final Genome father;

    public CreatureBirther(GenomeSerializer serializer, Genome mother, Genome father) {
        this.serializer = serializer;
        this.mother = mother;
        this.father = father;
    }

    @Override
    public Creature build() {
        // TODO: Add genome mixing
        long[] arr = serializer.serialize(mother);
        return new Creature(serializer.deserialize(arr).createPrototype());
    }
}
