package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.*;
import me.wappen.neuralcreatures.entities.Entity;
import me.wappen.neuralcreatures.entities.Plant;
import me.wappen.neuralcreatures.entities.creature.muscles.MoveMuscle;
import me.wappen.neuralcreatures.entities.creature.muscles.MuscleSystem;
import me.wappen.neuralcreatures.entities.creature.senses.HealthSense;
import me.wappen.neuralcreatures.entities.creature.senses.SensorySystem;
import me.wappen.neuralcreatures.entities.creature.senses.VisionSense;
import me.wappen.neuralcreatures.neural.NNUtils;
import me.wappen.neuralcreatures.neural.builder.LayeredNetworkBuilder;
import me.wappen.neuralcreatures.neural.Network;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;

public class Creature extends Entity implements Transformable, Colorable, CreatureState {
    private final Transform transform = new Transform(new PVector(0, 0), 1, new PVector(0, 1));

    private final float speed;
    private final PVector color;

    private final SensorySystem senses;
    private final MuscleSystem muscles;
    private final Network brain;

    private final Path path = new Path(this);

    private float energy = 1f;
    private float health = 1f;

    public Creature(PVector pos) {
        this.transform.setPos(pos);
        this.transform.setSize(10);
        this.transform.setDir(PVector.random2D());
        this.speed = 1;

        senses = new SensorySystem();
        senses.addSense(new HealthSense());
        senses.addSense(new VisionSense());

        muscles = new MuscleSystem();
        muscles.addMuscle(new MoveMuscle());

        LayeredNetworkBuilder nb = new LayeredNetworkBuilder(NNUtils::reLU);

        nb.addLayer(senses.getResolution(), NNUtils::map01); // input layer
        //nb.addLayer(8);
        nb.addLayer(4);
        //nb.addLayer(8);
        nb.addLayer(muscles.getResolution(), NNUtils::map11); // output layer
        this.brain = nb.build();

        //color = new PVector(255, 100, 100);
        color = PVector.random3D().add(1, 1, 1).mult(0.5f).mult(255);
    }

    public Creature(CreatureState state) {
        this.transform.setSize(10);
        this.transform.setDir(PVector.random2D());

        this.speed = state.getSpeed();
        this.color = state.getColor();
        this.senses = (SensorySystem) state.getSenses().copy();
        this.muscles = (MuscleSystem) state.getMuscles().copy();
        this.brain = state.getBrain().copy();
    }

    @Override
    public void tick() {
        float aggravationRate = 0.01f;
        float healRate = 0.001f;
        float starvationRate = 0.00125f;

        if (energy <= 0)
            health -= aggravationRate;
        if (health <= 1)
            health += healRate;

        energy -= starvationRate;

        path.tick();
        brain.process(() -> senses.get(this), (double[] out) -> muscles.handle(out, this));

        if (Main.getInstance().frameCount % 8 == 0) {
            List<Entity> hits = getWorld().getEntitiesInRadius(transform.getPos(), transform.getSize());

            for (Entity hit : hits) {
                if (hit instanceof Plant plant) {
                    energy += plant.getNutritionValue();
                    getWorld().deferTask(() -> getWorld().despawn(plant));
                }
            }
        }

        if (health <= 0)
            getWorld().deferTask(() -> getWorld().despawn(this));
    }

    @Override
    public void draw(PApplet applet) {
        PVector pos = transform.getPos();
        float size = transform.getSize();
        PVector dir = transform.getDir();

        // Draw body
        applet.fill(color.x, color.y, color.z);
        applet.ellipse(pos.x, pos.y, size, size);

        // Draw eye
        PVector eyePos = getEyePos();
        applet.fill(255);
        applet.stroke(color.x, color.y, color.z);
        applet.strokeWeight(0.666f);
        applet.ellipse(eyePos.x, eyePos.y, size / 2, size / 2);
        applet.noStroke();

        // Draw pupil
        PVector pupilPos = eyePos.add(dir.copy().rotate(smoothSquareWave(applet.frameCount / 30f + color.x)));
        applet.fill(0);
        applet.ellipse(pupilPos.x, pupilPos.y, size / 5, size / 5);

        // Draw path
        path.draw(applet);
    }

    public void move(PVector dir) {
        if (dir.mag() > 0) {
            float exhaustionFactor = 0.005f;
            energy -= dir.mag() * exhaustionFactor;
            transform.setDir(dir);
            transform.translate(dir.copy().mult(speed));
        }
    }

    public PVector getEyePos() {
        PVector pos = transform.getPos();
        PVector dir = transform.getDir();
        float size = transform.getSize();

        return pos.copy().add(dir.copy().mult(size / 3));
    }

    private static float smoothSquareWave(float t) {
        return (float) (Math.sin(t) + Math.sin(t * 3) / 3);
    }

    public float getEnergy() {
        return energy;
    }

    public float getHealth() {
        return health;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public PVector getColor() {
        return color;
    }

    @Override
    public SensorySystem getSenses() {
        return senses;
    }

    @Override
    public MuscleSystem getMuscles() {
        return muscles;
    }

    @Override
    public Network getBrain() {
        return brain;
    }
}
