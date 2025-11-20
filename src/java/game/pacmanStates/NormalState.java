package game.pacmanStates;

import game.entities.Pacman;
import game.entities.ghosts.Ghost;

public class NormalState extends PacmanState {

    public NormalState(Pacman pacman) {
        super(pacman);
    }

    @Override
    public int getSpeed() {
        return 2;
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
