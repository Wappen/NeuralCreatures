package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.*;
import me.wappen.neuralcreatures.entities.Entity;
import me.wappen.neuralcreatures.entities.creature.muscles.MoveMuscle;
import me.wappen.neuralcreatures.entities.creature.muscles.Muscles;
import me.wappen.neuralcreatures.entities.creature.senses.KeyboardSense;
import me.wappen.neuralcreatures.entities.creature.senses.Senses;
import me.wappen.neuralcreatures.entities.creature.senses.VisionSense;
import me.wappen.neuralcreatures.neural.NNUtils;
import me.wappen.neuralcreatures.neural.builder.LayeredNetworkBuilder;
import me.wappen.neuralcreatures.neural.Network;
import processing.core.PApplet;
import processing.core.PVector;

public class Creature extends Entity implements Transformable, Colorable {
    private final Transform transform;

    private final float speed;
    private final PVector color;

    private final Senses senses;
    private final Muscles muscles;
    private final Network brain;

    private final Path path;

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

        path = new Path(this);
        //color = new PVector(255, 100, 100);
        color = PVector.random3D().add(1, 1, 1).mult(0.5f).mult(255);
    }

    public void move(PVector dir) {
        transform.setDir(dir);
        transform.translate(dir.copy().mult(speed));
    }

    @Override
    public void tick() {
        // TODO: Change to energy dependent despawn
        if (Float.isNaN(transform.getDir().x) || Float.isNaN(transform.getDir().y) || transform.getDir().mag() == 0)
            getWorld().deferTask(() -> getWorld().despawn(this));

        path.tick();
        brain.process(senses, muscles);
    }

    @Override
    public void draw(PApplet applet) {
        PVector pos = transform.getPos();
        PVector size = transform.getSize();
        PVector dir = transform.getDir();

        // Draw body
        applet.fill(color.x, color.y, color.z);
        applet.ellipse(pos.x, pos.y, size.x, size.y);

        // Draw eye
        PVector eyePos = getEyePos();
        applet.fill(255);
        applet.stroke(color.x, color.y, color.z);
        applet.strokeWeight(0.666f);
        applet.ellipse(eyePos.x, eyePos.y, size.x / 2, size.y / 2);
        applet.noStroke();

        // Draw pupil
        PVector pupilPos = eyePos.add(dir.copy().rotate(smoothSquareWave(applet.frameCount / 30f + color.x)));
        applet.fill(0);
        applet.ellipse(pupilPos.x, pupilPos.y, size.x / 5, size.y / 5);

        // Draw path
        path.draw(applet);
    }

    public PVector getEyePos() {
        PVector pos = transform.getPos();
        PVector dir = transform.getDir();
        PVector size = transform.getSize();

        return pos.copy().add(dir.copy().mult(size.mag() / 4));
    }

    private float smoothSquareWave(float t) {
        return (float) (Math.sin(t) + Math.sin(t * 3) / 3);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public PVector getColor() {
        return color;
    }
}
