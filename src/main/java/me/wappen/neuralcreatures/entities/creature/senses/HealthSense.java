package me.wappen.neuralcreatures.entities.creature.senses;

import me.wappen.neuralcreatures.entities.creature.Creature;

public class HealthSense implements Sense {
    @Override
    public double[] get(Creature creature) {
        return new double[] { creature.getEnergy(), creature.getHealth() };
    }

    @Override
    public int getResolution() {
        return 2;
    }

    @Override
    public Sense copy() {
        try {
            return (Sense) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
