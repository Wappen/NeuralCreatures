package me.wappen.neuralcreatures;

import processing.core.PVector;

public class Transform {
    private PVector pos;
    private PVector size;
    private PVector dir;

    public Transform() {
        this(new PVector(0, 0));
    }

    public Transform(PVector pos) {
        this(pos, new PVector(1, 1));
    }

    public Transform(PVector pos, PVector size) {
        this(pos, size, new PVector(0, 1));
    }

    public Transform(PVector pos, PVector size, PVector dir) {
        this.pos = pos;
        this.size = size;
        this.dir = dir;
    }

    public void translate(PVector translation) {
        pos.add(translation);
    }

    public void scale(float factor) {
        size.mult(factor);
    }

    public void rotate(float theta) {
        dir.rotate(theta);
    }

    public void lerp(Transform other, float val) {
        pos.lerp(other.pos, val);
        size.lerp(other.size, val);
        dir.lerp(other.dir, val);
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

    public PVector getDir() {
        return dir;
    }

    public void setDir(PVector dir) {
        this.dir = dir;
        this.dir.normalize();
    }
}
