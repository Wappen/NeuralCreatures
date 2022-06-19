package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.Colorable;
import me.wappen.neuralcreatures.Main;
import me.wappen.neuralcreatures.Transform;
import me.wappen.neuralcreatures.Transformable;
import me.wappen.neuralcreatures.debug.Debugger;
import me.wappen.neuralcreatures.entities.Entity;
import me.wappen.neuralcreatures.entities.Plant;
import me.wappen.neuralcreatures.entities.creature.genetic.CreatureGenome;
import me.wappen.neuralcreatures.entities.creature.genetic.CreatureGenomeSerializer;
import me.wappen.neuralcreatures.entities.creature.muscles.MuscleSystem;
import me.wappen.neuralcreatures.entities.creature.senses.SensorySystem;
import me.wappen.neuralcreatures.neural.NeuralNetwork;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;

public class Creature extends Entity implements Transformable, Colorable, CreatureBlueprint {
    private final Transform transform = new Transform(new PVector(0, 0), 1, new PVector(0, 1));

    private final float speed;
    private final PVector color;

    private final CreatureGenome genome;
    private final SensorySystem senses;
    private final MuscleSystem muscles;
    private final NeuralNetwork brain;

    private final Path path = new Path(this);

    private float energy = 1f;
    private float health = 1f;

    public Creature(CreatureBlueprint blueprint) {
        this.transform.setSize(10);
        this.transform.setDir(PVector.random2D());

        this.genome = blueprint.getGenome();
        this.speed = blueprint.getSpeed();
        this.color = blueprint.getColor().copy();
        this.senses = (SensorySystem) blueprint.getSenses().copy();
        this.muscles = (MuscleSystem) blueprint.getMuscles().copy();
        this.brain = new NeuralNetwork(blueprint.getBrain());
    }

    @Override
    public void tick() {
        float aggravationRate = 0.01f;
        float healRate = 0.001f;
        float starvationRate = 0.005f;

        if (energy <= 0)
            health -= aggravationRate;
        if (health <= 1)
            health += healRate;

        energy -= starvationRate;

        path.tick();
        muscles.handle(brain.process(senses.get(this)), this);

        if (Main.getInstance().frameCount % 4 == getId() % 4) {
            List<Entity> hits = getWorld().getEntitiesInRadius(transform.getPos(), transform.getSize() / 2);

            for (Entity hit : hits) {
                if (hit instanceof Plant plant) {
                    energy += plant.getNutritionValue();
                    getWorld().deferTask(() -> getWorld().despawn(plant));
                }
            }
        }

        if (energy > 4)
            giveBirth();

        if (health <= 0)
            getWorld().deferTask(() -> getWorld().despawn(this));
    }

    private void giveBirth() {
        energy -= 3;
        getWorld().deferTask(() -> {
            CreatureGenomeSerializer serializer = new CreatureGenomeSerializer();
            Creature child = new CreatureBirther(serializer, genome, genome).build();
            child.getTransform().setPos(getTransform().getPos());
            getWorld().spawn(child);
        });
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
        PVector pupilPos = eyePos.add(dir.rotate(smoothSquareWave(applet.frameCount / 30f + color.x)));
        applet.fill(0);
        applet.ellipse(pupilPos.x, pupilPos.y, size / 5, size / 5);

        // Draw path
        path.draw(applet);
    }

    public void move(PVector dir) {
        dir.limit(1f);
        if (dir.mag() > 0) {
            float exhaustionFactor = 0.00025f;
            energy -= dir.mag() * exhaustionFactor;
            transform.setDir(dir);
            transform.translate(dir.copy().mult(speed));
        }
    }

    public PVector getEyePos() {
        PVector pos = transform.getPos();
        PVector dir = transform.getDir();
        float size = transform.getSize();

        return pos.add(dir.mult(size / 3));
    }

    @Override
    public void debug(Debugger debugger) {
        super.debug(debugger);
        debugger.setInfo("Health", health);
        debugger.setInfo("Energy", energy);
        debugger.setInfo("Speed", speed);
        debugger.setInfo("Color", color);
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
    public CreatureGenome getGenome() {
        return genome;
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
    public NeuralNetwork getBrain() {
        return brain;
    }

    private static float smoothSquareWave(float t) {
        return (float) (Math.sin(t) + Math.sin(t * 3) / 3);
    }
}
