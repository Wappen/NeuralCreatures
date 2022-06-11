package me.wappen.neuralcreatures;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Main extends PApplet {
    final PVector cameraMove = new PVector(0, 0);
    final float zoomSpeed = 0.3f;
    float moveSpeed = 2.0f;

    Camera camera;
    World world;

    @Override
    public void settings() {
        size(1200, 800);
        camera = new Camera(new Transform(new PVector(0, 0), new PVector(1, 1)));
    }

    @Override
    public void setup() {
        surface.setResizable(true);
        surface.setFrameRate(60);
        world = new World();
    }

    @Override
    public void draw() {
        translate(width / 2f, height / 2f); // Translate to center
        scale(camera.getTransform().getSize().x, camera.getTransform().getSize().y);
        translate(-camera.getTransform().getPos().x, -camera.getTransform().getPos().y); // Apply camera transform

        camera.move(new PVector(
                cameraMove.x / camera.getTransform().getSize().x,
                cameraMove.y / camera.getTransform().getSize().y)
                .normalize().mult(moveSpeed));

        camera.tick();
        world.tick();
        world.draw(this);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (keyCode) {
            case 'W' -> cameraMove.y = -1;
            case 'A' -> cameraMove.x = -1;
            case 'S' -> cameraMove.y = 1;
            case 'D' -> cameraMove.x = 1;
            case SHIFT -> moveSpeed = 50;
            case CONTROL -> moveSpeed = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        switch (keyCode) {
            case 'W', 'S' -> cameraMove.y = 0;
            case 'A', 'D' -> cameraMove.x = 0;
            case SHIFT, CONTROL -> moveSpeed = 5;
        }
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        camera.zoom(-event.getCount() * zoomSpeed);
    }

    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }
}