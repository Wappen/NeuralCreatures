package me.wappen.neuralcreature.neural.io;

public interface OutputConsumer {
    void accept(double[] output);
    int getLength();
}
