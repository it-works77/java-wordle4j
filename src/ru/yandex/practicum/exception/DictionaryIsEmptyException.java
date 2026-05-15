package ru.yandex.practicum.exception;

public class DictionaryIsEmptyException extends Exception {
    public DictionaryIsEmptyException() {
    }

    public DictionaryIsEmptyException(String message) {
        super(message);
    }
}
