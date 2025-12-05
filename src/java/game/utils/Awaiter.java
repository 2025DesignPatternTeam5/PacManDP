package game.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//비동기 지연 실행 유틸 클래스
public class Awaiter {
    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static void delay(float seconds, Runnable callback) {
        long mili = (long)(seconds * 1000);
        scheduler.schedule(callback, mili, TimeUnit.MILLISECONDS);
    }
}