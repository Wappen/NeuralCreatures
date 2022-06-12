package me.wappen.neuralcreatures.entities;

import me.wappen.neuralcreatures.Drawable;
import me.wappen.neuralcreatures.Tickable;
import me.wappen.neuralcreatures.World;

public abstract class Entity implements Tickable, Drawable {
    private static long staticId; // used to set new id on construction

    private final long id;

    private World world;

    public Entity() {
        this.id = staticId++;
    }

    public long getId() {
        return id;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
