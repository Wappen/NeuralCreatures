package me.wappen.neuralcreatures.entities.creature.genes;

public interface Gene {
    void applyTo(Genome genome);
    void deserialize(double[] sequence);
    double[] serialize();
}
