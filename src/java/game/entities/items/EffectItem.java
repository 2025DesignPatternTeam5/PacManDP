package game.entities.items;

import game.pacmanEffect.EffectCommand;

public abstract class EffectItem extends Item {
    protected final EffectCommand effectCommand;

    public EffectItem(int xPos, int yPos, String spriteName, EffectCommand effectCommand) {
        super(xPos, yPos, spriteName);
        if (effectCommand == null)
            throw new NullPointerException("수정 필요! EffectItem 생성 시 command가 누락되었습니다!");
        this.effectCommand = effectCommand;
    }

    public EffectCommand getEffectCommand() {
        return effectCommand;
    }
}
