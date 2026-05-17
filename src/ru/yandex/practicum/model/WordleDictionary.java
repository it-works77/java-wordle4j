package ru.yandex.practicum.model;

import ru.yandex.practicum.WordleLogger;

import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;
    private final WordleLogger logger;

    public WordleDictionary(WordleLogger logger) {
        this.logger = logger;
        words = new ArrayList<>();
    }

    public WordleDictionary(WordleDictionary wd, WordleLogger logger) {
        this.logger = logger;
        words = new ArrayList<>();
        words.addAll(wd.words);
    }

    public void addWord(String s) {
        words.add(s);
    }

    public String getWord(int index) {
        return words.get(index);
    }

    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public int getWordsCount() {
        return words.size();
    }

    public void removeNotMatchingWords(ArrayList<CorrectLetterInfo> correctLetters
            , HashSet<Character> wrongPositionLetters
            , HashSet<Character> absentLetters) {

        logger.info("Чистим словарь подсказок. Слов в словаре:", String.valueOf(words.size()));

        List<String> newWords = new ArrayList<>();
        for (String word : words) {
            if (hasAnyAbsentLetter(word, absentLetters)) {
                // Если есть хоть одна отсутствующая буква, исключаем слово
                logger.debug("Исключаем из подсказок слово", word
                        , ". В нем есть буквы, которых не должно быть " +
                                "(одна или несколько из):", absentLetters.toString());
                continue;
            }

            if (hasAllCorrectLetters(word, correctLetters) && hasAllWrongPositionLetters(word, wrongPositionLetters)) {
                logger.debug("В слове", word, "есть все необходимые буквы."
                        , "Угаданные:", correctLetters.toString()
                        , ". Не на своих местах: ", wrongPositionLetters.toString());
                newWords.add(word);
            }

        }
        words = newWords;
        logger.info("Очистили словарь подсказок. Осталось слов:", String.valueOf(words.size()));
    }

    private boolean hasAnyAbsentLetter(String word, HashSet<Character> letters) {
        // TODO Если придет пустой?
        // Если есть хоть одна отсутствующая буква, исключаем слово
        for (Character ch : letters) {
            if (word.indexOf(ch) != -1) {
                logger.debug("Исключаем слово ", word, "Присутствует буква: ", String.valueOf(ch));
                return true;
            }
        }
        return false;
    }

    private boolean hasAllCorrectLetters(String word, ArrayList<CorrectLetterInfo> letters) {
        for (CorrectLetterInfo letter : letters) {
            int index = word.indexOf(letter.getLetter());
            if (index != letter.getPosition()) {
                // При первом несовпадении буквы на нужной позиции - слово не подходит
                logger.debug("Исключаем слово ", word
                        , "Нет буквы: ", String.valueOf(letter.getLetter())
                        , "на позиции", String.valueOf(letter.getPosition())
                );
                return false;
            }
        }
        return true;
    }

    private boolean hasAllWrongPositionLetters(String word, HashSet<Character> letters) {
        for (Character ch : letters) {
            if (word.indexOf(ch) != -1) {
                // Нет хотя бы одной буквы на любой позиции - слово не подходит
                logger.debug("Исключаем слово ", word
                        , ". Нет буквы: ", String.valueOf(ch));
                return false;
            }
        }
        return true;
    }
}
