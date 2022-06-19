package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.entities.creature.muscles.MuscleSystem;
import me.wappen.neuralcreatures.entities.creature.senses.SensorySystem;
import me.wappen.neuralcreatures.neural.NeuralNetwork;
import processing.core.PVector;

public interface CreatureBlueprint {
    CreatureGenome getGenome();
    float getSpeed();
    PVector getColor();
    SensorySystem getSenses();
    MuscleSystem getMuscles();
    NeuralNetwork getBrain();
}
