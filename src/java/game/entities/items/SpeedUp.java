package game.entities.items;

import game.pacmanEffect.SpeedUpEffect;

// speed + 1; 5s
public class SpeedUp extends EffectItem {
    public SpeedUp(int xPos, int yPos) {
        super(xPos, yPos, "speedup.png", new SpeedUpEffect(5));
    }
}
