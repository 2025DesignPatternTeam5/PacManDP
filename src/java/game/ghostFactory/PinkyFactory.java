package game.ghostFactory;

import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import game.entities.ghosts.Pinky;
import game.ghostStates.GhostState;

//Factory concrète pour créer des fantômes Pinky
public class PinkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos, int lvl) {
        Ghost ghost = new Pinky(xPos, yPos);
        GhostState state = ghost.getState();
        state.lvlGhost(lvl);
        return ghost;
    }
}
