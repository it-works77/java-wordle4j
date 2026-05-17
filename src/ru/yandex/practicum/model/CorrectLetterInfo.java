package ru.yandex.practicum.model;

public class CorrectLetterInfo {
    private final Character letter;
    private final Integer position;

    public CorrectLetterInfo(Character letter, Integer position) {
        this.letter = letter;
        this.position = position;
    }

    public Character getLetter() {
        return letter;
    }

    public Integer getPosition() {
        return position;
    }
}
