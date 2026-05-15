package ru.yandex.practicum;

import org.junit.platform.commons.util.StringUtils;
import ru.yandex.practicum.config.WordleConfig;
import ru.yandex.practicum.exception.DictionaryIsEmptyException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Dictionary;
import java.util.logging.Logger;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final String filename;
    private final Charset file_charset;
    private final WordleLogger logger;

    public WordleDictionaryLoader(String filename, WordleLogger logger) {
        this.filename = filename;
        file_charset = WordleConfig.FILE_CHARSET;
        this.logger = logger;
    }

    public WordleDictionary getDictionary() throws IOException, DictionaryIsEmptyException {
        logger.debug("Enter getDictionary");
        WordleDictionary dict = new WordleDictionary();

        try (BufferedReader br = new BufferedReader(new FileReader(filename, file_charset))) {
            String dictLine = "";
            int lineCount = 0;

            while (br.ready()) {
                dictLine = br.readLine();
                lineCount++;
                if (lineCount % WordleConfig.LOG_LINES_READ_SAMPLER == 0)
                    logger.info("Прочитано %d слов".formatted(lineCount));
                if (isValidWord(dictLine)) {
                    dict.addWord(normalizeWord(dictLine));
                };
            }
            logger.info("Всего прочитано %d слов".formatted(lineCount));

            logger.info("В словарь загружено %d слов".formatted(dict.getWordsCount()));
            // ТЗ: Разделите исключения на те, что связаны с работой программы: ... словарь пуст
            if (dict.getWordsCount() == 0) {
                throw new DictionaryIsEmptyException("Пустой словарь");
            }
        } catch (DictionaryIsEmptyException e) {
            logger.error("Пустой словарь, слова не загружены:", filename);
            throw e;
        } catch (FileNotFoundException e) {
            logger.error("Файл словаря не найден:", filename);
            throw e;
        } catch (IOException e) {
            logger.error("Ошибка чтения словаря из файла:", filename);
            throw e;
        }

        return dict;

    }

    private String normalizeWord(String dictLine) {
        String result;

        result = dictLine.trim().toLowerCase()
                .replace("ё", "е");

        // TODO implement check for Russian alphabet!
        // TODO implement check for spaces and hyphen in word!

        return result;
    }

    private boolean isValidWord(String dictLine) {
        return dictLine.trim().length() == WordleConfig.GAME_WORD_LENGTH;
    }
}
