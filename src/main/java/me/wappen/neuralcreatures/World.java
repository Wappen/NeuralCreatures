package me.wappen.neuralcreatures;

import me.wappen.neuralcreatures.entities.creature.Creature;
import me.wappen.neuralcreatures.entities.Plant;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public class World implements Tickable, Drawable {

    Random rng;
    private final Map<Long, Entity> entities;

    public World() {
        rng = new Random();
        entities = new HashMap<>();

        massSpawn(pos -> spawn(new Creature(pos)), 1000, new PVector(-1000, -1000), new PVector(1000, 1000));
        massSpawn(pos -> spawn(new Plant(pos)), 1000, new PVector(-1000, -1000), new PVector(1000, 1000));
    }

    public void massSpawn(Consumer<PVector> posConsumer, int num, PVector topLeft, PVector bottomRight) {
        PVector delta = bottomRight.copy().sub(topLeft);

        for (int i = 0; i < num; i++) {
            PVector pos = new PVector(rng.nextFloat() * delta.x + topLeft.x, rng.nextFloat() * delta.y + topLeft.y);
            posConsumer.accept(pos);
        }
    }

    public boolean spawn(Entity entity) {
        if (entities.containsKey(entity.getId()))
            return false;

        entity.setWorld(this);
        entities.put(entity.getId(), entity);
        return true;
    }

    public boolean despawn(Entity entity) {
        if (!entities.containsKey(entity.getId()))
            return false;

        entity.setWorld(null);
        entities.remove(entity.getId());
        return true;
    }

    @Override
    public void tick() {
        for (Entity entity : entities.values()) {
            entity.tick();
        }
    }

    @Override
    public void draw(PApplet applet) {
        applet.background(32);

        for (Entity entity : entities.values()) {
            applet.fill(255, 255, 255);
            applet.stroke(0);
            applet.strokeWeight(0);
            applet.noStroke();
            entity.draw(applet);
        }
    }
}
