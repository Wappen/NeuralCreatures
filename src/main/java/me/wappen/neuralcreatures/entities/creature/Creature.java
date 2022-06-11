package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.Entity;
import me.wappen.neuralcreatures.Transform;
import me.wappen.neuralcreatures.Transformable;
import me.wappen.neuralcreatures.neural.NNUtils;
import me.wappen.neuralcreatures.neural.builder.LayeredNetworkBuilder;
import me.wappen.neuralcreatures.neural.Network;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class Creature extends Entity implements Transformable {
    private final Transform transform;

    private final float speed;

    private final Senses senses;
    private final Muscles muscles;
    private final Network brain;

    private int ticks = 0;

    private final Deque<PVector> path;

    public Creature(PVector pos) {
        this.transform = new Transform(pos, new PVector(10, 10));
        this.speed = 1;

        senses = new Senses();
        senses.addSense(new VisionSense(this));
        senses.addSense(new KeyboardSense());

        muscles = new Muscles();
        muscles.addMuscle(new MoveMuscle(this));

        LayeredNetworkBuilder nb = new LayeredNetworkBuilder(NNUtils::reLU);

        nb.addLayer(senses.getResolution(), NNUtils::map01); // input layer
        //nb.addLayer(8);
        nb.addLayer(4);
        //nb.addLayer(8);
        nb.addLayer(muscles.getResolution(), NNUtils::map11); // output layer
        this.brain = nb.build();

        path = new LinkedList<>();
    }

    public void move(PVector dir) {
        transform.setDir(dir);
        transform.translate(dir.mult(speed));
    }

    @Override
    public void tick() {
        ticks++;

        float ticksBetweenSection = 15;
        float pathSections = 200;

        if (path.isEmpty() || ticks % ticksBetweenSection == 0)
            path.add(transform.getPos().copy());

        while (path.size() > pathSections)
            path.remove();

        brain.process(senses, muscles);
    }

    @Override
    public void draw(PApplet applet) {
        PVector pos = transform.getPos();
        PVector size = transform.getSize();
        PVector dir = transform.getDir();

        // Draw body
        applet.fill(255, 100, 100);
        applet.ellipse(pos.x, pos.y, size.x, size.y);

        // Draw face
        PVector facePos = pos.copy().add(dir.copy().mult(size.mag() / 4));
        applet.ellipse(facePos.x, facePos.y, size.x / 1.5f, size.y / 1.5f);

        // Draw eye
        applet.fill(255);
        applet.ellipse(facePos.x, facePos.y, size.x / 2, size.y / 2);


        // Draw pupil
        PVector eyeDir = dir.copy().rotate(smoothSquareWave(ticks / 30f));
        applet.fill(0);
        applet.ellipse(facePos.x + eyeDir.x, facePos.y + eyeDir.y, size.x / 5, size.y / 5);

        // Draw path
        if (!path.isEmpty()) {
            applet.noFill();
            applet.strokeWeight(2);
            applet.stroke(255, 10);
            applet.beginShape();

            PVector prev = pos;
            applet.vertex(pos.x, pos.y);

            for (Iterator<PVector> it = path.descendingIterator(); it.hasNext(); ) {
                PVector p = it.next();
                PVector control = prev.copy().add(p).mult(0.5f);
                applet.quadraticVertex(prev.x, prev.y, control.x, control.y);
                prev = p;
            }

            applet.endShape();
        }
    }

    private float smoothSquareWave(float t) {
        return (float) (Math.sin(t) + Math.sin(t * 3) / 3);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }
}
