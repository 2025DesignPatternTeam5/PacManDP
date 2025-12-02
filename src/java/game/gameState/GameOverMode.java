package game.gameState;

import game.Game;

public class GameOverMode extends GameState {
    public GameOverMode(Game game) {super(game);}

    public void retry(){
        game.switchRunning();
    }
    public void exit(){
        System.exit(0);
    }
}
