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
        // В словаре подсказок как минимум должно остаться правильное слово, он не должен быть пустым
        if (words.isEmpty()) {
            logger.critical("Словарь подсказок пуст...");
        }
        return words.get(random.nextInt(words.size()));
    }

    public String getRandomSuggest() {
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

        // Если все буквы не угаданы, то словарь подсказок не изменится
        if (correctLetters.isEmpty() && wrongPositionLetters.isEmpty()) {
            return;
        }

        List<String> newWords = new ArrayList<>();
        for (String word : words) {
            if (hasAnyAbsentLetter(word, absentLetters)) {
                // Если есть хоть одна отсутствующая буква, исключаем слово
                logger.debug("Исключаем из подсказок слово", word
                        , "| В нем есть буквы, которых не должно быть " +
                                "(одна или несколько из):", absentLetters.toString());
                continue;
            }

            if (hasAllCorrectLetters(word, correctLetters) && hasAllWrongPositionLetters(word, wrongPositionLetters)) {
                logger.debug("В слове", word, "есть все необходимые буквы."
                        , "Угаданные:", correctLetters.toString()
                        , "| Не на своих местах: ", wrongPositionLetters.toString());
                newWords.add(word);
            }

        }
        words = newWords;
        logger.info("Очистили словарь подсказок. Осталось слов:", String.valueOf(words.size()));
        if (words.size() <= 50)
            logger.debug("Остались подсказки:", words.toString());
    }

    private boolean hasAnyAbsentLetter(String word, HashSet<Character> letters) {
        // Если есть хоть одна отсутствующая буква, исключаем слово
        for (Character ch : letters) {
            if (word.indexOf(ch) != -1) {
                logger.debug("Исключаем слово ", word, "| Присутствует буква: ", String.valueOf(ch));
                return true;
            }
        }
        return false;
    }

    private boolean hasAllCorrectLetters(String word, ArrayList<CorrectLetterInfo> letters) {
        for (CorrectLetterInfo letter : letters) {
            // Вхождений буквы может быть несколько, соберем все вхождения
            List<Integer> indexes = new ArrayList<>();
            int index = word.indexOf(letter.getLetter());

            while (index != -1) {
                indexes.add(index);
                index = word.indexOf(letter.getLetter(), index + 1);
            }

            boolean isIndexMatched = false;
            for (Integer i : indexes) {
                if (i.equals(letter.getPosition())) {
                    isIndexMatched = true;
                    break;
                }
            }

            if (!isIndexMatched) {
                // При первом же несовпадении буквы на нужной позиции (не нашли позицию буквы) - слово не подходит
                logger.debug("Исключаем слово", word
                            , "| Нет буквы:", String.valueOf(letter.getLetter())
                            , "на позиции", String.valueOf(letter.getPosition())
                            ,"Точные совпадения букв:", letters.toString()
                    );
                return false;
            }
        }
        return true;
    }

    private boolean hasAllWrongPositionLetters(String word, HashSet<Character> letters) {
        for (Character ch : letters) {
            if (word.indexOf(ch) == -1) {
                // Нет хотя бы одной буквы на любой позиции - слово не подходит
                logger.debug("Исключаем слово", word
                        , "| Нет буквы на 'неверной' позиции:", String.valueOf(ch)
                        , "| Набор букв:", letters.toString());
                return false;
            }
        }
        return true;
    }
}
