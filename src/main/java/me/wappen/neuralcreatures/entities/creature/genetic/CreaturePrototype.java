package me.wappen.neuralcreatures.entities.creature.genetic;

import me.wappen.neuralcreatures.entities.creature.CreatureBlueprint;
import me.wappen.neuralcreatures.entities.creature.muscles.MuscleSystem;
import me.wappen.neuralcreatures.entities.creature.senses.SensorySystem;
import me.wappen.neuralcreatures.neural.Network;
import processing.core.PVector;

public class CreaturePrototype implements CreatureBlueprint {

    private float speed = 0;
    private PVector color = new PVector(0, 0, 0);
    private SensorySystem senses = new SensorySystem();
    private MuscleSystem muscles = new MuscleSystem();
    private Network brain = new Network();

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

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setColor(PVector color) {
        this.color = color;
    }

    public void setSenses(SensorySystem senses) {
        this.senses = senses;
    }

    public void setMuscles(MuscleSystem muscles) {
        this.muscles = muscles;
    }

    public void setBrain(Network brain) {
        this.brain = brain;
    }
}
