package me.wappen.neuralcreatures.entities.creature;

public interface Muscle {
    void accept(double[] output);
    int getResolution();
}
