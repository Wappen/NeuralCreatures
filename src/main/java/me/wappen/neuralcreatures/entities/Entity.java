package me.wappen.neuralcreatures.entities;

import me.wappen.neuralcreatures.Drawable;
import me.wappen.neuralcreatures.Tickable;
import me.wappen.neuralcreatures.World;
import me.wappen.neuralcreatures.debug.Debuggable;
import me.wappen.neuralcreatures.debug.Debugger;

public abstract class Entity implements Tickable, Drawable, Debuggable {
    private static long staticId; // used to set new id on construction

    private final long id;

    private World world;

    public Entity() {
        this.id = staticId++;
    }

    @Override
    public void accept(Debugger debugger) {
        debugger.setTitle("Entity [%d]".formatted(getId()));
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
