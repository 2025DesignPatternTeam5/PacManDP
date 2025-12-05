package game.gameState;

import game.Game;

public class RunningMode extends GameState {
    public RunningMode(Game game) {super(game);}

    public void die(){
        game.switchGameOver();
    }
    public void gameClear(){
        game.switchGameClear();
    }
}
