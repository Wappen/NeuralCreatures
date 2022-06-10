package me.wappen.neuralcreature.entities.creature;

import me.wappen.neuralcreature.Entity;
import me.wappen.neuralcreature.Transform;
import me.wappen.neuralcreature.Transformable;
import me.wappen.neuralcreature.neural.Functions;
import me.wappen.neuralcreature.neural.builder.LayeredNetworkBuilder;
import me.wappen.neuralcreature.neural.io.CompositeInputProvider;
import me.wappen.neuralcreature.neural.io.CompositeOutputHandler;
import me.wappen.neuralcreature.neural.Network;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class Creature extends Entity implements Transformable {
    private final Transform transform;
    private final float speed;

    private final CompositeInputProvider senses;
    private final CompositeOutputHandler muscles;
    private final Network brain;

    private final Deque<PVector> path;

    private final float pathLength = 10000;
    private final float pathSectionLength = 100;

    public Creature(PVector pos) {
        this.transform = new Transform(pos, new PVector(10, 10));
        this.speed = 1;

        senses = new CompositeInputProvider();
        senses.addInputProvider(new VisionSense(this));

        muscles = new CompositeOutputHandler();
        muscles.addOutputHandler(new MoveMuscle(this));

        LayeredNetworkBuilder nb = new LayeredNetworkBuilder(Functions::reLU);

        nb.addLayer(senses.getLength(), Functions::map01); // input layer
        nb.addLayer(12);
        nb.addLayer(6);
        nb.addLayer(12);
        nb.addLayer(muscles.getLength(), Functions::map11); // output layer
        this.brain = nb.build();

        path = new LinkedList<>();
    }

    public void move(PVector dir) {
        transform.translate(dir.mult(speed));
    }

    @Override
    public void tick() {
        if (path.isEmpty() || path.peekLast().dist(transform.getPos()) > pathSectionLength)
            path.add(transform.getPos().copy());
        brain.process(senses, muscles);
    }

    @Override
    public void draw(PApplet applet) {
        applet.ellipse(transform.getPos().x, transform.getPos().y, transform.getSize().x, transform.getSize().y);

        // Draw path
        if (!path.isEmpty()) {
            applet.noFill();
            applet.strokeWeight(2);
            applet.stroke(255, 10);
            applet.beginShape();

            PVector prev = transform.getPos();
            PVector control = prev;
            applet.vertex(prev.x, prev.y);

            for (Iterator<PVector> it = path.descendingIterator(); it.hasNext(); ) {
                PVector p = it.next();
                control = prev.copy().add(p).mult(0.5f);
                applet.quadraticVertex(prev.x, prev.y, control.x, control.y);
                prev = p;
            }

            applet.quadraticVertex(control.x, control.y, prev.x, prev.y);

            applet.endShape();

            while (path.size() > pathLength / pathSectionLength + 1)
                path.remove();
        }
    }

    @Override
    public Transform getTransform() {
        return transform;
    }
}
