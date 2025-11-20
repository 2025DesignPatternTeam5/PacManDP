package game.ghostFactory;

import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import game.ghostStates.GhostState;

//Factory concrète pour créer des fantômes Blinky
public class BlinkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos,int lvl) {
        Ghost ghost = new Blinky(xPos, yPos);
        GhostState state = ghost.getState();
        state.lvlGhost(lvl);
        return ghost;
    }
}
