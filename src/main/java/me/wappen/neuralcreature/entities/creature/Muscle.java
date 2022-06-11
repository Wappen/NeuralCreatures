package me.wappen.neuralcreature.entities.creature;

public interface Muscle {
    void accept(double[] output);
    int getResolution();
}
