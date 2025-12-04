package game;

import game.entities.PacGum;
import game.entities.SuperPacGum;
import game.entities.ghosts.Ghost;
import game.entities.items.Item;
import game.pacmanEffect.EffectCommand;

//Observer패턴의 Subject 인터페이스 (Sujet은 Subject의 불어이다)
public interface Sujet {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserverPacGumEaten(PacGum pg);
    void notifyObserverSuperPacGumEaten(SuperPacGum spg);
    void notifyObserverGhostCollision(Ghost gh);
    void notifyObserverItemEaten(Item item);
    void notifyObserverEffectAdded(EffectCommand newEffect);
    void notifyObserverEffectRemoved(EffectCommand newEffect);
    void notifyObserverEffectTick(EffectCommand effect);
}
