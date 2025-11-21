package JUnitTest;

import game.entities.items.*;
import game.itemFactory.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemFactoryTest {

    @Test
    @DisplayName("CherryFactory는 Cherry 객체를 생성하고, 점수는 200점이어야 한다")
    void testCherryFactory() {
        AbstractItemFactory factory = new CherryFactory();
        int x = 100;
        int y = 200;

        Item item = factory.makeItem(x, y);
        assertInstanceOf(Cherry.class, item, "생성된 객체는 Cherry 타입이어야 합니다.");
        assertEquals(200, item.getPoint(), "Cherry의 점수는 200점이어야 합니다.");
        assertEquals(x, item.getxPos(),  "X 좌표가 일치해야 합니다.");
        assertEquals(y, item.getyPos(), "Y 좌표가 일치해야 합니다.");
        assertNotNull(item.getImage(), "이미지 로드 실패");
        item.destroy();
        assertEquals(-32, item.getxPos(), "X 좌표가 일치해야 합니다.");
        assertEquals(-32, item.getyPos(), "Y 좌표가 일치해야 합니다.");

    }

    @Test
    @DisplayName("AppleFactory는 Apple 객체를 생성하고, 점수는 500점이어야 한다")
    void testAppleFactory() {
        AbstractItemFactory factory = new AppleFactory();
        int x = 100;
        int y = 200;

        Item item = factory.makeItem(x, y);
        assertInstanceOf(Apple.class, item, "생성된 객체는 Apple 타입이어야 합니다.");
        assertEquals(500, item.getPoint(), "Apple의 점수는 500점이어야 합니다.");        assertNotNull(item.getImage(), "이미지 로드 실패");
        assertEquals(x, item.getxPos(), "X 좌표가 일치해야 합니다.");
        assertEquals(y, item.getyPos(), "Y 좌표가 일치해야 합니다.");
        item.destroy();
        assertEquals(-32, item.getxPos(), "X 좌표가 일치해야 합니다.");
        assertEquals(-32, item.getyPos(), "Y 좌표가 일치해야 합니다.");
    }

    @Test
    @DisplayName("WatermelonFactory는 Watermelon 객체를 생성하고, 점수는 1000점이어야 한다")
    void testWatermelonFactory() {
        AbstractItemFactory factory = new WatermelonFactory();
        int x = 100;
        int y = 200;

        Item item = factory.makeItem(x, y);
        assertInstanceOf(Watermelon.class, item, "생성된 객체는 Watermelon 타입이어야 합니다.");
        assertEquals(1000, item.getPoint(), "Watermelon의 점수는 1000점이어야 합니다.");
        assertEquals(x, item.getxPos(), "X 좌표가 일치해야 합니다.");
        assertEquals(y, item.getyPos(), "Y 좌표가 일치해야 합니다.");
        item.destroy();
        assertEquals(-32, item.getxPos(), "X 좌표가 일치해야 합니다.");
        assertEquals(-32, item.getyPos(), "Y 좌표가 일치해야 합니다.");
    }

    @Test
    @DisplayName("PhantomFactory는 Phantom 객체를 생성하고, 점수는 0점이어야 한다")
    void testPhantomFactory() {
        AbstractItemFactory factory = new PhantomFactory();
        int x = 100;
        int y = 200;

        Item item = factory.makeItem(x, y);
        assertInstanceOf(Phantom.class, item, "생성된 객체는 Phantom 타입이어야 합니다.");
        assertEquals(0, item.getPoint(), "Phantom의 점수는 0점이어야 합니다.");
        assertEquals(x, item.getxPos(), "X 좌표가 일치해야 합니다.");
        assertEquals(y, item.getyPos(), "Y 좌표가 일치해야 합니다.");
        item.destroy();
        assertEquals(-32, item.getxPos(), "X 좌표가 일치해야 합니다.");
        assertEquals(-32, item.getyPos(), "Y 좌표가 일치해야 합니다.");
    }

    @Test
    @DisplayName("SpeedUpFactory는 SpeedUp 객체를 생성하고, 점수는 0점이어야 한다")
    void testSpeedUpFactory() {
        AbstractItemFactory factory = new SpeedUpFactory();
        int x = 100;
        int y = 200;

        Item item = factory.makeItem(x, y);
        assertInstanceOf(SpeedUp.class, item, "생성된 객체는 SpeedUp 타입이어야 합니다.");
        assertEquals(0, item.getPoint(), "SpeedUp의 점수는 0점이어야 합니다.");
        assertEquals(x, item.getxPos(), "X 좌표가 일치해야 합니다.");
        assertEquals(y, item.getyPos(), "Y 좌표가 일치해야 합니다.");
        item.destroy();
        assertEquals(-32, item.getxPos(), "X 좌표가 일치해야 합니다.");
        assertEquals(-32, item.getyPos(), "Y 좌표가 일치해야 합니다.");
    }

}