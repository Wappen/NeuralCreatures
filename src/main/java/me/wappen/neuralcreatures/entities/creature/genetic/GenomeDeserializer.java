package me.wappen.neuralcreatures.entities.creature.genetic;

public interface GenomeDeserializer {
    Genome deserialize(final long[] arr);
}