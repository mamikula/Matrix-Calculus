public final class OperationTimer {
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public long getElapsedTime() {
        return endTime - startTime;
    }

    // get elasped time in seconds
    public double getElapsedTimeInSeconds() {
        return (endTime - startTime) / 1_000_000_000.0;
    }
}
