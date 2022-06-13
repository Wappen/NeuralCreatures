package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.Gene;
import me.wappen.neuralcreatures.entities.creature.genetic.Genome;
import me.wappen.neuralcreatures.entities.creature.senses.VisionSense;

public class EyeGene implements Gene {

    @Override
    public void applyTo(Genome genome) {
        genome.getSenses().addSense(new VisionSense());
    }

    @Override
    public void deserialize(double[] sequence) {

    }

    @Override
    public double[] serialize() {
        return new double[0];
    }
}
