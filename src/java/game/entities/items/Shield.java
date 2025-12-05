package game.entities.items;


import game.pacmanEffect.InvulnerableEffect;

// 무적 5초
public class Shield extends EffectItem{
    public Shield(int xPos, int yPos) {
        super(xPos, yPos, "Shield.png", new InvulnerableEffect(5));
    }
}
