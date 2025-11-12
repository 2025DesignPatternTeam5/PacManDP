package game;

import game.entities.PacGum;
import game.entities.SuperPacGum;
import game.entities.ghosts.Ghost;

//Observer 인터페이스
public interface Observer {
    void updatePacGumEaten(PacGum pg);
    void updateSuperPacGumEaten(SuperPacGum spg);
    void updateGhostCollision(Ghost gh);
}
