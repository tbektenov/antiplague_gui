package model.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class TimerModel {
    private final Timer timer;
    private int seconds;
    private final Consumer<Integer> onTick;
    private final Runnable onFinish;

    public TimerModel(int seconds, Consumer<Integer> onTick, Runnable onFinish) {
        this.seconds = seconds;
        this.timer = new Timer(true);
        this.onTick = onTick;
        this.onFinish = onFinish;
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (seconds > 0) {
                    seconds--;
                    onTick.accept(seconds);
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
}
