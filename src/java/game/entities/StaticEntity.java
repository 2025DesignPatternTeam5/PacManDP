package game.entities;

import java.awt.*;

//움직이지 않는 엔티티를 설명하기 위한 추상 클래스
public abstract class StaticEntity extends Entity {

    protected Rectangle hitbox;

    public StaticEntity(int size, int xPos, int yPos) {
        super(size, xPos, yPos);
        this.hitbox = new Rectangle(xPos, yPos, size, size); //히트박스는 엔티티 생성 시 한 번만 정의된다
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
