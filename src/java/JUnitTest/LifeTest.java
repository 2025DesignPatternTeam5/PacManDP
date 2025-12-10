package JUnitTest;

import game.LifeUIPanel;
import game.UIPanel;
import game.entities.Pacman;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import game.entities.items.Item;
import game.ghostStates.FrightenedMode;
import game.ghostStates.GhostState;
import game.itemFactory.AbstractItemFactory;
import game.itemFactory.AppleFactory;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LifeTest {
    Pacman pacman;
    UIPanel ui;

    @BeforeEach
    public void init() {
        ui = new UIPanel(0, 0);
        pacman = new Pacman(0, 0);
        ui.setPacman(pacman);
        pacman.registerObserver(ui);
    }

    @Test
    public void increaseLifeTest() {
        AbstractItemFactory factory = new AppleFactory();
        Item item = factory.makeItem(0, 0);

        int before = pacman.getLifeCnt();
        for(int i = 0; i < 10; i++) { //500 * 10 = 5000점 획득
            pacman.notifyObserverItemEaten(item);
        }
        int after = pacman.getLifeCnt();
        assertEquals(before + 1, after, "라이프가 1 증가해야 함.");
    }

    @Test
    public void decreaseLifeTest() {
        int before = pacman.getLifeCnt();
        pacman.notifyObserverPacmanDead();
        int after = pacman.getLifeCnt();
        assertEquals(before - 1, after, "라이프가 1 감소해야 함.");
    }
}
