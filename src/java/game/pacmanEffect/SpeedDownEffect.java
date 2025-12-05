package game.pacmanEffect;

import game.entities.MovingEntity;

import static java.lang.Math.max;

public class SpeedDownEffect extends EffectCommand {
    private final int speedBoost = -1;
    public SpeedDownEffect(long duration) {
        super(duration, "speedDownEffect.png");
    }

    @Override
    public void apply(MovingEntity movingEntity) {
        // 현재 속도에 부스트를 더함
        movingEntity.setSpd(movingEntity.getSpd() + speedBoost);
        System.out.println(this.getClass().getSimpleName() + " apply. (Speed: " + movingEntity.getSpd() + ")");
    }

    @Override
    public void remove(MovingEntity movingEntity) {
        // 속도 원상 복구
        movingEntity.setSpd(max(movingEntity.getSpd() - speedBoost, 0));
        System.out.println(this.getClass().getSimpleName() + " remove. (Speed: " + movingEntity.getSpd() + ")");
    }
}
