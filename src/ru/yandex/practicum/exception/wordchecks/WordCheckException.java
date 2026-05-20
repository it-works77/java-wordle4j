package ru.yandex.practicum.exception.wordchecks;

public class WordCheckException extends Exception {
    public WordCheckException() {
    }

    public WordCheckException(String message) {
        super(message);
    }
}
