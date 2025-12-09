package JUnitTest;

import game.entities.Pacman;
import game.entities.ghosts.Ghost;
import game.entities.items.*;
import game.ghostFactory.AbstractGhostFactory;
import game.ghostFactory.BlinkyFactory;
import game.itemFactory.*;
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
    @DisplayName("속도 증가 아이템을 먹으면 효과가 적용되고, 지속시간이 지나면 사라져야 한다")
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
    @DisplayName("속도 감소 아이템을 먹으면 효과가 적용되고, 지속시간이 지나면 사라져야 한다")
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


    @Test
    @DisplayName("speed 아이템 복합 테스트")
    void testSpeedEffectTimerReset() {
        // speed up, speed down 둘다 적용
        int speedDownDuration = 2;
        int speedUpDuration = 5;
        int boostAmount = 1;
        int initialSpeed = pacman.getSpd(); // 기본 속도 저장
        AbstractItemFactory speedUpfactory = new SpeedUpFactory();
        AbstractItemFactory speedDownfactory = new BananaFactory();

        Item speedDownitem = speedDownfactory.makeItem(40, 40);
        EffectCommand speedDown = ((EffectItem)speedDownitem).getEffectCommand();

        Item speedUpitem = speedUpfactory.makeItem(32, 32);
        EffectCommand speedUp = ((EffectItem)speedUpitem).getEffectCommand();

        // When: 효과 적용 (아이템 획득 시뮬레이션)
        pacman.addEffect(speedUp);
        assertEquals(initialSpeed + boostAmount, pacman.getSpd(), "속도가 즉시 증가해야 합니다.");
        assertEquals(pacman.findEffectClass(speedUp).getClass(), speedUp.getClass(), "활성화된 효과 리스트에 추가되어야 합니다.");

        pacman.addEffect(speedDown);
        assertEquals(initialSpeed, pacman.getSpd(), "슬로우 아이템을 먹으므로써 속도가 원상 복귀 되어야합니다.");
        assertEquals(pacman.findEffectClass(speedDown).getClass(), speedDown.getClass(), "활성화된 효과 리스트에 추가되어야 합니다.");


        // 여전히 효과 유지
        for (int i = 0; i < speedDownDuration * 60 - 1; i++) {
            pacman.updateEffect();
        }
        assertEquals(initialSpeed, pacman.getSpd(), "지속시간 내에는 속도가 유지되어야 합니다.");

        // 속도 감소 효과 제거
        pacman.updateEffect();
        assertEquals(initialSpeed + boostAmount, pacman.getSpd(), "speedDown이 종료되어 다시 속도가 증가해야 합니다.");
        assertNull(pacman.findEffectClass(speedDown), "활성화된 효과 리스트에 제거되어야 합니다.");


        pacman.removeEffectAll();
        assertEquals(initialSpeed, pacman.getSpd(), "효과 제거 -> 속도 다시 원상복귀해야 합니다.");
        assertNull(pacman.findEffectClass(speedDown), "활성화된 효과 리스트에 제거되어야 합니다.");
    }



    @Test
    @DisplayName("쉴드 아이템을 먹으면 효과가 적용되고, 지속시간이 지나면 사라져야 한다")
    void testShieldEffectLifecycle() {
        // 5초, 무적 아이템
        int duration = 5;
        AbstractItemFactory itemFactory = new ShieldFactory();
        Item item = itemFactory.makeItem(40, 40);
        EffectCommand shieldEffect = ((EffectItem)item).getEffectCommand();

        AbstractGhostFactory ghostFactory = new BlinkyFactory();
        Ghost ghost = ghostFactory.makeGhost(40, 40);

        // When: 효과 적용 (아이템 획득 시뮬레이션)
        pacman.addEffect(shieldEffect);

        // Then  즉시 효과가 적용되었는지 확인
        assertTrue(pacman.getInvulnerable(), "무적이어야합니다.");
        assertEquals(pacman.findEffectClass(shieldEffect).getClass(), shieldEffect.getClass(), "활성화된 효과 리스트에 추가되어야 합니다.");

        // 여전히 효과 유지
        for (int i = 0; i < duration * 60 - 1; i++) {
            pacman.updateEffect();
        }
        assertTrue(pacman.getInvulnerable(), "무적이어야합니다.");

        // 효과 제거
        pacman.updateEffect();
        assertFalse(pacman.getInvulnerable(), "무적이 아니어야 합니다.");
        assertNull(pacman.findEffectClass(shieldEffect), "활성화된 효과 리스트에 제거되어야 합니다.");

        shieldEffect = ((EffectItem)item).getEffectCommand();
        pacman.addEffect(shieldEffect);
        for (int i = 0; i < duration * 60 - 10; i++) {
            pacman.updateEffect();
        }
        item = itemFactory.makeItem(40, 40);
        shieldEffect = ((EffectItem)item).getEffectCommand();
        pacman.addEffect(shieldEffect);
        assertEquals(0, pacman.findEffectClass(shieldEffect).getTimer(), "지속시간이 초기화 되어야함.");
    }



    @Test
    @DisplayName("Demon 아이템을 먹으면 효과가 적용되고, 지속시간이 지나면 사라져야 한다")
    void testConfuseEffectLifecycle() {
        // 5초, 방향 반대
        int duration = 5;
        AbstractItemFactory itemFactory = new DemonFactory();
        Item item = itemFactory.makeItem(40, 40);
        EffectCommand confuseEffect = ((EffectItem)item).getEffectCommand();

        // When: 효과 적용 (아이템 획득 시뮬레이션)
        pacman.addEffect(confuseEffect);

        // Then  즉시 효과가 적용되었는지 확인
        assertTrue(pacman.getConfuse(), "confuse 상태이어야합니다.");
        assertEquals(pacman.findEffectClass(confuseEffect).getClass(), confuseEffect.getClass(), "활성화된 효과 리스트에 추가되어야 합니다.");

        // 여전히 효과 유지
        for (int i = 0; i < duration * 60 - 1; i++) {
            pacman.updateEffect();
        }
        assertTrue(pacman.getConfuse(), "confuse 상태이어야합니다.");

        // 효과 제거
        pacman.updateEffect();
        assertFalse(pacman.getConfuse(), "confuse가 끝났어야 합니다.");
        assertNull(pacman.findEffectClass(confuseEffect), "활성화된 효과 리스트에 제거되어야 합니다.");

        confuseEffect = ((EffectItem)item).getEffectCommand();
        pacman.addEffect(confuseEffect);
        for (int i = 0; i < duration * 60 - 10; i++) {
            pacman.updateEffect();
        }
        item = itemFactory.makeItem(40, 40);
        confuseEffect = ((EffectItem)item).getEffectCommand();
        pacman.addEffect(confuseEffect);
        assertEquals(0, pacman.findEffectClass(confuseEffect).getTimer(), "지속시간이 초기화 되어야함.");
    }
}