package model.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerModel {
    private final Timer timer;
    private int seconds;

    public TimerModel(int seconds) {
        this.seconds = seconds;
        this.timer = new Timer(true);
    }

    public void start(Runnable onFinish) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (seconds > 0) {
                    seconds--;
                } else {
                    stop();
                    onFinish.run();
                }
            }
        }, 0, 1000);
    }

    public void stop() {
        timer.cancel();
    }

    public int getTimeRemaining() {
        return seconds;
    }
}
