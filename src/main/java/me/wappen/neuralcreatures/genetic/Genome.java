package me.wappen.neuralcreatures.genetic;

import java.util.List;

public interface Genome<T> {
    void addGene(Gene<T> gene);

    List<Gene<T>> getGenes();

    T createPrototype();
}
