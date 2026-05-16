package ru.yandex.practicum;

import ru.yandex.practicum.exception.LoggerException;
import ru.yandex.practicum.model.SeverityLevel;
import ru.yandex.practicum.model.WordleDictionary;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    public static final String LOG_FILENAME = "wordle4j.log";
    public static final String LOG_LEVEL = "WARNING";
    private static final String DICTIONARY_FILENAME = "words_ru.txt";
    public static final Charset DICTIONARY_CHARSET = StandardCharsets.UTF_8;

    public static WordleDictionaryLoader wdl;
    public static WordleDictionary wd;
    public static WordleGame game;
    public static WordleLogger logger;


    public static void main(String[] args) {
        try {
            // создать лог-файл (он должен передаваться во все классы)
            logger = new WordleLogger(SeverityLevel.DEBUG, LOG_FILENAME);
            logger.warning("Старт приложения...");

            // создать загрузчик словарей WordleDictionaryLoader
            logger.info("Создаем загрузчик словарей");
            wdl = new WordleDictionaryLoader(DICTIONARY_FILENAME, logger);

            // загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
            logger.info("Загружаем словарь из файла %s".formatted(DICTIONARY_FILENAME));
            wd = wdl.getDictionary();

            // затем создать игру WordleGame и передать ей словарь
            logger.info("Создаем игру");
            game = new WordleGame(wd, logger);

            // вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
            logger.info("Запускаем игру");
            game.run();

            // вывести состояние игры и конечный результат
            logger.info("Выводим результаты");

            logger.warning("Завершение приложения...");

        } catch (LoggerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.critical("Фатальная ошибка, завершаем работу:", e.getMessage());

        } finally {
            if (Objects.nonNull(logger)) {
                logger.close();
            }
        }
    }

}
