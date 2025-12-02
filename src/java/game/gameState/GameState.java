package game.gameState;

import game.Game;

public abstract class GameState {
    protected Game game;
    private int level = 0;
    public GameState(Game game) {this.game = game;}

    public void die(){}
    public void gameClear(){}
    public void retryGame(){}
    public void exitGame(){}


}
