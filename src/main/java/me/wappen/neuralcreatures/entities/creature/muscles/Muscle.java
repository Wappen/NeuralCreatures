package me.wappen.neuralcreatures.entities.creature.muscles;

public interface Muscle {
    void accept(double[] output);
    int getResolution();
}
