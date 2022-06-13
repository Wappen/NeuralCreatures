package me.wappen.neuralcreatures.entities.creature.senses;

import me.wappen.neuralcreatures.Main;
import me.wappen.neuralcreatures.entities.creature.Creature;

public class KeyboardSense implements Sense {
    @Override
    public double[] get(Creature creature) {
        return new double[] { isPressed() ? 1 : 0 };
    }

    private boolean isPressed() {
        Main main = Main.getInstance();
        return main.keyPressed && main.key == Main.CODED && main.keyCode == 112 /* F1 */;
    }

    @Override
    public int getResolution() {
        return 1;
    }
}
