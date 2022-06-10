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

public class Creature extends Entity implements Transformable {
    private final Transform transform;
    private final float speed;

    private final CompositeInputProvider senses;
    private final CompositeOutputHandler muscles;
    private final Network brain;

    public Creature(PVector pos) {
        this.transform = new Transform(pos, new PVector(10, 10));
        this.speed = 1;

        senses = new CompositeInputProvider();
        senses.addInputProvider(new VisionSense(this));

        muscles = new CompositeOutputHandler();
        muscles.addOutputHandler(new MoveMuscle(this));

        LayeredNetworkBuilder nb = new LayeredNetworkBuilder();
        nb.addLayer(senses.getLength(), Math::tanh);
        /*nb.addLayer(12, Functions::reLU);
        nb.addLayer(6, Functions::reLU);
        nb.addLayer(12, Functions::reLU);*/
        nb.addLayer(4, Functions::reLU);
        nb.addLayer(muscles.getLength(), Math::tanh);
        this.brain = nb.build();
    }

    public void move(PVector dir) {
        transform.translate(dir.mult(speed));
    }

    @Override
    public void tick() {
        brain.process(senses, muscles);
    }

    @Override
    public void draw(PApplet applet) {
        applet.ellipse(transform.getPos().x, transform.getPos().y, transform.getSize().x, transform.getSize().y);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }
}
