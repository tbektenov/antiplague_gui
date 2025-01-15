package model.timer;

public class TimerModel
        implements Runnable {

    private boolean running = true;

    private int timePassed;
    private final Runnable onRun;
    private final Runnable onFinish;

    public TimerModel(Runnable onRun, Runnable onFinish) {
        this.timePassed = 0;
        this.onRun = onRun;
        this.onFinish = onFinish;
    }

    public synchronized int getTimePassed() {
        return this.timePassed;
    }

    @Override
    public void run() {
        while (timePassed < 300) {
            synchronized (this) {
                timePassed++;
            }
            onRun.run();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Thread interrupted", e);
            }
        }

        onFinish.run();
    }

    public void stop() {
        running = false;
    }
}
