package me.wappen.neuralcreature.neural.io;

public interface OutputHandler {
    void handle(double[] output);
    int getLength();
}
