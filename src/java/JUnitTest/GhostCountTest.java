package JUnitTest;

import game.entities.ghosts.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GhostCountTest {
    ArrayList<Ghost> ghosts;

    @BeforeEach
    void init() {
        ghosts = new ArrayList<>();
        ghosts.add(new Blinky(0,0));
        ghosts.add(new Clyde(0,0));
        ghosts.add(new Inky(0,0));
        ghosts.add(new Pinky(0,0));
        ghosts.forEach(g -> g.getState().lvlGhost(3));
    }

    @Test
    void InitCounterTest() {
        assertEquals(0, Ghost.getFrightenedCnt(), "FrightenedCnt의 초기값은 0이어야 함.");
        assertEquals(0, Ghost.getEatenCnt(), "EatenCnt의 초기값은 0이어야 함.");
    }

    @Test
    void NormalToFrightenedTest() {
        for(int i = 0; i < 4; i++) {
            init();
            for(int j = 0; j <= i; j++) {
                ghosts.get(j).switchFrightenedMode();
            }
            assertEquals(i + 1, Ghost.getFrightenedCnt(), "공포 모드로 변경 시, 변경된 유령 수만큼 FrightenedCnt가 증가해야 함.");
        }
    }

    @Test
    void FrightenedToNormalTest() {
         for(int i = 0; i < 4; i++) {
            for(int j = 0; j <= i; j++) {
                ghosts.get(j).switchFrightenedMode();
            }

            for(Ghost g : ghosts) {
                g.switchChaseModeOrScatterMode();
            }
            assertEquals(0, Ghost.getFrightenedCnt(), "모두 일반 모드로 돌아왔다면, FrightenedCnt는 0이어야 함.");
        }
    }

    @Test
    void FrightenedToEatenTest() {
         for(int i = 0; i < 4; i++) {
            init();
            for(Ghost g : ghosts) {
                g.switchFrightenedMode();
            }

            for(int j = 0; j <= i; j++) {
                ghosts.get(j).switchEatenMode();
            }
            assertEquals(i + 1, Ghost.getEatenCnt(), "먹힌 유령 개수만큼 eatenCnt가 증가해야 함.");
        }
    }

    @Test
    void EatenToFrightenedTest() { //먹힌 유령이 house로 돌아갔지만 아직 FrightenedTimer시간이 남은 상황
        for(int i = 0; i < 4; i++) {
            init();
            for(Ghost g : ghosts) {
                g.switchFrightenedMode();
            }

            for(int j = 0; j <= i; j++) {
                ghosts.get(j).switchEatenMode();
                ghosts.get(j).switchHouseMode();
                ghosts.get(j).switchChaseModeOrScatterMode();
            }
            assertEquals(0, Ghost.getEatenCnt(), "먹힌 유령이 다 house로 복귀했다면, eatenCnt는 0 이어야 함.");
            assertEquals(3 - i, Ghost.getFrightenedCnt(), "먹힌 유령이 아닌 유령들의 개수만큼 FrightenedCnt가 존재해야 함.");
        }
    }

    @Test
    void EatenToNormalTest() { //먹힌 유령이 house로 돌아갔는데 이미 FrightenedTimer가 끝난 상황
         for(int i = 0; i < 4; i++) {
            init();
            for(Ghost g : ghosts) {
                g.switchFrightenedMode();
            }

            for(int j = 0; j <= i; j++) {
                ghosts.get(j).switchEatenMode();
                ghosts.get(j).switchHouseMode();
                ghosts.get(j).switchChaseModeOrScatterMode();
            }

            for(int j = i + 1; j < 4; j++) {
                ghosts.get(j).switchChaseModeOrScatterMode();
            }

            assertEquals(0, Ghost.getEatenCnt(), "먹힌 유령이 다 house로 복귀했다면, eatenCnt는 0이어야 함.");
            assertEquals(0, Ghost.getFrightenedCnt(), "공포 타이머가 이미 끝났으므로 FrightenedCnt가 0이어야 함.");
        }
    }
}
