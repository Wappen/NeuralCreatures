package me.wappen.neuralcreatures.space;

import processing.core.PVector;

import java.util.Collection;
import java.util.function.Supplier;

public interface Space<T> {
    void addObj(T obj, Supplier<PVector> pos);
    Collection<T> getClosest(PVector pos);
    void update();
}
