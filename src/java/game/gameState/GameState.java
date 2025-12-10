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
    //각각의 상태 별로 렌더링할 화면을 지정
    public Graphics2D screen(int width, int height, Graphics2D g){return g;};
    //현재의 상태를 int로 반환 (running == 0 , gameclear == 1, gameover ==2)
    public int state_now(){return 0;}
}
