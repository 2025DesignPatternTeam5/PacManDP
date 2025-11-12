package game.entities;

import java.awt.*;

//Classe pour les SuperPacGums
public class SuperPacGum extends StaticEntity {
    private int frameCount = 0;

    public SuperPacGum(int xPos, int yPos) {
        super(16, xPos, yPos);
    }

    @Override
    public void render(Graphics2D g) {
        //SuperPacGum이 깜빡이도록 하기 위해, 60프레임 중 30프레임만 렌더링한다
        if (frameCount%60 < 30) {
            g.setColor(new Color(255, 183, 174));
            g.fillOval(this.xPos, this.yPos, this.size, this.size);
        }
    }

    @Override
    public void update() {
        frameCount++;
    }
}
