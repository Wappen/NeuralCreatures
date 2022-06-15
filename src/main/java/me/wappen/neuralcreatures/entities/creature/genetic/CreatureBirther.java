package me.wappen.neuralcreatures.entities.creature.genetic;

import me.wappen.neuralcreatures.entities.creature.Creature;
import me.wappen.neuralcreatures.entities.creature.CreatureBuilder;

public class CreatureBirther implements CreatureBuilder {
    private Genome mother;
    private Genome father;

    public CreatureBirther(Genome mother, Genome father) {
        this.mother = mother;
        this.father = father;
    }

    @Override
    public Creature build() {
        // TODO: Add genome mixing
        return new Creature(mother.createPrototype());
    }
}
