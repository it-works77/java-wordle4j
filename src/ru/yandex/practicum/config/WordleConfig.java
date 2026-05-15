package ru.yandex.practicum.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class WordleConfig {
    public static final int GAME_WORD_LENGTH = 5;
    public static final int GAME_MAX_STEPS = 6;
    public static final int LOG_LINES_READ_SAMPLER = 5000;
    public static final Charset FILE_CHARSET = StandardCharsets.UTF_8;

}
