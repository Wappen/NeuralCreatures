package me.wappen.neuralcreatures.neural;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

public final class Neuron {
    private final UUID uuid;
    private final double bias;
    private final Function<Double, Double> activation;

    public Neuron(double bias, Function<Double, Double> activation) {
        this.uuid = UUID.randomUUID();
        this.bias = bias;
        this.activation = activation;
    }

    public UUID uuid() {
        return uuid;
    }

    public double bias() {
        return bias;
    }

    public Function<Double, Double> activation() {
        return activation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Neuron) obj;
        return Objects.equals(this.uuid, that.uuid) &&
                Double.doubleToLongBits(this.bias) == Double.doubleToLongBits(that.bias) &&
                Objects.equals(this.activation, that.activation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, bias, activation);
    }

    @Override
    public String toString() {
        return "Neuron[" +
                "uuid=" + uuid + ", " +
                "bias=" + bias + ", " +
                "activation=" + activation + ']';
    }
}
