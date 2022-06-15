package me.wappen.neuralcreatures.misc;

import processing.core.PVector;

import java.util.Collection;
import java.util.function.Supplier;

public interface Space<T> {
    void addObj(T obj, Supplier<PVector> pos);
    void removeObj(T obj);
    Collection<T> getClosest(PVector pos);
    void update();
}
