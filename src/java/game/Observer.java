package game;

import game.entities.PacGum;
import game.entities.SuperPacGum;
import game.entities.ghosts.Ghost;
import game.entities.items.Item;
import game.pacmanEffect.EffectCommand;

//Observer 인터페이스
public interface Observer {
    void updatePacGumEaten(PacGum pg);
    void updateSuperPacGumEaten(SuperPacGum spg);
    void updateGhostCollision(Ghost gh);
    void updateItemEaten(Item item);
    void updateEffectAdded(EffectCommand effect);
    void updateEffectRemoved(EffectCommand effect);
    void updateEffectTick(EffectCommand effect);
}
