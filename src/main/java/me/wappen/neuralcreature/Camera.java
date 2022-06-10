package me.wappen.neuralcreature;

import processing.core.PVector;

public class Camera implements Transformable, Tickable {

    private final Transform transform;
    private final Transform target;

    private final float stiffness;

    public Camera(Transform transform) {
        this.transform = transform;
        this.target = new Transform(transform.getPos().copy(), transform.getSize().copy());
        this.stiffness = .3f;
    }

    public void zoom(float val) {
        if (val > 0)
            target.getSize().mult(1 + val);
        else
            target.getSize().div(1 - val);
    }

    public void move(PVector move) {
        target.translate(move);
    }

    @Override
    public void tick() {
        transform.getPos().lerp(target.getPos(), stiffness);
        transform.getSize().lerp(target.getSize(), stiffness);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }
}
