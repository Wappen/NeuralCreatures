package me.wappen.neuralcreatures.entities.creature;

import me.wappen.neuralcreatures.entities.creature.muscles.MuscleSystem;
import me.wappen.neuralcreatures.entities.creature.senses.SensorySystem;
import me.wappen.neuralcreatures.neural.NeuralNetwork;
import processing.core.PVector;

public class CreaturePrototype implements CreatureBlueprint {

    private CreatureGenome genome;
    private float speed = 0;
    private PVector color = new PVector(0, 0, 0);
    private SensorySystem senses = new SensorySystem();
    private MuscleSystem muscles = new MuscleSystem();
    private NeuralNetwork brain = null;

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

    public void setGenome(CreatureGenome genome) {
        this.genome = genome;
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

    public void setBrain(NeuralNetwork brain) {
        this.brain = brain;
    }
}
