package me.wappen.neuralcreatures.entities.creature.genetic;

import java.util.LinkedList;
import java.util.List;

public class Genome {
    List<Gene> genes;

    public Genome() {
        this.genes = new LinkedList<>();
    }

    public void addGene(Gene gene) {
        genes.add(gene);
    }

    public CreaturePrototype createPrototype() {
        CreaturePrototype prototype = new CreaturePrototype();

        for (Gene gene : genes) {
            gene.applyTo(prototype);
        }

        return prototype;
    }
}
