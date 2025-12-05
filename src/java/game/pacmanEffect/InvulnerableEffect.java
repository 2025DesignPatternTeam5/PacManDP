package game.pacmanEffect;

import game.entities.MovingEntity;

public class InvulnerableEffect extends EffectCommand {
    public InvulnerableEffect(long duration) {
        super(duration, "invulnerableEffect.png");
    }

    @Override
    public void apply(MovingEntity movingEntity) {
        // 무적 추기
        movingEntity.setInvulnerable(true);
        System.out.println(this.getClass().getSimpleName() + " apply. (Effect: " + this.getClass().getSimpleName() + ")");
    }

    @Override
    public void remove(MovingEntity movingEntity) {
        // 무적 취소
        movingEntity.setInvulnerable(false);
        System.out.println(this.getClass().getSimpleName() + " remove.");
    }
}
