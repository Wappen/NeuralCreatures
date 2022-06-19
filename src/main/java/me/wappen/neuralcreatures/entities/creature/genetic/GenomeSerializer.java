package me.wappen.neuralcreatures.entities.creature.genetic;

public interface GenomeSerializer {
    Genome deserialize(final long[] arr);
    long[] serialize(final Genome genome);
}
