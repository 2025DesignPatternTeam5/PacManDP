package game.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//유틸 클래스
public class Utils {
    private static Map<Integer, Double> directionConverterMap = new HashMap<>();

    static {
        directionConverterMap.put(0, 0d);
        directionConverterMap.put(1, Math.PI);
        directionConverterMap.put(2, Math.PI / 2);
        directionConverterMap.put(3, Math.PI * (3/2));
    }

    //두 점 사이의 거리를 구하는 함수
    public static double getDistance(double xA, double yA, double xB, double yB) {
        return Math.sqrt( Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2) );
    }

    //두 점 사이가 이루는 각도를 구하는 함수
    public static double getDirection(double xA, double yA, double xB, double yB) {
        return Math.atan2((yB - yA), (xB - xA));
    }

    //주어진 시작점에서 특정 각도와 거리를 적용하여 새로운 좌표를 계산하는 함수
    public static int[] getPointDistanceDirection(int x, int y, double distance, double direction) {
        int[] point = new int[2];
        point[0] = x + (int)(Math.cos(direction) * distance);
        point[1] = y + (int)(Math.sin(direction) * distance);
        return point;
    }

    //위에서 생성한 맵을 이용하여, 엔티티의 '방향'을 라디안 단위의 각도로 변환하는 함수
    public static double directionConverter(int spriteDirection) {
        return directionConverterMap.get(spriteDirection);
    }

    //0이상 n미만의 정수를 생성하는 함수
    public static int randomInt(int n) {
        Random r = new Random();
        return r.nextInt(n);
    }

    //x와 y를 포함한 범위 내에서 정수를 생성하는 함수
    public static int randomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt(max-min) + min;
    }

    //임의의 불리언 값을 반환하는 함수
    public static boolean randomBool() {
        return (randomInt(1) == 1);
    }
}
