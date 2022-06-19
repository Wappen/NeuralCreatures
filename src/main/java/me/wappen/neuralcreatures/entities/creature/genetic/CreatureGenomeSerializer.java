package me.wappen.neuralcreatures.entities.creature.genetic;

import java.util.*;
import java.util.function.Supplier;

public class CreatureGenomeSerializer implements GenomeSerializer {
    Map<Long, Supplier<? extends Gene>> geneDeserializer;
    Map<Class<? extends Gene>, Long> geneIds;

    public CreatureGenomeSerializer() {
        geneDeserializer = new HashMap<>();
        geneIds = new HashMap<>();
    }

    public <T extends Gene> void registerGene(Class<T> geneClass, Supplier<T> supplier) {
        geneDeserializer.put((long) geneIds.size(), supplier);
        geneIds.put(geneClass, (long) geneIds.size());
    }

    @Override
    public Genome deserialize(final long[] arr) {
        Genome genome = new Genome();

        for (int i = 0; i < arr.length; i++) {
            long id = arr[i];
            Gene gene = geneDeserializer.get(id).get();
            i += gene.deserialize(arr, i + 1); // Skip everything that was used by the gene
            genome.addGene(gene);
        }

        return genome;
    }

    @Override
    public long[] serialize(Genome genome) {
        List<long[]> list = new ArrayList<>();
        for (Gene gene : genome.genes) {
            list.add(new long[] { geneIds.get(gene.getClass()) }); // Add identifier
            list.add(gene.serialize());
        }
        return list.stream().flatMapToLong(Arrays::stream).toArray();
    }
}
