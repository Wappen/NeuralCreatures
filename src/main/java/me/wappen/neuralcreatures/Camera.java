package me.wappen.neuralcreatures;

import processing.core.PVector;

public class Camera implements Transformable, Tickable {

    private final Transform transform;
    private final Transform target;

    private final float stiffness;

    public Camera(Transform transform) {
        this.transform = transform;
        this.target = new Transform(transform.getPos().copy(), transform.getSize());
        this.stiffness = .3f;
    }

    public void zoom(float val) {
        if (val > 0)
            target.setSize(target.getSize() * (1 + val));
        else
            target.setSize(target.getSize() / (1 - val));
    }

    public void move(PVector move) {
        target.translate(move);
    }

    @Override
    public void tick() {
        transform.lerp(target, stiffness);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }
}
