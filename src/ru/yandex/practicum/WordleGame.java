package ru.yandex.practicum;

import ru.yandex.practicum.config.WordleConfig;
import ru.yandex.practicum.controller.MenuController;
import ru.yandex.practicum.exception.wordchecks.WordCheckException;
import ru.yandex.practicum.model.AnswerCheckResult;
import ru.yandex.practicum.model.WordleDictionary;

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
    private final MenuController menu;

    private String answer;
    private String targetWord;
    private Integer currentStep;
    private Integer stepsLeft;
    private final WordleDictionary dictionary;
    private final WordleDictionary wordsSuggests;

    public WordleGame(WordleDictionary wd, WordleLogger logger) {
        dictionary = wd;
        wordsSuggests = new WordleDictionary(wd, logger);
        currentStep = 0;
        stepsLeft = WordleConfig.GAME_MAX_STEPS;
        this.logger = logger;
        menu = new MenuController();
    }

    public void run() {

        targetWord = dictionary.getRandomWord();
        logger.info("Загадали слово", targetWord);

        menu.showGreeting();

        boolean isWin = false;
        while (stepsLeft > 0) {
            currentStep++;
            logger.info("Попытка №", currentStep.toString());
            menu.showCurrentStepNumber(currentStep);

            boolean isIncorrectInput;
            do {
                // игровые ошибки обрабатываем здесь
                try {
                    isIncorrectInput = false;
                    answer = menu.readUserAnswer();
                } catch (WordCheckException ex) {
                    logger.warning(ex.getMessage());
                    menu.showIncorrectWordWarning(ex.getMessage());

                    isIncorrectInput = true;
                }
            } while (isIncorrectInput);

            // Корректный ввод или запрос подсказки тратит одну попытку
            stepsLeft--;
            logger.info("Слово пользователя: \"", answer, "\"");

            // Если перевод строки, то предположить слово, иначе проверить слово
            if (answer.isEmpty()) {
                // Получить и показать на экране подсказку
                logger.info("Пользователь запросил подсказку");
                answer = getClueWord();
                menu.showСlue(answer);
            }

            AnswerCheckResult result = checkAnswer(answer);

            if (result.isMatched()) {
                logger.info("Пользователь угадал слово: ", result.getGuess());
                isWin = true;
                break;
            } else {
                /* TODO
                * Определить вхождения букв и сохранить (?)
                * Наполнить "угадывалку".
                */
                menu.showTryAgain(stepsLeft);
            }
        }

        if (isWin) {
            menu.showEndGameWin(currentStep);
        } else {
            menu.showEndGameLose();
        }

    }

    private String getClueWord() {
        // TODO Просто берем из словаря подсказок?

        return "аббат";
    }

    private AnswerCheckResult checkAnswer(String answer) {
        // TODO Implement this
        logger.info("Проверяем ответ:", answer);

        // Проверяем ответ
        logger.debug("Проверяем ответ:", answer, ". Загаданное слово:", targetWord);
        AnswerCheckResult result = new AnswerCheckResult(answer, targetWord);

        // Появились новые данные по буквам
        // Чистим wordsSuggests по новым данным (будет использоваться в getClueWord
        dictionary.removeNotMatchingWords(result.getCorrectLetters()
                , result.getWrongPositionLetters()
                , result.getAbsentLetters()
        );

        return  result;
    }
}
