package me.wappen.neuralcreatures.entities.creature.genetic;

public interface Gene {
    void applyTo(CreaturePrototype prototype);
    int deserialize(final long[] arr, int index); // Returns the length of the sequence it used
    long[] serialize(); // Returns the length of the sequence it used
}
