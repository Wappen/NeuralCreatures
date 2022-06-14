package me.wappen.neuralcreatures.entities;

import me.wappen.neuralcreatures.Colorable;
import me.wappen.neuralcreatures.Main;
import me.wappen.neuralcreatures.Transform;
import me.wappen.neuralcreatures.Transformable;
import me.wappen.neuralcreatures.debug.Debugger;
import processing.core.PApplet;
import processing.core.PVector;

public class Plant extends Entity implements Transformable, Colorable {
    private final Transform transform;
    private final float maxSize;

    public Plant(PVector pos, float growth) {
        float tmpMaxSize = Main.getInstance().randomGaussian() + 10f;
        this.maxSize = Math.max(5f, Math.min(15f, tmpMaxSize));
        this.transform = new Transform(pos, maxSize * growth);
    }

    @Override
    public void tick() {
        if (transform.getSize() < maxSize)
            transform.setSize(transform.getSize() + 0.01f);
    }

    @Override
    public void draw(PApplet applet) {
        applet.fill(60, 255, 60);
        applet.ellipse(transform.getPos().x, transform.getPos().y, transform.getSize(), transform.getSize());
    }

    @Override
    public void accept(Debugger debugger) {
        super.accept(debugger);
        debugger.setInfo("Growth", transform.getSize() / maxSize);
        debugger.setInfo("Nutrition", getNutritionValue());
        debugger.setInfo("MaxSize", maxSize);
    }

    public float getNutritionValue() {
        return transform.getSize() / 5f;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public PVector getColor() {
        return new PVector(60, 255, 60);
    }
}
