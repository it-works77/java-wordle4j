package ru.yandex.practicum;

import org.junit.platform.commons.util.ExceptionUtils;
import ru.yandex.practicum.config.WordleConfig;
import ru.yandex.practicum.exception.LoggerException;
import ru.yandex.practicum.model.WordleDictionary;

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
    public static WordleDictionaryLoader wdl;
    public static WordleDictionary wd;
    public static WordleGame game;
    public static WordleLogger logger;


    public static void main(String[] args) {
        try {
            // создать лог-файл (он должен передаваться во все классы)
            logger = new WordleLogger(WordleConfig.LOG_LEVEL, WordleConfig.LOG_FILENAME);
            logger.warning("Старт приложения...");

            // создать загрузчик словарей WordleDictionaryLoader
            logger.info("Создаем загрузчик словарей");
            wdl = new WordleDictionaryLoader(WordleConfig.DICTIONARY_FILENAME, logger);

            // загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
            logger.info("Загружаем словарь из файла %s".formatted(WordleConfig.DICTIONARY_FILENAME));
            wd = wdl.getDictionary();

            // затем создать игру WordleGame и передать ей словарь
            logger.info("Создаем игру");
            game = new WordleGame(wd, logger);

            // вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
            logger.info("Запускаем игру");
            game.run();

            // вывести состояние игры и конечный результат
            logger.info("Выводим результаты");
            if (game.isWin()) {
                logger.info("Пользователь выиграл");
            } else {
                logger.info("Пользователь проиграл");
            }

            logger.warning("Завершение приложения...");

        } catch (LoggerException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            logger.critical("Фатальная ошибка, завершаем работу:"
                    , ex.getMessage(), "\n"
                    , ExceptionUtils.readStackTrace(ex));
            System.out.println("Игра сломалась, извините...");

        } finally {
            if (Objects.nonNull(logger)) {
                logger.close();
            }
        }
    }

}
