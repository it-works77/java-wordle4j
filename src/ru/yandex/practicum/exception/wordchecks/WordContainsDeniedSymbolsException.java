package ru.yandex.practicum.exception.wordchecks;

public class WordContainsDeniedSymbolsException extends WordCheckException {
    public WordContainsDeniedSymbolsException() {
    }

    public WordContainsDeniedSymbolsException(String message) {
        super(message);
    }
}

