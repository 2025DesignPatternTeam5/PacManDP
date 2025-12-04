package game.entities.items;

import game.pacmanEffect.ConfuseEffect;

// 방향 반대 5초
public class Demon extends EffectItem {
    public Demon(int xPos, int yPos) {
        super(xPos, yPos, "Demon.png", new ConfuseEffect(5));
    }
}
