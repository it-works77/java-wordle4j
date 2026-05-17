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

    private final WordleDictionary dictionary; // Слова для угадывания
    private final WordleDictionary wordsSuggests; // Словарь подсказок, очищается по мере угадывания букв

    private boolean isWin = false; // Результат игры

    private String answer;
    private String targetWord;
    private Integer currentStep;
    private Integer stepsLeft;

    public WordleGame(WordleDictionary wd, WordleLogger logger) {
        dictionary = wd;
        wordsSuggests = new WordleDictionary(wd, logger);
        currentStep = 0;
        stepsLeft = WordleConfig.GAME_MAX_STEPS;
        this.logger = logger;
        menu = new MenuController();
    }

    public void run() {

        if (WordleConfig.FIXED_TARGET_WORD.isEmpty()) {
            targetWord = dictionary.getRandomWord();
        } else {
            targetWord = WordleConfig.FIXED_TARGET_WORD;
        }
        logger.info("Загадали слово", targetWord);

        menu.showGreeting();

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

            // Если перевод строки, то предположить слово, иначе проверить слово
            if (answer.isEmpty()) {
                // Получить и показать на экране подсказку
                logger.info("Пользователь запросил подсказку");
                answer = getClueWord();
                menu.showClue(answer);
            } else {
                logger.info("Слово пользователя: \"", answer, "\"");
            }

            AnswerCheckResult result = checkAnswer(answer);

            // Чистим wordsSuggests по новым данным от неподходящих слов
            // (будет использоваться в getClueWord на следующем шаге)
            wordsSuggests.removeNotMatchingWords(result.getCorrectLetters()
                    , result.getWrongPositionLetters()
                    , result.getAbsentLetters()
            );

            menu.showAnswer(result);

            if (result.isMatched()) {
                logger.info("Пользователь угадал слово:", result.getGuess());
                isWin = true;
                break;
            } else {
                logger.info("Пользователь ошибся:", result.getGuess());
                menu.showTryAgain(stepsLeft);
            }
        }

        if (isWin) {
            menu.showEndGameWin(currentStep);
        } else {
            menu.showEndGameLose(targetWord);
        }

    }

    private String getClueWord() {
        return wordsSuggests.getRandomWord();
    }

    private AnswerCheckResult checkAnswer(String answer) {
        logger.info("Проверяем ответ:", answer);

        // Проверяем ответ
        AnswerCheckResult result = new AnswerCheckResult(answer, targetWord);
        logger.debug("Совпадения:", result.toString()
                , "| Загаданное слово:", targetWord);
        return  result;
    }

    public boolean isWin() {
        return isWin;
    }
}
