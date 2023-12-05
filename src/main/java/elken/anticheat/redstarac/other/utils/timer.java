package elken.anticheat.redstarac.other.utils;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class timer extends TimerTask {
    @Override
public void run() {
    int value = 1;
    System.out.println("The duration of sending the mail will took: " + value);
    try {
        TimeUnit.SECONDS.sleep(value);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
}
}