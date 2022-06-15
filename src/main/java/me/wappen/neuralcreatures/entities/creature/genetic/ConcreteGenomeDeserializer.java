package me.wappen.neuralcreatures.entities.creature.genetic;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public class ConcreteGenomeDeserializer implements GenomeDeserializer {
    Map<Long, Supplier<Gene>> geneDeserializer;

    public void addDeserializer(Long geneKey, Supplier<Gene> deserializer) {
        geneDeserializer.put(geneKey, deserializer);
    }

    @Override
    public Genome deserialize(final long[] arr) {
        Genome genome = new Genome();

        for (int i = 0; i < arr.length; i++) {
            long key = arr[i];
            Gene gene = geneDeserializer.get(key).get();
            i += gene.deserialize(arr, i + 1); // Skip everything that was used by the gene
            genome.addGene(gene);
        }

        return genome;
    }

    @Override
    public long[] serialize(Genome genome) {
        return new long[0];
    }
}
