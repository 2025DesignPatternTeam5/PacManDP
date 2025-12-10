package game.gameState;

import game.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class RunningMode extends GameState {
    private Image backgroundImage;
    public RunningMode(Game game) throws IOException {
        super(game);
        backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("img/background.png"));
    }

    public void die(){
        game.switchGameOver();
    }
    public void gameClear(){
        game.switchGameClear();
    }
    public Graphics2D screen(int width, int height, Graphics2D g){
        if (g != null) {
            g.drawImage(backgroundImage, 0, 0, width, height, null);
            game.render(g);
        }
        return g;
    }
    public int state_now(){
        return 0;
    }
}
