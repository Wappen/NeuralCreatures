package me.wappen.neural.network;

public class Axon {
    Neuron target;
    double weight;

    public Axon(Neuron target, double weight) {
        this.target = target;
        this.weight = weight;
    }
}
