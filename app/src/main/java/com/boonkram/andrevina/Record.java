package com.boonkram.andrevina;

public class Record {
    private String name;
    private int attempts;
    //private String time = "00:00";

    public Record(String name, int attempts) {
        this.setName(name);
        this.setAttempts(attempts);
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
}
