package me.wappen.neuralcreatures.entities.creature.genetic;

import me.wappen.neuralcreatures.entities.creature.CreatureState;
import me.wappen.neuralcreatures.entities.creature.muscles.Muscles;
import me.wappen.neuralcreatures.entities.creature.senses.Senses;
import me.wappen.neuralcreatures.neural.Network;
import processing.core.PVector;

public class Genome implements CreatureState {

    private float speed = 0;
    private PVector color = new PVector(0, 0, 0);
    private Senses senses = new Senses();
    private Muscles muscles = new Muscles();
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
    public Senses getSenses() {
        return senses;
    }

    @Override
    public Muscles getMuscles() {
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

    public void setSenses(Senses senses) {
        this.senses = senses;
    }

    public void setMuscles(Muscles muscles) {
        this.muscles = muscles;
    }

    public void setBrain(Network brain) {
        this.brain = brain;
    }
}
