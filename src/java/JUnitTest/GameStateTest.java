package JUnitTest;

import game.Game;
import game.GameLauncher;
import game.GameplayPanel;
import game.UIPanel;
import game.gameState.GameClearMode;
import game.gameState.GameOverMode;
import game.gameState.GameState;
import game.gameState.RunningMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class GameStateTest {
    Game game=new Game(0);
    UIPanel ui;
    JPanel gameWindow = new JPanel(new BorderLayout());

    @BeforeEach
    public void setUp() {
        try {
            gameWindow.add(new GameplayPanel(448,496), BorderLayout.CENTER); //448 496
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        ui=new UIPanel(0,0);
        gameWindow.add(ui, BorderLayout.NORTH);
        ui.updateScore(100);
    }
    @Test
    public void gameStateTest() {
        assertInstanceOf(RunningMode.class,game.getGameState(), "RunningMode");
        game.getGameState().gameClear();
        assertInstanceOf(GameClearMode.class,game.getGameState(), "GameClearMode");
        game.getGameState().retryGame();
        game.getGameState().die();
        assertInstanceOf(GameOverMode.class,game.getGameState(), "GameClearMode");
    }

}
