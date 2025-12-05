package game.entities;

import game.GameplayPanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//Classe abtraite pour décrire une entité mouvante
public abstract class MovingEntity extends Entity {
    protected int spd;
    protected int xSpd = 0;
    protected int ySpd = 0;
    protected BufferedImage sprite;
    protected float subimage = 0;
    protected int nbSubimagesPerCycle;
    protected int direction = 0;
    protected float imageSpd = 0.2f;
    protected boolean invulnerable = false;
    protected boolean confuse = false;

    public MovingEntity(int size, int xPos, int yPos, int spd, String spriteName, int nbSubimagesPerCycle, float imageSpd) {
        super(size, xPos, yPos);
        this.spd = spd;
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("img/" + spriteName));
            this.nbSubimagesPerCycle = nbSubimagesPerCycle;
            this.imageSpd = imageSpd;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        updatePosition();
    }

    public void updatePosition() {
        //엔티티 위치 업데이트
        if (!(xSpd == 0 && ySpd == 0)) { //수평 속도나 수직 속도가 0이 아니면, 그에 따라 수평 위치와 수직 위치를 증가시킨다
            xPos+=xSpd;
            yPos+=ySpd;

            //이동한 방향에 따라 방향 값을 변경한다 (특히 어떤 이미지 부분을 표시할지 결정하는 정수 값)
            if (xSpd > 0) {
                direction = 0;
            } else if (xSpd < 0) {
                direction = 1;
            } else if (ySpd < 0) {
                direction = 2;
            } else if (ySpd > 0) {
                direction = 3;
            }

            //표시할 애니메이션의 현재 이미지 값을 증가시킨다(속도는 가변적이며), 애니메이션 이미지 수에 따라 값이 반복(loop)된다
            subimage += imageSpd;
            if (subimage >= nbSubimagesPerCycle) {
                subimage = 0;
            }
        }

        //엔티티가 게임 구역의 경계를 넘어가면 반대쪽으로 나온다
        if (xPos > GameplayPanel.width) {
            xPos = 0 - size + spd;
        }

        if (xPos < 0 - size + spd) {
            xPos = GameplayPanel.width;
        }

        if (yPos > GameplayPanel.height) {
            yPos = 0 - size + spd;
        }

        if (yPos < 0 - size + spd) {
            yPos = GameplayPanel.height;
        }
    }

    @Override
    public void render(Graphics2D g) {
        //기본적으로, 각 스프라이트는 방향마다 4가지 애니메이션 변형을 포함하며, 각 애니메이션은 일정한 수의 이미지로 구성된다
        //이를 기반으로, 올바른 방향과 애니메이션 프레임에 해당하는 스프라이트 이미지 부분만 화면에 표시한다
        g.drawImage(sprite.getSubimage((int)subimage * size + direction * size * nbSubimagesPerCycle, 0, size, size), this.xPos, this.yPos,null);
    }

    //엔티티가 게임 구역의 그리드 한 칸 위에 정확히 위치했는지 확인하는 메서드
    public boolean onTheGrid() {
        return (xPos%8 == 0 && yPos%8 == 0);
    }

    //엔티티가 게임 구역 안에 있는지 여부를 확인하는 메서드
    public boolean onGameplayWindow() { return !(xPos<=0 || xPos>= GameplayPanel.width || yPos<=0 || yPos>= GameplayPanel.height); }

    public Rectangle getHitbox() {
        return new Rectangle(xPos, yPos, size, size);
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public void setSprite(String spriteName) {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("img/" + spriteName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getSubimage() {
        return subimage;
    }

    public void setSubimage(float subimage) {
        this.subimage = subimage;
    }

    public int getNbSubimagesPerCycle() {
        return nbSubimagesPerCycle;
    }

    public void setNbSubimagesPerCycle(int nbSubimagesPerCycle) {
        this.nbSubimagesPerCycle = nbSubimagesPerCycle;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getxSpd() {
        return xSpd;
    }

    public void setxSpd(int xSpd) {
        this.xSpd = xSpd;
    }

    public int getySpd() {
        return ySpd;
    }

    public void setySpd(int ySpd) {
        this.ySpd = ySpd;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public boolean getInvulnerable() {
        return invulnerable;
    }

    public void setConfuse(boolean confuse) {
        this.confuse = confuse;
    }

    public boolean getConfuse() {
        return confuse;
    }


    // x, y 속도 갱신. map의 cellsize가 8단위 이므로 8에는 무조건 갈 수 있도록 함.
    public void updatexySpd() {
        if (xSpd != 0) {
            if (xSpd > 0) {
                xSpd = this.spd;
                if (xPos % 8 > (xPos + xSpd) % 8)
                    xSpd = xSpd - (xPos + xSpd) % 8;
            }
            else {
                xSpd = -this.spd;
                if (xPos % 8 != 0 && xPos % 8 < (xPos + xSpd) % 8)
                    xSpd = xSpd + 8 - (xPos + xSpd) % 8;
            }
        }
        if (ySpd != 0) {
            if (ySpd > 0) {
                ySpd = this.spd;
                if (yPos % 8 > (yPos + ySpd) % 8)
                    ySpd = ySpd - (yPos + ySpd) % 8;
            }
            else {
                ySpd = -this.spd;
                if (yPos % 8 != 0 && yPos % 8 < (yPos + ySpd) % 8)
                    ySpd = ySpd + 8 - (yPos + ySpd) % 8;
            }
        }
    }
}
