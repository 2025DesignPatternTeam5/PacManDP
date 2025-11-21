package game.pacmanStates;

import game.entities.Pacman;
import game.entities.ghosts.Ghost;

public abstract class PacmanState {
    protected Pacman pacman;
    protected int spd = 2;

    public PacmanState(Pacman pacman) {
        this.pacman = pacman;
    }
    public PacmanState(Pacman pacman, int speed) {
        this.pacman = pacman;
        this.spd = speed;
    }

    public abstract int getSpeed();
    public abstract boolean isInvincible();
    public abstract void update();

    public void resetTimer() {}
}
