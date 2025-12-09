package game.ghostFactory;

import game.entities.ghosts.Blinky;
import game.entities.ghosts.Clyde;
import game.entities.ghosts.Ghost;
import game.ghostStates.GhostState;

//Factory concrète pour créer des fantômes Clyde
public class ClydeFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        Ghost ghost = new Clyde(xPos, yPos);
        return ghost;
    }
}
