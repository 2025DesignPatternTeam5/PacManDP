package game.entities.items;


import game.pacmanEffect.InvulnerableEffect;

// 일정시간 충돌 무시
public class Shield extends EffectItem{
    public Shield(int xPos, int yPos) {
        super(xPos, yPos, "Shield.png", new InvulnerableEffect(5));
    }
}
