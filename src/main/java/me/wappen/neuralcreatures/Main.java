package me.wappen.neuralcreatures;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class Main extends PApplet {
    private static Main instance;

    private final PVector cameraMove = new PVector(0, 0);
    private float moveSpeed = 2.0f;

    private Camera camera;
    private World world;

    private final AtomicInteger fastForward = new AtomicInteger(0);
    private Thread simulationThread;

    public Main() {
        instance = this;
    }

    @Override
    public void settings() {
        size(1200, 800);
        camera = new Camera(new Transform(new PVector(0, 0), new PVector(1, 1)));
    }

    @Override
    public void setup() {
        surface.setResizable(true);
        surface.setFrameRate(90);
        world = new World();
    }

    @Override
    public void draw() {
        if (!isFastForward()) {
            translate(width / 2f, height / 2f); // Translate to center
            scale(camera.getTransform().getSize().x, camera.getTransform().getSize().y);
            translate(-camera.getTransform().getPos().x, -camera.getTransform().getPos().y); // Apply camera transform

            camera.move(new PVector(
                    cameraMove.x / camera.getTransform().getSize().x,
                    cameraMove.y / camera.getTransform().getSize().y)
                    .normalize().mult(moveSpeed));

            camera.tick();
            simulate();
            world.draw(this);
        }
        else {
            fill(255, 255, 255);
            stroke(0);
            strokeWeight(0);
            textSize(100);
            textAlign(CENTER, CENTER);
            text(">>", width / 2f, height / 2f);
        }
    }

    private void simulate() {
        do {
            world.tick();
        } while(isFastForward());
    }

    private void toggleFastForward() {
        fastForward.getAndIncrement();

        if (isFastForward()) {
            simulationThread = new Thread(this::simulate);
            simulationThread.start();
        }
        else {
            try {
                simulationThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isFastForward() {
        return fastForward.get() % 2 != 0;
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
            case 113 /* F2 */ -> toggleFastForward();
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
        float zoomSpeed = 0.3f;
        camera.zoom(-event.getCount() * zoomSpeed);
    }

    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    public static Main getInstance() {
        return instance;
    }
}