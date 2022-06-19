package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.genetic.Gene;
import me.wappen.neuralcreatures.genetic.Genome;

import java.util.LinkedList;
import java.util.List;

public class CreatureGenome implements Genome<CreaturePrototype> {
    private final List<Gene<CreaturePrototype>> genes;

    public CreatureGenome() {
        this.genes = new LinkedList<>();
    }

    @Override
    public void addGene(Gene<CreaturePrototype> gene) {
        genes.add(gene);
    }

    @Override
    public List<Gene<CreaturePrototype>> getGenes() {
        return genes;
    }

    @Override
    public CreaturePrototype createPrototype() {
        CreaturePrototype prototype = new CreaturePrototype();

        prototype.setGenome(this);

        for (Gene<CreaturePrototype> gene : genes)
            gene.applyTo(prototype);

        return prototype;
    }
}
