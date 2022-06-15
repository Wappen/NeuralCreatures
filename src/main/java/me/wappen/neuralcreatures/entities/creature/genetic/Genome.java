package me.wappen.neuralcreatures.entities.creature.genetic;

import java.util.LinkedList;
import java.util.List;

public class Genome {
    final List<Gene> genes;

    public Genome() {
        this.genes = new LinkedList<>();
    }

    public void addGene(Gene gene) {
        genes.add(gene);
    }

    public CreaturePrototype createPrototype() {
        CreaturePrototype prototype = new CreaturePrototype();

        prototype.setGenome(this);

        for (Gene gene : genes)
            gene.applyTo(prototype);

        return prototype;
    }
}
