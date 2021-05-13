package com.hung.entity;

/**
 * @author Hung
 */
public class LeavingMessage {
    String name;
    String message;

    public LeavingMessage() {
    }

    public LeavingMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"message\":\"" + message + '\"' +
                '}';
    }
}
