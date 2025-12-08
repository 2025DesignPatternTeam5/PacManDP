package JUnitTest;

import game.entities.Pacman;
import game.entities.items.Banana;
import game.entities.items.EffectItem;
import game.entities.items.Item;
import game.itemFactory.AbstractItemFactory;
import game.itemFactory.BananaFactory;
import game.itemFactory.SpeedUpFactory;
import game.pacmanEffect.EffectCommand;
import game.pacmanEffect.SpeedUpEffect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacmanEffectTest {

    private Pacman pacman;

    @BeforeEach
    void setUp() {
        // 테스트할 때마다 새로운 팩맨 생성 (초기 속도 2 가정)
        pacman = new Pacman(100, 100);
    }

    @Test
    @DisplayName("아이템을 먹으면 효과가 적용되고, 지속시간이 지나면 사라져야 한다")
    void testSpeedUpEffectLifecycle() {
        // 5초, 속도 +1 증가 아이템
        int duration = 5;
        int boostAmount = 1;
        int initialSpeed = pacman.getSpd(); // 기본 속도 저장
        AbstractItemFactory itemFactory = new SpeedUpFactory();
        Item item = itemFactory.makeItem(40, 40);
        EffectCommand speedUp = ((EffectItem)item).getEffectCommand();

        // When: 효과 적용 (아이템 획득 시뮬레이션)
        pacman.addEffect(speedUp);

        // Then  즉시 효과가 적용되었는지 확인
        assertEquals(initialSpeed + boostAmount, pacman.getSpd(), "속도가 즉시 증가해야 합니다.");
        assertEquals(pacman.findEffectClass(speedUp).getClass(), speedUp.getClass(), "활성화된 효과 리스트에 추가되어야 합니다.");

        // 여전히 효과 유지
        for (int i = 0; i < duration * 60 - 1; i++) {
            pacman.updateEffect();
        }
        assertEquals(initialSpeed + boostAmount, pacman.getSpd(), "지속시간 내에는 속도가 유지되어야 합니다.");

        // 효과 제거
        pacman.updateEffect();
        assertEquals(initialSpeed, pacman.getSpd(), "지속시간이 지나면 원래 속도로 돌아와야 합니다.");
        assertNull(pacman.findEffectClass(speedUp), "활성화된 효과 리스트에 제거되어야 합니다.");

        speedUp = ((EffectItem)item).getEffectCommand();
        pacman.addEffect(speedUp);
        for (int i = 0; i < duration * 60 - 10; i++) {
            pacman.updateEffect();
        }
        item = itemFactory.makeItem(40, 40);
        speedUp = ((EffectItem)item).getEffectCommand();
        pacman.addEffect(speedUp);
        assertEquals(0, pacman.findEffectClass(speedUp).getTimer(), "지속시간이 초기화 되어야함.");
    }


    @Test
    @DisplayName("아이템을 먹으면 효과가 적용되고, 지속시간이 지나면 사라져야 한다")
    void testSpeedDownEffectTimerReset() {
        // 2초, 속도 -1 증가 아이템
        int duration = 2;
        int boostAmount = -1;
        int initialSpeed = pacman.getSpd(); // 기본 속도 저장
        AbstractItemFactory itemFactory = new BananaFactory();
        Item item = itemFactory.makeItem(40, 40);
        EffectCommand speedDown = ((EffectItem)item).getEffectCommand();

        // When: 효과 적용 (아이템 획득 시뮬레이션)
        pacman.addEffect(speedDown);

        // Then  즉시 효과가 적용되었는지 확인
        assertEquals(initialSpeed + boostAmount, pacman.getSpd(), "속도가 즉시 증가해야 합니다.");
        assertEquals(pacman.findEffectClass(speedDown).getClass(), speedDown.getClass(), "활성화된 효과 리스트에 추가되어야 합니다.");

        // 여전히 효과 유지
        for (int i = 0; i < duration * 60 - 1; i++) {
            pacman.updateEffect();
        }
        assertEquals(initialSpeed + boostAmount, pacman.getSpd(), "지속시간 내에는 속도가 유지되어야 합니다.");

        // 효과 제거
        pacman.updateEffect();
        assertEquals(initialSpeed, pacman.getSpd(), "지속시간이 지나면 원래 속도로 돌아와야 합니다.");
        assertNull(pacman.findEffectClass(speedDown), "활성화된 효과 리스트에 제거되어야 합니다.");


        item = itemFactory.makeItem(40, 40);
        speedDown = ((EffectItem)item).getEffectCommand();
        pacman.addEffect(speedDown);
        assertEquals(0, pacman.findEffectClass(speedDown).getTimer(), "지속시간이 초기화 되어야함.");
    }
}