package bloramcboxingclient.lystech.org.utils;

public class MSTimer {

    private long lastMS = 0;

    public boolean hasTimePassed(long time) {
        return System.currentTimeMillis() - lastMS >= time;
    }

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public long getTime() {
        return System.currentTimeMillis() - lastMS;
    }
}