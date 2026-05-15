package ru.yandex.practicum;

import ru.yandex.practicum.config.WordleConfig;
import ru.yandex.practicum.controller.MenuController;

import java.util.Scanner;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private final WordleLogger logger;
    private MenuController menu;

    private String answer;
    private String targetWord;
    private Integer steps;
    private final WordleDictionary dictionary;

    public WordleGame(WordleDictionary wd, WordleLogger logger) {
        dictionary = wd;
        steps = 0;
        this.logger = logger;
    }

    public void run() {
        // игровые ошибки обрабатываем здесь
        targetWord = dictionary.getRandomWord();
        logger.info("Загадали слово", targetWord);

        menu.showGreeting();

        while (steps < WordleConfig.GAME_MAX_STEPS) {
            logger.info("Попытка №", steps.toString());

            answer = menu.readUserAnswer();
            logger.info("Слово пользователя: \"", answer, "\"");

            // Если перевод строки, то предположить слово, иначе проверить слово

            if (answer.isEmpty()) {
                /* TODO
                * Предположить слово
                * Показать на экране
                * Очистить "угадывалку" (?)
                * */
            } else if (checkAnswer(answer)) {
               menu.showCongratulations(steps);
               break;
            } else {
                /* TODO
                * Определить вхождения букв и сохранить (?)
                * Наполнить "угадывалку".
                */
                menu.showTryAgain(steps);
            }
        }


    }

    public boolean checkAnswer(String answer) {
        // TODO Реализовать
        // TODO "ё" конвертировать в "е"
        return false;
    }
}
