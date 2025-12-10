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
    Game game;
    UIPanel ui;

    @BeforeEach
    public void setUp() {
        ui=new UIPanel(0,0);
        GameLauncher.setUIPanel(ui);
        game = new Game(0);
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
