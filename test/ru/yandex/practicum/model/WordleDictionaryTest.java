package ru.yandex.practicum.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.WordleDictionaryLoader;
import ru.yandex.practicum.WordleLogger;
import ru.yandex.practicum.exception.DictionaryIsEmptyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {
    static WordleDictionaryLoader wdlFull;
    static WordleDictionary dictionary;
    WordleDictionary dict;
    int initialWordsCount;

    @BeforeAll
    static void setUp() throws IOException, DictionaryIsEmptyException {
        wdlFull = new WordleDictionaryLoader("words_ru.txt", new WordleLogger());
        dictionary = wdlFull.getDictionary();
    }

    @BeforeEach
    void beforeEach() {
        dict = new WordleDictionary(dictionary, new WordleLogger());
        initialWordsCount = dict.getWordsCount();
    }

    @Test
    void AllLettersIsAbsent() {
        ArrayList<CorrectLetterInfo> correctLetters = new ArrayList<>();
        HashSet<Character> wrongPositionLetters = new HashSet<>();
        HashSet<Character> absentLetters = getLettersSet('а', 'б', 'т');
        dict.removeNotMatchingWords(correctLetters, wrongPositionLetters, absentLetters);

        assertEquals(initialWordsCount, dict.getWordsCount());
    }

    @Test
    void OnlyOneCorrectLetter() {
        ArrayList<CorrectLetterInfo> correctLetters = new ArrayList<>();
        correctLetters.add(new CorrectLetterInfo('а', 0));

        HashSet<Character> wrongPositionLetters = getLettersSet();
        HashSet<Character> absentLetters = getLettersSet();
        dict.removeNotMatchingWords(correctLetters, wrongPositionLetters, absentLetters);

        assertEquals(143, dict.getWordsCount());
    }

    @Test
    void OnlyOneWrongPositionLetter() {
        ArrayList<CorrectLetterInfo> correctLetters = new ArrayList<>();
        HashSet<Character> wrongPositionLetters = getLettersSet('а');
        HashSet<Character> absentLetters = getLettersSet();
        dict.removeNotMatchingWords(correctLetters, wrongPositionLetters, absentLetters);

        assertEquals(2046, dict.getWordsCount());
    }

    @Test
    void OnlyCorrectLetters() {
        ArrayList<CorrectLetterInfo> correctLetters = new ArrayList<>();
        correctLetters.add(new CorrectLetterInfo('а', 0));
        correctLetters.add(new CorrectLetterInfo('е', 2));

        HashSet<Character> wrongPositionLetters = getLettersSet();
        HashSet<Character> absentLetters = getLettersSet();
        dict.removeNotMatchingWords(correctLetters, wrongPositionLetters, absentLetters);

        assertEquals(12, dict.getWordsCount());
    }

    @Test
    void MoreThanOneEqualCorrectLetter() {
        ArrayList<CorrectLetterInfo> correctLetters = new ArrayList<>();
        correctLetters.add(new CorrectLetterInfo('е', 1));
        correctLetters.add(new CorrectLetterInfo('е', 3));

        HashSet<Character> wrongPositionLetters = getLettersSet();
        HashSet<Character> absentLetters = getLettersSet();
        dict.removeNotMatchingWords(correctLetters, wrongPositionLetters, absentLetters);

        assertEquals(74, dict.getWordsCount());
    }

    @Test
    void OnlyWrongPositionLetters() {
        ArrayList<CorrectLetterInfo> correctLetters = new ArrayList<>();
        HashSet<Character> wrongPositionLetters = getLettersSet('а', 'е');
        HashSet<Character> absentLetters = getLettersSet();
        dict.removeNotMatchingWords(correctLetters, wrongPositionLetters, absentLetters);

        assertEquals(353, dict.getWordsCount());
    }

    private static HashSet<Character> getLettersSet(Character... letters) {
        return new HashSet<>(Arrays.asList(letters));
    }
}