package me.wappen.neuralcreatures.entities.creature.senses;

import me.wappen.neuralcreatures.entities.creature.Creature;

public interface Sense {
    double[] get(Creature creature);
    int getResolution();
    Sense copy();
}
