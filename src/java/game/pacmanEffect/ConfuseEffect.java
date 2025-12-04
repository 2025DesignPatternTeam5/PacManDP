package game.pacmanEffect;

import game.entities.MovingEntity;

public class ConfuseEffect extends EffectCommand{
    public ConfuseEffect(long duration) {
        super(duration);
    }

    @Override
    public void apply(MovingEntity movingEntity) {
        // 방향 반전
        movingEntity.setConfuse(true);
        System.out.println(this.getClass().getSimpleName() + " apply. (Effect: " + this.getClass().getSimpleName() + ")");
    }

    @Override
    public void remove(MovingEntity movingEntity) {
        // 방향 반전 취소
        movingEntity.setConfuse(false);
        System.out.println(this.getClass().getSimpleName() + " remove.");
    }
}
