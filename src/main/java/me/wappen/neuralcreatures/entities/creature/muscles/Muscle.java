package me.wappen.neuralcreatures.entities.creature.muscles;

import me.wappen.neuralcreatures.entities.creature.Creature;

public interface Muscle {
    void handle(double[] output, Creature creature);

    int getResolution();

    Muscle copy();
}
