package game.entities.items;

import game.pacmanEffect.SpeedDownEffect;
import game.pacmanEffect.SpeedUpEffect;

public class Banana extends EffectItem {
    public Banana(int xPos, int yPos) {
        super(xPos, yPos, "banana.png", new SpeedDownEffect(2));
    }
}
