package ru.yandex.practicum.model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AnswerCheckResultTest {

    @Test
    void isMatchedEqualWords() {
        AnswerCheckResult cr = new AnswerCheckResult("аббат", "аббат");
        assertTrue(cr.isMatched());
    }

    @Test
    void NotMatchedDifferentWords() {
        AnswerCheckResult cr = new AnswerCheckResult("фывап", "аббат");
        assertFalse(cr.isMatched());
    }

    @Test
    void testToStringEqualWords() {
        AnswerCheckResult cr = new AnswerCheckResult("аббат", "аббат");
        assertTrue(Objects.equals(cr.toString(), "+++++"));

        assertEquals(5, cr.getCorrectLetters().size());
        assertEquals(0, cr.getWrongPositionLetters().size());
        assertEquals(0, cr.getAbsentLetters().size());

        assertEquals(2, cr.getCorrectLetters().get(2).getPosition());
        Character ch = 'б';
        assertTrue(ch.equals(cr.getCorrectLetters().get(2).getLetter()));
    }

    @Test
    void testToStringNotExistingLetters() {
        AnswerCheckResult cr = new AnswerCheckResult("яппру", "аббат");
        assertTrue(Objects.equals(cr.toString(), "-----"));

        assertEquals(0, cr.getCorrectLetters().size());
        assertEquals(0, cr.getWrongPositionLetters().size());
        assertEquals(4, cr.getAbsentLetters().size());

    }

    @Test
    void testToStringWrongPositionsLetters() {
        AnswerCheckResult cr = new AnswerCheckResult("таабб", "аббат");
        assertTrue(Objects.equals(cr.toString(), "^^^^^"));

        assertEquals(0, cr.getCorrectLetters().size());
        assertEquals(3, cr.getWrongPositionLetters().size());
        assertEquals(0, cr.getAbsentLetters().size());
    }

    @Test
    void testToStringMixLetters() {
        AnswerCheckResult cr = new AnswerCheckResult("аптут", "аббат");
        assertTrue(Objects.equals(cr.toString(), "+-^-+"));

        assertEquals(2, cr.getCorrectLetters().size());
        assertEquals(1, cr.getWrongPositionLetters().size());
        assertEquals(2, cr.getAbsentLetters().size());
    }

}