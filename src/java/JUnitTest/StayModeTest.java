package JUnitTest;

import game.entities.ghosts.*;
import game.ghostStates.GhostState;
import game.ghostStates.HouseMode;
import game.ghostStates.StayMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StayModeTest {
    Ghost blinky=new Blinky(0,0);
    Ghost clyde=new Clyde(0,0);
    Ghost inky=new Inky(0,0);
    Ghost pinky=new Pinky(0,0);
    GhostState stayMode = new StayMode(blinky);
    GhostState houseMode = new HouseMode(blinky);

    @Test
    public void testStayMode_lv0() {
        GhostState state = blinky.getState();
        state.lvlGhost(0);
        assertEquals(blinky.getState().getClass(),houseMode.getClass(),"blinky - house");
        state = clyde.getState();
        state.lvlGhost(0);
        assertEquals(clyde.getState().getClass(),stayMode.getClass(),"clyde - stay");
        state = inky.getState();
        state.lvlGhost(0);
        assertEquals(inky.getState().getClass(),stayMode.getClass(),"inky - stay");
        state = pinky.getState();
        state.lvlGhost(0);
        assertEquals(pinky.getState().getClass(),stayMode.getClass(),"pinky - stay");
    }
    @Test
    public void testStayMode_lv1() {
        GhostState state = blinky.getState();
        state.lvlGhost(1);
        assertEquals(blinky.getState().getClass(),houseMode.getClass(),"blinky - house");
        state = clyde.getState();
        state.lvlGhost(1);
        assertEquals(clyde.getState().getClass(),houseMode.getClass(),"clyde - house");
        state = inky.getState();
        state.lvlGhost(1);
        assertEquals(inky.getState().getClass(),stayMode.getClass(),"inky - stay");
        state = pinky.getState();
        state.lvlGhost(1);
        assertEquals(pinky.getState().getClass(),stayMode.getClass(),"pinky - stay");
    }
    @Test
    public void testStayMode_lv2() {
        GhostState state = blinky.getState();
        state.lvlGhost(2);
        assertEquals(blinky.getState().getClass(),houseMode.getClass(),"blinky - house");
        state = clyde.getState();
        state.lvlGhost(2);
        assertEquals(clyde.getState().getClass(),houseMode.getClass(),"clyde - house");
        state = inky.getState();
        state.lvlGhost(2);
        assertEquals(inky.getState().getClass(),houseMode.getClass(),"inky - house");
        state = pinky.getState();
        state.lvlGhost(2);
        assertEquals(pinky.getState().getClass(),stayMode.getClass(),"pinky - stay");
    }
    @Test
    public void testStayMode_lv3() {
        GhostState state = blinky.getState();
        state.lvlGhost(3);
        assertEquals(blinky.getState().getClass(),houseMode.getClass(),"blinky - house");
        state = clyde.getState();
        state.lvlGhost(3);
        assertEquals(clyde.getState().getClass(),houseMode.getClass(),"clyde - house");
        state = inky.getState();
        state.lvlGhost(3);
        assertEquals(inky.getState().getClass(),houseMode.getClass(),"inky - house");
        state = pinky.getState();
        state.lvlGhost(3);
        assertEquals(pinky.getState().getClass(),houseMode.getClass(),"pinky - house");
    }

}
