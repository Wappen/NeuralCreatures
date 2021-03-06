package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.entities.creature.genetic.CreatureGenome;
import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.genetic.GenomeSerializer;

public class CreatureBirther implements CreatureBuilder {
    private final GenomeSerializer<CreaturePrototype> serializer;
    private final CreatureGenome mother;
    private final CreatureGenome father;

    public CreatureBirther(GenomeSerializer<CreaturePrototype> serializer, CreatureGenome mother, CreatureGenome father) {
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
