package JUnitTest;

import game.SoundManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;

import static org.junit.jupiter.api.Assertions.*;

class SoundManagerTest {

    @Test
    public void testLoadAllSoundFiles() {
        SoundManager sm = SoundManager.getInstance();

        for (var s : SoundManager.Sound.values()) {
            Clip clip = sm.getClip(s);
            Assertions.assertNotNull(clip, "Clip should not be null for " + s);
            Assertions.assertTrue(clip.getFrameLength() > 0, "Clip should have valid length for " + s);
        }
    }
}