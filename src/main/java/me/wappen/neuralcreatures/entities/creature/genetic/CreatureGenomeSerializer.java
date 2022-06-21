package me.wappen.neuralcreatures.entities.creature.genetic;

import me.wappen.neuralcreatures.entities.creature.genetic.genes.*;
import me.wappen.neuralcreatures.genetic.Gene;
import me.wappen.neuralcreatures.genetic.Genome;
import me.wappen.neuralcreatures.genetic.GenomeSerializer;

import java.util.*;
import java.util.function.Supplier;

public class CreatureGenomeSerializer implements GenomeSerializer<CreaturePrototype> {
    Map<Long, Supplier<? extends Gene<CreaturePrototype>>> geneDeserializer;
    Map<Class<? extends Gene<CreaturePrototype>>, Long> geneIds;

    public CreatureGenomeSerializer() {
        geneDeserializer = new HashMap<>();
        geneIds = new HashMap<>();

        registerGene(EyeGene.class, EyeGene::new);
        registerGene(LegGene.class, LegGene::new);
        registerGene(SpeedGene.class, SpeedGene::new);
        registerGene(BrainGene.class, BrainGene::new);
        registerGene(ColorGene.class, ColorGene::new);
        registerGene(HealthSenseGene.class, HealthSenseGene::new);
    }

    public <T extends Gene<CreaturePrototype>> void registerGene(Class<T> geneClass, Supplier<T> supplier) {
        geneDeserializer.put((long) geneIds.size(), supplier);
        geneIds.put(geneClass, (long) geneIds.size());
    }

    @Override
    public Genome<CreaturePrototype> deserialize(final long[] arr) {
        CreatureGenome genome = new CreatureGenome();

        for (int i = 0; i < arr.length; i++) {
            long id = arr[i];
            Gene<CreaturePrototype> gene = geneDeserializer.get(id).get();
            i += gene.deserialize(arr, i + 1); // Skip everything that was used by the gene
            genome.addGene(gene);
        }

        return genome;
    }

    @Override
    public long[] serialize(Genome<CreaturePrototype> genome) {
        List<long[]> list = new ArrayList<>();
        for (Gene<CreaturePrototype> gene : genome.getGenes()) {
            list.add(new long[] { geneIds.get(gene.getClass()) }); // Add identifier
            list.add(gene.serialize());
        }
        return list.stream().flatMapToLong(Arrays::stream).toArray();
    }
}
