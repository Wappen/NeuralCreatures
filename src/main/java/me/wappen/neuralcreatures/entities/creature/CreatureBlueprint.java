package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.entities.creature.muscles.MuscleSystem;
import me.wappen.neuralcreatures.entities.creature.senses.SensorySystem;
import me.wappen.neuralcreatures.neural.Network;
import processing.core.PVector;

public interface CreatureBlueprint {
    float getSpeed();
    PVector getColor();
    SensorySystem getSenses();
    MuscleSystem getMuscles();
    Network getBrain();
}
