package me.wappen.neuralcreatures.genetic;

public interface Gene<T> {
    void applyTo(T prototype);
    int deserialize(final long[] arr, int index); // Returns the length of the sequence it used
    long[] serialize(); // Returns the length of the sequence it used
}
