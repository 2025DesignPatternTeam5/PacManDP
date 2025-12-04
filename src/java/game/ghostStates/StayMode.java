package game.ghostStates;

import game.entities.ghosts.*;

public class StayMode extends GhostState{
    public StayMode(Ghost ghost) {super(ghost);}
    @Override
    public void lvlGhost(int level){
        if(ghost.getClass()== Blinky.class){ghost.switchHouseMode();}
        if(ghost.getClass()== Clyde.class&&level>0){ghost.switchHouseMode();}
        if(ghost.getClass()== Inky.class&&level>1){ghost.switchHouseMode();}
        if(ghost.getClass()== Pinky.class&&level>2){ghost.switchHouseMode();}
    }
}
