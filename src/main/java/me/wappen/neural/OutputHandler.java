package me.wappen.neural;

public interface OutputHandler {
    void handle(double[] output);
    int getLength();
}
