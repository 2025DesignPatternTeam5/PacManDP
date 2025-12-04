package game.pacmanEffect;

import game.entities.MovingEntity;

public abstract class EffectCommand {
    protected long timer;
    protected long duration; // 지속 시간 (ms)

    public EffectCommand(long duration) {
        this.duration = duration * 60;
        this.timer = 0;
    }

    // 효과 적용 시점 (최초 1회)
    public abstract void apply(MovingEntity movingEntity);

    // 효과 종료 시점 (원상 복구)
    public abstract void remove(MovingEntity movingEntity);

    // 중복 아이템 획득 시 타이머 초기화
    public void resetTimer() {
        this.timer = 0;
        System.out.println(this.getClass() + " 지속시간 초기화됨");
    }

    public void update() {
        timer++;
    }

    public boolean isExpired() {
        return timer >= duration;}

}


