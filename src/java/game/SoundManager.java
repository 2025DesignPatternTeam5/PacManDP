package game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    public enum Sound {
        START,
        FAIL,
        PAC_EAT,
        PAC_FRUIT,
        PAC_DOT,
        PAC_EXTRA,
        GHOST_NORMAL,
        GHOST_EATEN,
        GHOST_FRIGHTENED,
    }

    private final static SoundManager instance = new SoundManager();

    private final Map<Sound, Clip> soundMap = new HashMap<Sound, Clip>();

    private SoundManager() {
        loadAllSoundFiles();
    }

    public static SoundManager getInstance() { return instance; }

    //특정 사운드 재생
    public void play(Sound sound) {
        stop(sound);
        soundMap.get(sound).start();
    }

    //특정 사운드 반복 재생
    public void playLoop(Sound sound) {
        soundMap.get(sound).loop(20);
    }

    //특정 사운드 정지하고 시작지점으로 되돌림
    public void stop(Sound sound) {
        Clip clip = soundMap.get(sound);
        clip.stop();
        clip.setFramePosition(0);
    }

    public void stopAllSound() {
        for(Sound sound : soundMap.keySet()) {
            stop(sound);
        }
    }

    public float getClipLength(Sound sound) {
        long micro = soundMap.get(sound).getMicrosecondLength();
        float second = (float) (micro / 1000000.0f);
        return second;
    }

    //Test를 위한 Getter함수
    public Clip getClip(Sound sound) {
        return soundMap.get(sound);
    }

    //모든 사운드 파일을 보관
    private void loadAllSoundFiles() {
        for (Sound sound : Sound.values()) {
            loadClip(sound);
        }
    }

    //특정 사운드 파일을 폴더에서 읽어서 Map에 보관
    private void loadClip(Sound type) {
        String filename = type.name().toLowerCase();

        try {
            URL url = getClass().getClassLoader().getResource("sound/" + filename + ".wav");
            if (url == null) {
                throw new RuntimeException("Sound file not found: " + filename);
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            soundMap.put(type, clip);//Map에 <오디오 type, clip>을 등록
            //System.out.println("Sound loaded: " + filename);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
