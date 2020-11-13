package com.boonkram.andrevina;

import android.net.Uri;

public class Record {
    private String name;
    private int attempts;
    //private String time = "00:00";
    private Uri fileUri;

    public Record(String name, int attempts, Uri fileUri) {
        this.setName(name);
        this.setAttempts(attempts);
        this.setFileUri(fileUri);
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

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }
}
