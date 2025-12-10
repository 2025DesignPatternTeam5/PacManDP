package game.gameState;

import game.Game;

import java.awt.*;

public abstract class GameState {
    protected Game game;
    Graphics2D g;
    public GameState(Game game) {this.game = game;}

    public void die(){}
    public void gameClear(){}
    public void retryGame(){}
    public void exitGame(){}
    public Graphics2D screen(int width, int height, Graphics2D g){return g;};
    public int state_now(){return 0;}

}
