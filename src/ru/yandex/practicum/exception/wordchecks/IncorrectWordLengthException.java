package ru.yandex.practicum.exception.wordchecks;

public class IncorrectWordLengthException extends WordCheckException {
    public IncorrectWordLengthException() {
    }

    public IncorrectWordLengthException(String message) {
        super(message);
    }
}
