package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class Path implements Tickable, Drawable {

    private final Transformable tracer;
    private final Deque<PVector> path;

    private int ticks;

    public Path(Transformable tracer) {
        this.tracer = tracer;
        this.path = new LinkedList<>();
    }

    @Override
    public void tick() {
        ticks++;

        float ticksBetweenSection = 5;
        float pathSections = 200;

        if (path.isEmpty() || ticks % ticksBetweenSection == 0)
            path.add(tracer.getTransform().getPos().copy());

        while (path.size() > pathSections)
            path.remove();
    }

    @Override
    public void draw(PApplet applet) {
        if (!path.isEmpty()) {
            applet.noFill();
            applet.strokeWeight(2);
            applet.stroke(255, 10);
            applet.beginShape();

            PVector pos = tracer.getTransform().getPos();
            PVector prev = pos;
            applet.vertex(pos.x, pos.y);

            int i = 0;
            int quality = (int)Math.ceil(1 / Main.getCamera().getTransform().getSize() * 12);

            for (Iterator<PVector> it = path.descendingIterator(); it.hasNext(); ) {
                PVector p = it.next();

                if (i % quality == 0 || i >= path.size() - 2) {
                    PVector control = prev.copy().add(p).mult(0.5f);
                    applet.quadraticVertex(prev.x, prev.y, control.x, control.y);
                    prev = p;
                }

                i++;
            }

            applet.endShape();
        }
    }
}
