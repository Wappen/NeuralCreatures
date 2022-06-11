package me.wappen.neuralcreatures.entities;

import me.wappen.neuralcreatures.Entity;
import me.wappen.neuralcreatures.Transform;
import me.wappen.neuralcreatures.Transformable;
import processing.core.PApplet;
import processing.core.PVector;

public class Plant extends Entity implements Transformable {

    private final Transform transform;

    public Plant(PVector pos) {
        this.transform = new Transform(pos, new PVector(0, 0));
    }

    @Override
    public void tick() {
        if (transform.getSize().mag() < 15)
            transform.getSize().add(0.01f, 0.01f);
    }

    @Override
    public void draw(PApplet applet) {
        applet.fill(60, 255, 60);
        applet.ellipse(transform.getPos().x, transform.getPos().y, transform.getSize().x, transform.getSize().y);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }
}
