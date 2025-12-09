package game.pacmanEffect;

import game.entities.MovingEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.lang.Math.max;

public abstract class EffectCommand {
    protected BufferedImage effectImage;
    protected long timer;
    protected long duration; // 지속 시간 (ms)

//    public EffectCommand(long duration) {
//        this.duration = max(duration * 60, 1);
//        this.timer = 0;
//        effectImage = null;
//    }

    public EffectCommand(long duration, String imageName) {
        this.duration = max(duration * 60, 1);
        this.timer = 0;
        try {
            this.effectImage = ImageIO.read(getClass().getClassLoader().getResource("img/effects/" + imageName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
    public long getTimer() {
        return timer;
    }

    public boolean isExpired() {
        return timer >= duration;
    }

    public float getDurationRatio() {
        // 예: timer=150, duration=300 이면 -> 1.0 - 0.5 = 0.5 (절반 남음)
        return 1.0f - ((float)this.timer / (float)this.duration);
    }

    public BufferedImage getEffectImage() {
        return effectImage;
    }
}


