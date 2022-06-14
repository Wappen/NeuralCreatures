package me.wappen.neuralcreatures;

import processing.core.PVector;

public class Transform {
    private PVector pos;
    private float size;
    private PVector dir;

    public Transform() {
        this(new PVector(0, 0));
    }

    public Transform(PVector pos) {
        this(pos.copy(), 1f);
    }

    public Transform(PVector pos, float size) {
        this(pos.copy(), size, new PVector(0, 1));
    }

    public Transform(PVector pos, float size, PVector dir) {
        this.pos = pos;
        this.size = size;
        this.dir = dir;
    }

    public void translate(PVector translation) {
        pos.add(translation);
    }

    public void scale(float factor) {
        size *= factor;
    }

    public void rotate(float theta) {
        dir.rotate(theta);
    }

    public void lerp(Transform other, float val) {
        pos.lerp(other.pos, val);
        size = (1 - val) * size + val * other.size;
        dir.lerp(other.dir, val);
    }

    public PVector getPos() {
        return pos;
    }

    public void setPos(PVector pos) {
        this.pos = pos.copy();
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public PVector getDir() {
        return dir;
    }

    public void setDir(PVector dir) {
        this.dir = dir.copy().normalize();
    }
}
