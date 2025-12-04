package game.pacmanEffect;

import game.entities.Pacman;

import static java.lang.Math.max;

public class InvulnerableEffect extends EffectCommand {
    public InvulnerableEffect(long duration) {
        super(duration);
    }

    @Override
    public void apply(Pacman pacman) {
        // 무적 추가
        pacman.setInvulnerable(true);
        System.out.println(this.getClass().getSimpleName() + " apply. (Effect: " + this.getClass().getSimpleName() + ")");
    }

    @Override
    public void remove(Pacman pacman) {
        // 무적 취소
        pacman.setInvulnerable(false);
        System.out.println(this.getClass().getSimpleName() + " remove. (Effect: Normal)");
    }
}
