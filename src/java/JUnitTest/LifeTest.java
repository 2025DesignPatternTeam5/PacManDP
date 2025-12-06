package JUnitTest;

import game.entities.Pacman;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LifeTest {
    Pacman pacman = new Pacman(1, 1);

    @Test
    public void increaseLifeTest() {
        int before = pacman.getLifeCnt();
        pacman.increaseLifeCnt();
        int after = pacman.getLifeCnt();
        assertEquals(before + 1, after, "라이프가 1 증가해야 함.");
    }

    @Test
    public void decreaseLifeTest() {
        int before = pacman.getLifeCnt();
        pacman.decreaseLifeCnt();
        int after = pacman.getLifeCnt();
        assertEquals(before - 1, after, "라이프가 1 감소해야 함.");
    }
}
