package com.buddy.wakemate;


public class Alarm {
    private int id;
    private long timeInMillis;
    private String tone;
    private boolean isEnabled;

    public Alarm(int id, long timeInMillis, String tone, boolean isEnabled) {
        this.id = id;
        this.timeInMillis = timeInMillis;
        this.tone = tone;
        this.isEnabled = isEnabled;
    }

    // Getters and setters
    public int getId() { return id; }
    public long getTimeInMillis() { return timeInMillis; }
    public String getTone() { return tone; }
    public boolean isEnabled() { return isEnabled; }
    public void setEnabled(boolean enabled) { isEnabled = enabled; }
}
