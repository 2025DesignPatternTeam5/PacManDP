package game.pacmanEffect;

import game.entities.Pacman;

import static java.lang.Math.max;

public class SpeeedUpEffect extends EffectCommand {
    private final int speedBoost = 1;
    public SpeeedUpEffect(long duration) {
        super(duration);
    }

    @Override
    public void apply(Pacman pacman) {
        // 현재 속도에 부스트를 더함
        pacman.setSpd(pacman.getSpd() + speedBoost);
        System.out.println(this.getClass().getSimpleName() + "apply. (Speed: " + pacman.getSpd() + ")");
    }

    @Override
    public void remove(Pacman pacman) {
        // 속도 원상 복구
        pacman.setSpd(max(pacman.getSpd() - speedBoost, 0));
        System.out.println(this.getClass().getSimpleName() + "remove. (Speed: " + pacman.getSpd() + ")");
    }
}
