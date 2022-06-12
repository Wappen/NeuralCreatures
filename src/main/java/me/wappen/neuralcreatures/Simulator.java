package me.wappen.neuralcreatures;

import java.util.concurrent.atomic.AtomicInteger;

public class Simulator implements Tickable {

    private final World world;

    private Thread simulationThread;

    private final AtomicToggleBoolean paused = new AtomicToggleBoolean(false);
    private final AtomicToggleBoolean fastForward = new AtomicToggleBoolean(false);

    public Simulator(World world) {
        this.world = world;
        this.simulationThread = new Thread(this::simulate);
    }

    public void toggleFastForward() {
        if (fastForward.toggle()) {
            paused.set(false);
            simulationThread = new Thread(this::simulate);
            simulationThread.start();
        }
        else {
            stopSimulation();
        }
    }

    public void togglePause() {
        if (paused.toggle()) {
            fastForward.set(false);
            stopSimulation();
        }
    }

    private void stopSimulation() {
        try {
            if (simulationThread.isAlive())
                simulationThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void tick() {
        if (isRealtime())
            world.tick();
    }

    private void simulate() {
        do {
            world.tick();
        } while(isFastForward());
    }

    public boolean isRealtime() {
        return !isFastForward() && !isPaused();
    }

    public boolean isFastForward() {
        return fastForward.get();
    }

    public boolean isPaused() {
        return paused.get();
    }

    public World getWorld() {
        return world;
    }

    private static class AtomicToggleBoolean {
        private final AtomicInteger val;

        public AtomicToggleBoolean(boolean val) {
            this.val = new AtomicInteger(val ? 1 : 0);
        }

        public boolean toggle() {
            return val.getAndIncrement() % 2 == 0;
        }

        public boolean get() {
            return val.get() % 2 != 0;
        }

        public void set(boolean val) {
            this.val.set(val ? 1 : 0);
        }
    }
}
