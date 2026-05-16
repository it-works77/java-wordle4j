package ru.yandex.practicum.controller;

import java.util.Scanner;

public class MenuController {
    private final Scanner scanner;

    public MenuController() {
        scanner = new Scanner(System.in);
    }

    public void showCongratulations(int steps) {
        System.out.printf("Вы угадали слово за %d попыток!%n", steps);
    }

    public String readUserAnswer() {
        // TODO Проверять корректность ввода, выбрасывать игровые исключения, если неверно
        String answer = scanner.nextLine().trim();

        return answer;
    }

    public void showGreeting() {
        System.out.println("Добро пожаловать в игру Wordle!");
        System.out.println("Компьютер «загадывает» слово — " +
                "существительное в единственном числе в именительном падеже. " +
                "Используется словарь русских слов, состоящих из пяти букв." +
                "Используются только буквы (нет пробелов и дефисов). \n" +
                "Игроку доступно шесть попыток. ");
    }

    public void showTryAgain(int steps)  {
        System.out.printf("Осталось %d попыток%n", steps);
        System.out.println("Попробуйте еще...");
    }
}
