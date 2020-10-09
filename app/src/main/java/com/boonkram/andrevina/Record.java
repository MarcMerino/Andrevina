package com.boonkram.andrevina;

public class Record {
    private String name;
    private int attempts;
    private String time = "00:00";

    public Record(String name, int attempts, String time) {
        this.setName(name);
        this.setAttempts(attempts);
        this.setTime(time);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
