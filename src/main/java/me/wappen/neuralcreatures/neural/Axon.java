package me.wappen.neuralcreatures.neural;

import java.util.Objects;
import java.util.UUID;

public final class Axon {
    private final UUID uuid;
    private final double weight;

    public Axon(double weight) {
        this.uuid = UUID.randomUUID();
        this.weight = weight;
    }

    public UUID uuid() {
        return uuid;
    }

    public double weight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Axon) obj;
        return Objects.equals(this.uuid, that.uuid) &&
                Double.doubleToLongBits(this.weight) == Double.doubleToLongBits(that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, weight);
    }

    @Override
    public String toString() {
        return "Axon[" +
                "uuid=" + uuid + ", " +
                "weight=" + weight + ']';
    }
}
