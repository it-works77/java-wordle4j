package ru.yandex.practicum.config;

import ru.yandex.practicum.model.SeverityLevel;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class WordleConfig {
    public static final String DICTIONARY_FILENAME = "words_ru.txt";
    public static final Charset DICTIONARY_CHARSET = StandardCharsets.UTF_8;

    public static final int GAME_WORD_LENGTH = 5;
    public static final int GAME_MAX_STEPS = 6;
    public static final Charset FILE_CHARSET = StandardCharsets.UTF_8;

    public static final String LOG_FILENAME = "wordle4j.log";
    public static final SeverityLevel LOG_LEVEL = SeverityLevel.INFO;
    public static final int LOG_LINES_READ_SAMPLER = 5000;


    public static final String FIXED_TARGET_WORD = "";
}
