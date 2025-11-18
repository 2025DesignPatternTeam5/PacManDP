package game;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;

import static org.junit.jupiter.api.Assertions.*;

class SoundManagerTest {

    @Test
    public void testLoadAllSoundFiles() {
        SoundManager sm = SoundManager.getInstance();

        for (var s : SoundManager.Sound.values()) {
            Clip clip = sm.getClip(s);
            assertNotNull(clip, "Clip should not be null for " + s);
            assertTrue(clip.getFrameLength() > 0, "Clip should have valid length for " + s);
        }
    }
}