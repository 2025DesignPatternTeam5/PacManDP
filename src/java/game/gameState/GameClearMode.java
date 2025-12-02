package game.gameState;

import game.Game;

public class GameClearMode extends GameState {
    public GameClearMode(Game game) {super(game);}

    public void retry(){
        game.switchRunning();
    }
    public void exit(){
        System.exit(0);
    }
}
