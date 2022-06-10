package me.wappen.neuralcreature;

import processing.core.PVector;

public class Transform {
    private PVector pos;
    private PVector size;

    public Transform(PVector pos, PVector size) {
        this.pos = pos;
        this.size = size;
    }

    public PVector getPos() {
        return pos;
    }

    public void setPos(PVector pos) {
        this.pos = pos;
    }

    public PVector getSize() {
        return size;
    }

    public void setSize(PVector size) {
        this.size = size;
    }

    public void translate(PVector translation) {
        pos.add(translation);
    }
}
