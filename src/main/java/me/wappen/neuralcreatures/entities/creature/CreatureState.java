package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.entities.creature.muscles.Muscles;
import me.wappen.neuralcreatures.entities.creature.senses.Senses;
import me.wappen.neuralcreatures.neural.Network;
import processing.core.PVector;

public interface CreatureState {
    float getSpeed();
    PVector getColor();
    Senses getSenses();
    Muscles getMuscles();
    Network getBrain();
}
