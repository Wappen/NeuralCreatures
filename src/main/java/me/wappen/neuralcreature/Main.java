package me.wappen.neuralcreature;

import processing.core.PApplet;

public class Main extends PApplet {
    World world;

    @Override
    public void settings() {
        size(1200, 800);
    }

    @Override
    public void setup() {
        world = new World();
    }

    @Override
    public void draw() {
        translate(width / 2f, height / 2f);

        world.tick();
        world.draw(this);
    }

    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }
}