package game.ghostFactory;

import game.entities.ghosts.Ghost;
import game.entities.ghosts.Inky;
import game.ghostStates.GhostState;

//Factory concrète pour créer des fantômes Inky
public class InkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        Ghost ghost = new Inky(xPos, yPos);
        return ghost;
    }
}
