package game.ghostFactory;

import game.entities.ghosts.*;

//서로 다른 생성자를 이용해 구체적인 유령들을 생성하는 추상 팩토리(Abstract Factory)
public abstract class AbstractGhostFactory {
    public abstract Ghost makeGhost(int xPos, int yPos, int lvl);
}

