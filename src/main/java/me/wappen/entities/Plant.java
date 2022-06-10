package me.wappen.entities;

import me.wappen.Entity;
import me.wappen.Transformable;
import processing.core.PApplet;
import processing.core.PVector;

public class Plant extends Entity implements Transformable {

    private PVector pos;
    private PVector size;

    public Plant(PVector pos) {
        this.pos = pos;
        this.size = new PVector(0, 0);
    }

    @Override
    public void tick() {
        size.add(0.01f, 0.01f);
    }

    @Override
    public void draw(PApplet applet) {
        applet.fill(60, 255, 60);
        applet.ellipse(pos.x, pos.y, size.x, size.y);
    }

    @Override
    public PVector getPos() {
        return pos;
    }

    @Override
    public PVector getSize() {
        return size;
    }
}
