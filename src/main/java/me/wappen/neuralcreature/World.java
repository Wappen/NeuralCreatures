package me.wappen.neuralcreature;

import me.wappen.neuralcreature.entities.creature.Creature;
import me.wappen.neuralcreature.entities.Plant;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class World implements Tickable, Drawable {

    Random rng;
    private final Map<Long, Entity> entities;

    public World() {
        rng = new Random();
        entities = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            if (rng.nextBoolean())
                spawn(new Creature(PVector.random2D().mult(100)));
            else
                spawn(new Plant(PVector.random2D().mult(100)));
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
        for (Entity entity : entities.values()) {
            applet.fill(255, 255, 255);
            entity.draw(applet);
        }
    }
}
