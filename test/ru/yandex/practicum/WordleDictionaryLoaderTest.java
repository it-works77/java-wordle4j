package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exception.DictionaryIsEmptyException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {
    WordleDictionaryLoader wdl;

    @BeforeEach
    void setUp() {
        wdl = new WordleDictionaryLoader(".\\test\\resources\\test1.txt", new WordleLogger());

    }

    @Test
    void getEmptyDictionary() {
        WordleDictionaryLoader wdl = new WordleDictionaryLoader(".\\test\\resources\\empty.txt"
                , new WordleLogger());
        assertThrows(DictionaryIsEmptyException.class, wdl::getDictionary);

    }

    @Test
    void checkIsValid() throws IOException, DictionaryIsEmptyException {
        // test1.txt has 2 words with length = 5
        assertEquals(2, wdl.getDictionary().getWordsCount());
    }

    @Test
    void normalizeWordSuccess() throws IOException, DictionaryIsEmptyException {
        WordleDictionaryLoader wdl = new WordleDictionaryLoader(".\\test\\resources\\check_normalize.txt"
                , new WordleLogger());
        WordleDictionary dict = wdl.getDictionary();

        assertEquals(1, dict.getWordsCount());
        assertEquals("аббет", dict.getWord(0));
    }

    @Test
    void normalizeNotRussianWordSuccess() throws IOException, DictionaryIsEmptyException {
        WordleDictionaryLoader wdl = new WordleDictionaryLoader(".\\test\\resources\\check_for_russian_word.txt"
                , new WordleLogger());
        WordleDictionary dict = wdl.getDictionary();

        assertEquals(1, dict.getWordsCount());
        assertEquals("аббат", dict.getWord(0));
    }
}