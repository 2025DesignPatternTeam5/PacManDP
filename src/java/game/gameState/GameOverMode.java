package game.gameState;

import game.Game;

import java.awt.*;

public class GameOverMode extends GameState {
    public GameOverMode(Game game) {super(game);}

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
            String gameOverText = "GAME OVER";
            g.setColor(Color.YELLOW);
            Font largeFont = new Font("Arial", Font.BOLD, 72);
            g.setFont(largeFont);
            FontMetrics fmLarge = g.getFontMetrics();
            int xLarge = (width - fmLarge.stringWidth(gameOverText)) / 2;
            int yLarge = (height / 2) - (fmLarge.getHeight() / 2);
            g.drawString(gameOverText, xLarge, yLarge);
            String retryText = "Retry? (Y/N)";
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
        return 2;
    }
}
