package Engine;

public class Time {

    private static final long NANO_SECOND = 1_000_000_000L;
    private static final float INVERSE_NANO_SECOND = 1e-9f;
    private static final float SMOOTHING_FACTOR = 0.2f;

    private final long initialTime;
    private long previousFrameTime;
    private long currentFrameTime;
    private long deltaFrameTime;

    private float ema;

    public Time() {

        currentFrameTime = System.nanoTime();
        previousFrameTime = currentFrameTime;
        initialTime = currentFrameTime;
        deltaFrameTime = 0L;
        ema = 0;

    }

    public void frame() {
        currentFrameTime = System.nanoTime();
        deltaFrameTime = currentFrameTime - previousFrameTime;
        previousFrameTime = currentFrameTime;
        ema = SMOOTHING_FACTOR * (deltaFrameTime - ema) + ema;
    }

    public long getDeltaFrameTime() {
        return deltaFrameTime;
    }

    public float getDeltaFrameTimeSeconds() {
        return deltaFrameTime * INVERSE_NANO_SECOND;
    }

    public long getElapsedTime() {
        return System.nanoTime() - initialTime;
    }

    public float getElapsedTimeSeconds() {
        return getElapsedTime() * INVERSE_NANO_SECOND;
    }

    public int getFramesPerSecond() {
        return (int) (NANO_SECOND / ema);
    }

    public long getCurrentFrameTime() {
        return currentFrameTime;
    }

}
