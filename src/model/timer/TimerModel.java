package model.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class TimerModel {
    private final Timer timer;
    private int timeRemaining;
    private final Consumer<Integer> onTick;
    private final Runnable onFinish;

    public TimerModel(int durationInSeconds, Consumer<Integer> onTick, Runnable onFinish) {
        this.timeRemaining = durationInSeconds;
        this.timer = new Timer(true);
        this.onTick = onTick;
        this.onFinish = onFinish;
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    onTick.accept(timeRemaining);
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
