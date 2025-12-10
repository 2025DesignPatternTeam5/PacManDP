package game.gameState;

import game.Game;
import game.GameplayPanel;
import game.utils.KeyHandler;

import java.awt.*;

public class GameClearMode extends GameState {
    public GameClearMode(Game game) {super(game);}

    public void retryGame(){
        game.switchRunning();
    }
    public void exitGame(){
        System.exit(0);
    }

    public Graphics2D screen(int width, int height, Graphics2D g){
        if (g != null) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            String gameClearText = "LEVEL CLEAR!";
            g.setColor(Color.YELLOW);
            Font largeFont = new Font("Arial", Font.BOLD, 56);
            g.setFont(largeFont);
            FontMetrics fmLarge = g.getFontMetrics();
            int xLarge = (width - fmLarge.stringWidth(gameClearText)) / 2;
            int yLarge = (height / 2) - (fmLarge.getHeight() / 2);
            g.drawString(gameClearText, xLarge, yLarge);
            String retryText = "Next level? (Y/N)";
            g.setColor(Color.WHITE);
            Font smallFont = new Font("Arial", Font.PLAIN, 24);
            g.setFont(smallFont);
            FontMetrics fmSmall = g.getFontMetrics();
            int ySmall = yLarge + fmLarge.getHeight() + 20;
            int xSmall = (width - fmSmall.stringWidth(retryText)) / 2;

            g.drawString(retryText, xSmall, ySmall);
        }
        return g;
    }
    public int state_now(){
        return 1;
    }
}
