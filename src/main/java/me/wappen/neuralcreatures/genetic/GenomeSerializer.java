package me.wappen.neuralcreatures.genetic;

public interface GenomeSerializer<T> {
    Genome<T> deserialize(final long[] arr);
    long[] serialize(final Genome<T> genome);
}
