package game.gameState;

import game.Game;

public class GameClearMode extends GameState {
    public GameClearMode(Game game) {super(game);}

    public void retryGame(){
        game.switchRunning();
    }
    public void exitGame(){
        System.exit(0);
    }
}
