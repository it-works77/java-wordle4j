package ru.yandex.practicum.controller;

import ru.yandex.practicum.WordleDictionaryLoader;
import ru.yandex.practicum.exception.wordchecks.WordCheckException;
import ru.yandex.practicum.model.AnswerCheckResult;

import java.util.Scanner;

public class MenuController {
    private final Scanner scanner;

    public MenuController() {
        scanner = new Scanner(System.in);
    }


    public String readUserAnswer() throws WordCheckException {

        System.out.println("Введите слово или нажмите Enter для получения подсказки:");
        String answer = scanner.nextLine();

        answer = WordleDictionaryLoader.normalizeWord(answer);

        // Пустая строка, это корректно, пользователю нужна подсказка
        if (!answer.isEmpty()) {
            // проверяем корректность ввода
            WordleDictionaryLoader.validateWord(answer);
        }
        return answer;
    }

    public void showGreeting() {
        System.out.println("Добро пожаловать в игру Wordle!\n");
        System.out.println("""
                Компьютер «загадывает» слово: существительное в единственном числе в именительном падеже.
                Используется словарь русских слов, состоящих из пяти букв.
                Используются только буквы (нет пробелов и дефисов).
                Игроку доступно шесть попыток.
                """);
    }

    public void showCurrentStepNumber(Integer currentStep) {
        System.out.printf("%nПопытка №%d%n", currentStep);
    }

    public void showIncorrectWordWarning(String message) {
        System.out.println(message + ".");
    }

    public void showTryAgain(int stepsLeft) {
        System.out.println("Вы не угадали...");
        String stepsLeftWord = getStepWordInRussian(stepsLeft);
        if (stepsLeft > 0) {
            System.out.printf("Осталось %d %s%n", stepsLeft, stepsLeftWord);
            System.out.println("Попробуйте еще раз или получите подсказку...");
        }
    }

    public void showClue(String clue) {
        System.out.println("Компьютер выбрал подсказку: " + clue);
    }

    public void showEndGameWin(int currentStep) {
        String currentStepWord = getStepWordInRussian(currentStep);
        System.out.printf("Вы угадали слово за %d %s!%n", currentStep, currentStepWord);
    }

    private static String getStepWordInRussian(int stepNumber) {
        String stepNumberWord = "попыток";
        if (stepNumber == 1) {
            stepNumberWord = "попытка";
        } else if (stepNumber > 1 && stepNumber < 5) {
            stepNumberWord = "попытки";
        }
        return stepNumberWord;
    }

    public void showEndGameLose(String targetWord) {
        System.out.println("Попытки кончились. Вы не угадали слово " + targetWord);
    }

    public void showAnswer(AnswerCheckResult result) {
        System.out.println("\nУгаданные буквы");
        System.out.println(result.getGuess());
        System.out.println(result);
    }
}
