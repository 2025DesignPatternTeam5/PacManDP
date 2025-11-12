package game.utils;

import game.Game;
import game.entities.*;

//두 엔티티 간 충돌을 감지하는 클래스
public class CollisionDetector {
    private Game game;

    public CollisionDetector(Game game) {
        this.game = game;
    }

    //collisionCheck 타입의 엔티티들과 obj 엔티티 간 충돌을 감지한다; 충돌이 발생하면 테스트한 타입의 엔티티를 반환한다
    //collisionCheck 타입의 엔티티는 직사각형 히트박스를 가지며, 여기서는 obj 엔티티의 히트박스를 점(point)으로 간주한다 (팩맨과 유령 간 충돌 시, 어느 정도 여유를 두어 게임이 너무 가혹하지 않게 하기 위함)
    public Entity checkCollision(Entity obj, Class<? extends Entity> collisionCheck) {
        for (Entity e : game.getEntities()) {
            if (!e.isDestroyed() && collisionCheck.isInstance(e) && e.getHitbox().contains(obj.getxPos() + obj.getSize() / 2, obj.getyPos() + obj.getSize() / 2)) return e;
        }
        return null;
    }

    //이전 메서드와 동일하지만, 모든 히트박스는 직사각형으로 간주된다
    public Entity checkCollisionRect(Entity obj, Class<? extends Entity> collisionCheck) {
        for (Entity e : game.getEntities()) {
            if (!e.isDestroyed() && collisionCheck.isInstance(e) && e.getHitbox().intersects(obj.getHitbox())) return e;
        }
        return null;
    }
}
