package core.tools;

public final class TimeStamp {

    private long start;

    private long interval;

    public TimeStamp() {
        start = System.currentTimeMillis();
        interval = start;
    }

    public long interval() {
        return interval(true, "");
    }

    public long interval(boolean debug) {
        return interval(debug, "");
    }

    public long interval(boolean debug, String info) {
        long current = System.currentTimeMillis();
        long difference = current - interval;
        if (debug || difference > 100) {

        }
        interval = current;
        return difference;
    }

    public long duration(boolean debug, String info) {
        long current = System.currentTimeMillis();
        long difference = current - start;
        if (debug) {

        }
        return difference;
    }

}
