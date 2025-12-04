package game.gameState;

import game.Game;

public class GameOverMode extends GameState {
    public GameOverMode(Game game) {super(game);}

    public void retryGame(){
        game.switchRunning();
    }
    public void exitGame(){
        System.exit(0);
    }
}
