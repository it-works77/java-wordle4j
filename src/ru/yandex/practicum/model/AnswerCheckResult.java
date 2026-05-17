package ru.yandex.practicum.model;

import ru.yandex.practicum.config.WordleConfig;

import java.util.ArrayList;
import java.util.HashSet;

public class AnswerCheckResult {
    private final String guess;
    private final String correctWord;
    private final Boolean isMatched;
    private final ArrayList<CharStatus> charStatuses;
    private final ArrayList<CorrectLetterInfo> correctLetters;
    private final HashSet<Character> wrongPositionLetters;
    private final HashSet<Character> absentLetters;

    public String getGuess() {
        return guess;
    }

    public Boolean isMatched() {
        return isMatched;
    }

    public ArrayList<CharStatus> getCharStatuses() {
        return charStatuses;
    }

    public ArrayList<CorrectLetterInfo> getCorrectLetters() {
        return correctLetters;
    }

    public HashSet<Character> getWrongPositionLetters() {
        return wrongPositionLetters;
    }

    public HashSet<Character> getAbsentLetters() {
        return absentLetters;
    }

    public AnswerCheckResult(String guess, String correctWord) {
        this.guess = guess;
        this.correctWord = correctWord;
        charStatuses = new ArrayList<>(WordleConfig.GAME_WORD_LENGTH);

        correctLetters = new ArrayList<>();
        wrongPositionLetters = new HashSet<>();
        absentLetters = new HashSet<>();

        if (guess.equals(correctWord)) {
            isMatched = true;
            for (int i = 0; i < WordleConfig.GAME_WORD_LENGTH; i++) {
                // TODO можно как-то инициировать по-другому?
                charStatuses.add(CharStatus.CORRECT);
                correctLetters.add(new CorrectLetterInfo(guess.charAt(i), i));
            }
        } else {
            isMatched = false;
            if (guess.length() != correctWord.length()) {
                throw new IllegalArgumentException("Длина загаданного слова и попытки пользователя не совпадают");
            }

            for (int i = 0; i < guess.length(); i++) {
                if (guess.charAt(i) == correctWord.charAt(i)) {
                    charStatuses.add(CharStatus.CORRECT);
                    correctLetters.add(new CorrectLetterInfo(guess.charAt(i), i));

                } else if (correctWord.indexOf(guess.charAt(i)) == -1) {
                    charStatuses.add(CharStatus.ABSENT);
                    absentLetters.add(guess.charAt(i));

                } else {
                    charStatuses.add(CharStatus.WRONG_POSITION);
                    wrongPositionLetters.add(guess.charAt(i));
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CharStatus s : charStatuses) {
            if (s == CharStatus.CORRECT) {
                sb.append("+");
            } else if (s == CharStatus.WRONG_POSITION) {
                sb.append("^");
            } else if (s == CharStatus.ABSENT) {
                sb.append("-");
            } else {
                // TODO Надо ли так?
                throw new RuntimeException("Неверный статус");
            }
        }
        return sb.toString();
    }
}
