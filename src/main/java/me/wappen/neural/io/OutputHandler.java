package me.wappen.neural.io;

public interface OutputHandler {
    void handle(double[] output);
    int getLength();
}
