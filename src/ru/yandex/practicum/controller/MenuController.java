package ru.yandex.practicum.controller;

import ru.yandex.practicum.WordleDictionaryLoader;
import ru.yandex.practicum.exception.wordchecks.WordCheckException;

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
        System.out.println("Компьютер «загадывает» слово: " +
                "существительное в единственном числе в именительном падеже.\n" +
                "Используется словарь русских слов, состоящих из пяти букв.\n" +
                "Используются только буквы (нет пробелов и дефисов).\n" +
                "Игроку доступно шесть попыток.\n");
    }

    public void showCurrentStepNumber(Integer currentStep) {
        System.out.printf("%nПопытка №%d%n", currentStep);
    }

    public void showIncorrectWordWarning(String message) {
        System.out.println(message + ".");
    }

    public void showTryAgain(int stepsLeft)  {
        System.out.println("Вы не угадали...");
        String stepsLeftWord = "попыток";
        if (stepsLeft == 1) {
            stepsLeftWord = "попытка";
        } else if (stepsLeft > 1 && stepsLeft < 5) {
            stepsLeftWord = "попытки";

        }
        System.out.printf("Осталось %d %s%n", stepsLeft, stepsLeftWord);
        System.out.println("Попробуйте еще раз или получите подсказку...");
    }

    public void showСlue(String clue) {
        System.out.println("Компьютер выбрал подсказку: " + clue);

    }
    public void showEndGameWin(int currentStep) {
        System.out.printf("Вы угадали слово за %d попыток!%n", currentStep);
    }
    public void showEndGameLose() {
        System.out.println("Попытки кончились. Вы не угадали слово...");
    }

}
