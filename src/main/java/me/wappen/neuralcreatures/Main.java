package me.wappen.neuralcreatures;

import me.wappen.neuralcreatures.debug.Debugger;
import me.wappen.neuralcreatures.debug.StringDebugger;
import me.wappen.neuralcreatures.entities.Entity;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Main extends PApplet {
    private static Main instance;

    private final PVector cameraMove = new PVector(0, 0);
    private float moveSpeed = 2.0f;

    private Camera camera;
    private Simulator simulator;
    private Entity selected = null;

    public Main() {
        instance = this;
    }

    @Override
    public void settings() {
        size(1200, 800);
        camera = new Camera(new Transform(new PVector(0, 0), 1f));
    }

    @Override
    public void setup() {
        surface.setResizable(true);
        surface.setFrameRate(90);
        simulator = new Simulator(new World());
    }

    @Override
    public void draw() {
        if (!simulator.isFastForward()) {
            translate(width / 2f, height / 2f); // Translate to center
            scale(camera.getTransform().getSize(), camera.getTransform().getSize());
            translate(-camera.getTransform().getPos().x, -camera.getTransform().getPos().y); // Apply camera transform

            camera.move(new PVector(
                    cameraMove.x / camera.getTransform().getSize(),
                    cameraMove.y / camera.getTransform().getSize())
                    .normalize().mult(moveSpeed));

            camera.tick();
            simulator.getWorld().draw(this);

            if (simulator.isRealtime())
                simulator.tick();

            if (selected != null) {
                Transform transform = ((Transformable) selected).getTransform();
                PVector pos = transform.getPos();
                float size = transform.getSize();

                fill(255, 128);
                stroke(255);
                strokeWeight(1);
                ellipse(pos.x, pos.y, size, size);
            }
        }
        else {
            fill(255, 255, 255);
            stroke(0);
            strokeWeight(0);
            textSize(100);
            textAlign(LEFT, TOP);
            text(simulator.isFastForward() ? ">>" : simulator.isPaused() ? "||" : "???", 0, 0);
        }

        if (selected != null) {
            Debugger debugger = new StringDebugger();
            selected.debug(debugger);
            System.out.println(debugger);
        }
    }

    private void selectEntity(Entity entity) {
        selected = entity;
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
            case TAB -> simulator.toggleFastForward();
            case ' ' -> simulator.togglePause();
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

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == LEFT) {
            selectEntity(simulator.getWorld().getEntityAtCoord(mousePos()));
        }
    }

    public PVector mousePos() {
        float s = camera.getTransform().getSize();
        PVector p = camera.getTransform().getPos();
        return new PVector((mouseX - width / 2f) / s + p.x, (mouseY - height / 2f) / s + p.y);
    }

    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    public static Main getInstance() {
        return instance;
    }

    public static Camera getCamera() {
        return instance.camera;
    }
}