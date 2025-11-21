package game.pacmanStates;

import game.entities.Pacman;
import game.entities.ghosts.Ghost;

public class NormalState extends PacmanState {

    public NormalState(Pacman pacman) {
        super(pacman);
    }
    public NormalState(Pacman pacman, int speed) {
        super(pacman, speed);
    }

    @Override
    public int getSpeed() {
        return this.spd;
    }

    @Override
    public boolean isInvincible() {
        return false;
    }

    @Override
    public void update() {
        //
    }
}
