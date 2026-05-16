package ru.yandex.practicum.exception.wordchecks;

public class WordCheckException extends Throwable {
    public WordCheckException() {
    }

    public WordCheckException(String message) {
        super(message);
    }
}
