package ru.yandex.practicum.exception;

public class LoggerException extends Exception {
    public  LoggerException() {

    }

    public LoggerException(String message) {
        super(message);
    }

    public LoggerException(String message, Throwable cause) {
        super(message, cause);
    }
}
