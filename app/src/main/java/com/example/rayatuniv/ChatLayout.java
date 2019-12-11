package com.example.rayatuniv;

public class ChatLayout {
    String from,message;
    long time;

    public ChatLayout(){


    }

    public ChatLayout(String from, String message, long time) {
        this.from = from;
        this.message = message;
        this.time = time;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }
}
