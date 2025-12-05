package game.entities.items;

import game.pacmanEffect.SpeedDownEffect;

// speed - 1; 2s
public class Banana extends EffectItem {
    public Banana(int xPos, int yPos) {
        super(xPos, yPos, "banana.png", new SpeedDownEffect(2));
    }
}
