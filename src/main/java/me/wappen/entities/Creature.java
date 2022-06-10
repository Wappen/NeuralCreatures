package me.wappen.entities;

import me.wappen.Entity;
import me.wappen.Transformable;
import me.wappen.neural.*;
import me.wappen.neural.network.Network;
import processing.core.PApplet;
import processing.core.PVector;

public class Creature extends Entity implements Transformable {
    private PVector pos;
    private PVector size;
    private float speed;

    private Network brain;
    private InputProvider senses;
    private OutputHandler muscles;

    public Creature(PVector pos) {
        this.pos = pos;
        this.size = new PVector(10, 10);
        this.speed = 1;

        this.brain = new Network();

        CompositeInputProvider senses = new CompositeInputProvider();
        //senses.addInputProvider(null);
        this.senses = senses;

        CompositeOutputHandler muscles = new CompositeOutputHandler();
        //muscles.addOutputHandler(null);
        this.muscles = muscles;
    }

    @Override
    public void tick() {
        brain.process(senses, muscles);

        pos = pos.add(PVector.random2D().mult(speed));
    }

    @Override
    public void draw(PApplet applet) {
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
