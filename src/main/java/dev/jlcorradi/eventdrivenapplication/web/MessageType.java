package dev.jlcorradi.eventdrivenapplication.web;

import lombok.Getter;

@Getter
public enum MessageType {
    SUCCESS("x-message-success"),
    INFO("x-message-info"),
    ERROR("x-message-error"),
    WARN("x-message-warning");

    private final String header;

    MessageType(String header) {
        this.header = header;
    }
}