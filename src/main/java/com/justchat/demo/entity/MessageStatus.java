package com.justchat.demo.entity;

public enum MessageStatus {
    privateMessage,publicMessage;

    @Override
    public String toString() {
        return name();
    }
}
