package game.utils;

import game.Game;
import game.entities.Entity;
import game.entities.GhostHouse;
import game.entities.Wall;

import java.awt.*;

//엔티티와 벽 사이의 충돌을 감지하는 클래스 (CollisionDetector 클래스와 달리, 벽은 고정되어 있음)
public class WallCollisionDetector {

    //엔티티 위치에서 일정 거리(delta)만큼 떨어진 곳에 벽이 있는지 확인하는 함수 (이 delta는 실제로 벽에 부딪히기 전에 미리 감지하기 위해 사용됨)
    public static boolean checkWallCollision(Entity obj, int dx, int dy) {
        Rectangle r = new Rectangle(obj.getxPos() + dx, obj.getyPos() + dy, obj.getSize(), obj.getSize());
        for (Wall w : Game.getWalls()) {
            if (w.getHitbox().intersects(r)) return true;
        }
        return false;
    }

    //이전 메서드와 동일하지만, 여기서는 유령의 집 벽과의 충돌은 무시할 수 있다
    public static boolean checkWallCollision(Entity obj, int dx, int dy, boolean ignoreGhostHouses) {
        Rectangle r = new Rectangle(obj.getxPos() + dx, obj.getyPos() + dy, obj.getSize(), obj.getSize());
        for (Wall w : Game.getWalls()) {
            if (!(ignoreGhostHouses && w instanceof GhostHouse) && w.getHitbox().intersects(r)) return true;
        }
        return false;
    }
}
