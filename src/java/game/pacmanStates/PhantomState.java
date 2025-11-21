package game.pacmanStates;

import game.entities.Pacman;
import game.entities.ghosts.Ghost;

public class PhantomState extends PacmanState {
    private int timer = 0;
    private final int DURATION = 60 * 5;

    public PhantomState(Pacman pacman) {
        super(pacman);
    }

    @Override
    public void resetTimer() {
        this.timer = 0;
    }

    @Override
    public int getSpeed() {
        return this.spd;
    }

    @Override
    public boolean isInvincible() {
        return true; // 무적
    }

    @Override
    public void update() {
        timer++;
        if (timer >= DURATION) {
            pacman.switchNormalState();
            System.out.println("Phantom Mode Ended");
        }
    }
}