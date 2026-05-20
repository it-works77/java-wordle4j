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
                charStatuses.add(CharStatus.CORRECT);
                correctLetters.add(new CorrectLetterInfo(guess.charAt(i), i));
            }
        } else {
            isMatched = false;
            if (guess.length() != correctWord.length()) {
                throw new IllegalArgumentException("Длина загаданного слова и попытки пользователя не совпадают");
            }


            StringBuilder guessTemp = new StringBuilder(guess);
            StringBuilder correctWordTemp = new StringBuilder(correctWord);
            char alreadyProcessedChar = '#';

            for (int i = 0; i < guessTemp.length(); i++) {
                if (guessTemp.charAt(i) == correctWord.charAt(i)) {
                    // Учитываем букву, которая ЕСТЬ в загаданном слове и находится на правильной позиции.
                    charStatuses.add(CharStatus.CORRECT);
                    correctLetters.add(new CorrectLetterInfo(guess.charAt(i), i));

                    // Замена на недопустимый символ, чтобы исключить из поиска букв WRONG_POSITION
                    correctWordTemp.setCharAt(i, alreadyProcessedChar);
                    // Исключаем из поиска (уже нашли)
                    guessTemp.setCharAt(i, alreadyProcessedChar);

                } else if (correctWordTemp.toString().indexOf(guessTemp.charAt(i)) == -1) {
                    // Учитываем букву, которой НЕТ в загаданном слове
                    charStatuses.add(CharStatus.ABSENT);
                    absentLetters.add(guessTemp.charAt(i));

                    // Исключаем из дальнейшего поиска
                    guessTemp.setCharAt(i, alreadyProcessedChar);
                } else {
                    // Статус буквы пока не известен
                    charStatuses.add(null);
                }
            }

            for (int i = 0; i < guessTemp.length(); i++) {
                if (guessTemp.charAt(i) == alreadyProcessedChar) {
                    continue;
                }
                int indexOfWrongPositionLetter = correctWordTemp.toString().indexOf(guessTemp.charAt(i));
                if (indexOfWrongPositionLetter == -1) {
                    // Лишняя буква, помечаем отсутствующей
                    charStatuses.set(i, CharStatus.ABSENT);
                    absentLetters.add(guessTemp.charAt(i));

                } else {
                    charStatuses.set(i, CharStatus.WRONG_POSITION);
                    wrongPositionLetters.add(guessTemp.charAt(i));
                    // Замена на недопустимый символ, чтобы исключить из дальнейшего поиска букв WRONG_POSITION
                    correctWordTemp.setCharAt(indexOfWrongPositionLetter, alreadyProcessedChar);
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
                throw new RuntimeException("Неверный статус");
            }
        }
        return sb.toString();
    }
}
