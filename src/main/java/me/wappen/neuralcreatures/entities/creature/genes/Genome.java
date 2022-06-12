package me.wappen.neuralcreatures.entities.creature.genes;

import me.wappen.neuralcreatures.Transform;
import me.wappen.neuralcreatures.entities.creature.Creature;
import me.wappen.neuralcreatures.entities.creature.Path;
import me.wappen.neuralcreatures.entities.creature.muscles.Muscles;
import me.wappen.neuralcreatures.entities.creature.senses.Senses;
import me.wappen.neuralcreatures.neural.Network;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Genome {

    private final List<Gene> genes;

    private Transform transform;
    private float speed;
    private PVector color;
    private Senses senses;
    private Muscles muscles;
    private Network brain;
    private Path path;

    public Genome() {
        genes = new ArrayList<>();
    }

    public void addGene(Gene gene) {
        genes.add(gene);
    }

    public Creature build() {
        return new Creature(transform, speed, color, senses, muscles, brain, path);
    }
}
