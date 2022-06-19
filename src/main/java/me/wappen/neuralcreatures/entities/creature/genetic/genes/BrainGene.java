package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.genetic.Gene;
import me.wappen.neuralcreatures.neural.NNUtils;
import me.wappen.neuralcreatures.neural.builder.LayeredNetworkBuilder;

public class BrainGene implements Gene<CreaturePrototype> {
    @Override
    public void applyTo(CreaturePrototype prototype) {
        prototype.setBrain(new LayeredNetworkBuilder()
                .addInputLayer(prototype.getSenses().getResolution(), NNUtils::map01)
                .addHiddenLayer(4, NNUtils::reLU)
                .addOutputLayer(prototype.getMuscles().getResolution(), NNUtils::map11)
                .build());
    }

    @Override
    public int deserialize(long[] arr, int index) {
        return 0;
    }

    @Override
    public long[] serialize() {
        return new long[0];
    }
}
