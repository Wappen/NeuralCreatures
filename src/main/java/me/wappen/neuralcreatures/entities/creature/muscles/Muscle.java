package me.wappen.neuralcreatures.entities.creature.muscles;

import me.wappen.neuralcreatures.entities.creature.Creature;

public interface Muscle {
    void accept(double[] output, Creature creature);

    int getResolution();
}
