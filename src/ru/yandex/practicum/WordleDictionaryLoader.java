package ru.yandex.practicum;

import ru.yandex.practicum.config.WordleConfig;
import ru.yandex.practicum.exception.DictionaryIsEmptyException;
import ru.yandex.practicum.exception.wordchecks.IncorrectWordLengthException;
import ru.yandex.practicum.exception.wordchecks.WordCheckException;
import ru.yandex.practicum.exception.wordchecks.WordContainsDeniedSymbolsException;
import ru.yandex.practicum.model.WordleDictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final String filename;
    private final Charset fileCharset;
    private final WordleLogger logger;

    public WordleDictionaryLoader(String filename, WordleLogger logger) {
        this.filename = filename;
        fileCharset = WordleConfig.FILE_CHARSET;
        this.logger = logger;
    }

    public WordleDictionary getDictionary() throws IOException, DictionaryIsEmptyException {
        logger.info("Загружаем словарь из файла", filename);
        WordleDictionary dict = new WordleDictionary(logger);

        try (BufferedReader br = new BufferedReader(new FileReader(filename, fileCharset))) {
            String dictLine;
            int lineCount = 0;

            while ((dictLine = br.readLine()) != null) {
                lineCount++;
                if (lineCount % WordleConfig.LOG_LINES_READ_SAMPLER == 0)
                    logger.info("Прочитано %d слов".formatted(lineCount));

                try {
                    String nw = normalizeWord(dictLine);
                    validateWord(normalizeWord(nw));
                    dict.addWord(nw);
                } catch (WordCheckException ex) {
                    // logger.debug(ex.getMessage(), ": ", dictLine);
                }
            }
            logger.info("Всего прочитано %d слов".formatted(lineCount));

            logger.info("В словарь загружено %d слов".formatted(dict.getWordsCount()));
            // ТЗ: Разделите исключения на те, что связаны с работой программы: ... словарь пуст
            if (dict.getWordsCount() == 0) {
                throw new DictionaryIsEmptyException("Пустой словарь");
            }
        } catch (DictionaryIsEmptyException ex) {
            logger.error("Пустой словарь, слова не загружены:", filename);
            throw ex;
        } catch (FileNotFoundException ex) {
            logger.error("Файл словаря не найден:", filename);
            throw ex;
        } catch (IOException ex) {
            logger.error("Ошибка чтения словаря из файла:", filename);
            throw ex;
        }

        return dict;

    }

    public static String normalizeWord(String dictLine) {
        return dictLine.trim().toLowerCase()
                .replace("ё", "е");
    }

    public static void validateWord(String word) throws WordCheckException {
        String wordToCheck = word.trim();

        if (wordToCheck.length() != WordleConfig.GAME_WORD_LENGTH)
            throw new IncorrectWordLengthException("Неверная длина слова");

        if (wordToCheck.contains(" ")) {
            throw new WordContainsDeniedSymbolsException("Слово содержит пробелы");
        }

        if (wordToCheck.contains("-")) {
            throw new WordContainsDeniedSymbolsException("Слово содержит дефис");
        }

        if (wordToCheck.matches(".*[a-zA-Z].*")) {
            throw new WordContainsDeniedSymbolsException("Слово содержит буквы английского алфавита");
        }

        if (wordToCheck.matches(".*[0-9].*")) {
            throw new WordContainsDeniedSymbolsException("Слово содержит цифры");
        }

        if (!wordToCheck.matches("[а-яА-ЯёЁ]+")) {
            throw new WordContainsDeniedSymbolsException("Слово содержит символы, отличные от русского алфавита");
        }
    }
}
