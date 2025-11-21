package game.pacmanStates;

import game.entities.Pacman;
import game.entities.ghosts.Ghost;

public class SpeedUpState extends PacmanState {
    private int timer = 0;
    private final int DURATION = 60 * 5;

    public SpeedUpState(Pacman pacman) {
        super(pacman, pacman.getSpd() * 2);
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
        return false;
    }

    @Override
    public void update() {
        timer++;
        if (timer >= DURATION) {
            pacman.switchNormalState();
            System.out.println("SpeedUp Ended");
        }
    }
}
