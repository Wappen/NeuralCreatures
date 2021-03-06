package me.wappen.neuralcreatures;

import me.wappen.neuralcreatures.entities.Entity;
import me.wappen.neuralcreatures.entities.Plant;
import me.wappen.neuralcreatures.entities.creature.Creature;
import me.wappen.neuralcreatures.entities.creature.genetic.CreatureGenome;
import me.wappen.neuralcreatures.entities.creature.genetic.genes.*;
import me.wappen.neuralcreatures.misc.ChunkSpace;
import me.wappen.neuralcreatures.misc.Space;
import me.wappen.neuralcreatures.misc.Utils;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.*;
import java.util.function.Consumer;

public class World implements Tickable, Drawable {
    private final Random rng;
    private final Map<Long, Entity> entities;
    private final Space<Entity> entitySpace;

    private final Queue<Runnable> deferredTasks;

    public World() {
        rng = new Random();
        entities = new HashMap<>();
        entitySpace = new ChunkSpace<>(200);
        deferredTasks = new LinkedList<>();

        float worldSize = 1000;

        massSpawn(pos -> {
            CreatureGenome genome = new CreatureGenome();
            genome.addGene(new EyeGene());
            genome.addGene(new LegGene());
            genome.addGene(new SpeedGene());
            genome.addGene(new BrainGene());
            genome.addGene(new ColorGene());
            Creature creature = new Creature(genome.createPrototype());
            creature.getTransform().setPos(pos);
            spawn(creature);
        }, 200, new PVector(-worldSize / 2, -worldSize / 2), new PVector(worldSize / 2, worldSize / 2));

        massSpawn(pos -> spawn(new Plant(pos, rng.nextFloat())), 2000,
                new PVector(-worldSize, -worldSize), new PVector(worldSize, worldSize));
    }

    public Entity getEntityAtCoord(PVector coord) {
        for (Entity e : entitySpace.getClosest(coord)) {
            if (e instanceof Transformable entity) {
                PVector normalizedCoord = coord.copy().sub(entity.getTransform().getPos());
                if (normalizedCoord.mag() < entity.getTransform().getSize() / 2)
                    return e;
            }
        }

        return null;
    }

    public List<Entity> getEntitiesInRadius(PVector coord, float radius) {
        List<Entity> hits = new ArrayList<>();

        for (Entity e : entitySpace.getClosest(coord)) {
            if (e instanceof Transformable entity) {
                PVector normalizedCoord = coord.copy().sub(entity.getTransform().getPos());
                if (normalizedCoord.mag() < radius + entity.getTransform().getSize() / 2)
                    hits.add(e);
            }
        }

        return hits;
    }

    public List<Entity> getEntitiesBetween(PVector start, PVector end) {
        List<Entity> hits = new ArrayList<>();

        for (Entity e : entitySpace.getBetween(start, end)) {
            if (e instanceof Transformable entity) {
                if (Utils.circleLineIntersection(start, end,
                        entity.getTransform().getPos(), entity.getTransform().getSize() / 2))
                    hits.add(e);
            }
        }

        return hits;
    }

    public void deferTask(Runnable task) {
        deferredTasks.add(task);
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

        if (entity instanceof Transformable tEntity)
            entitySpace.addObj(entity, tEntity.getTransform()::getPos);

        entity.setWorld(this);
        entities.put(entity.getId(), entity);
        return true;
    }

    public boolean despawn(Entity entity) {
        if (!entities.containsKey(entity.getId()))
            return false;

        if (entity instanceof Transformable)
            entitySpace.removeObj(entity);

        entity.setWorld(null);
        entities.remove(entity.getId());
        return true;
    }

    @Override
    public void tick() {
        while (!deferredTasks.isEmpty())
            deferredTasks.remove().run();

        for (Entity entity : entities.values())
            entity.tick();

        if (Main.getInstance().frameCount % 8 == 0)
            entitySpace.update();
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
