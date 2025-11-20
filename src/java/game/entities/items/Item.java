package game.entities.items;

import game.entities.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// static Entity를 상속한 item.. 근데 사진을 넣어야함.
public abstract class Item extends StaticEntity {
    protected BufferedImage sprite;

    public Item(int xPos, int yPos, String spriteName) {
        super(32, xPos, yPos);
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("img/items/" + spriteName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (this.sprite != null) {
            // 문법: g.drawImage(이미지, x좌표, y좌표, 가로크기, 세로크기, 옵저버);
            g.drawImage(this.sprite, (int)this.xPos, (int)this.yPos, 32, 32, null);
        }
    }
}
