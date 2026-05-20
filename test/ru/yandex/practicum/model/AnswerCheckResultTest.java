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
        assertTrue(Objects.equals(cr.toString(), "+---+"));

        assertEquals(2, cr.getCorrectLetters().size());
        assertEquals(0, cr.getWrongPositionLetters().size());
        assertEquals(3, cr.getAbsentLetters().size());
    }

    /*
     * Следующие два теста на проверки ситуаций сравнения, когда в варианте пользователя повторяется буква,
     * которая есть в загаданном. Причем повторов больше, чем в загаданном.
     * Например:
     * В варианте пользователя встречается дважды одна и та же буква.
     * 1. В загаданном слове она одна.
     * Допустим, первая на верной позиции. Тогда для второй возвращаем статус ABSENT (в загаданном слове только
     * одна такая буква). Вторая на верной. Тогда для первой возвращаем ABSENT
     * 2. В загаданном слове их две.
     * Первая на верной, вторая - нет. Тогда CORRECT, WRONG_POSITION
     * Обе на неверных - два WRONG_POSITION
     */
    @Test
    void testToStringOneCorrectLetterAndOneInWrongPosition() {
        // При определении WRONG_POSITION необходимо из сравнения исключить буквы со статусом CORRECT
        AnswerCheckResult cr = new AnswerCheckResult("аббат", "абвер");
        // Не должно быть ++^--
        assertTrue(Objects.equals(cr.toString(), "++---"));

        assertEquals(2, cr.getCorrectLetters().size());
        assertEquals(0, cr.getWrongPositionLetters().size());
        assertEquals(3, cr.getAbsentLetters().size());
    }

    @Test
    void testToStringGuessHasMoreWrongPositionThanCorrectWord() {
        /*
         * При определении WRONG_POSITION необходимо учитывать кол-во одинаковых букв в варианте пользователя.
         * Если оно больше, чем в загаданном слове, то "лишняя" часть букв будет ABSENT
         */
        AnswerCheckResult cr = new AnswerCheckResult("цаацц", "абвер");
        // Не должно быть -^^--
        assertTrue(Objects.equals(cr.toString(), "-^---"));

        assertEquals(0, cr.getCorrectLetters().size());
        assertEquals(1, cr.getWrongPositionLetters().size());
        assertEquals(2, cr.getAbsentLetters().size());

        cr = new AnswerCheckResult("цааац", "абвер");
        // Не должно быть -^^^-
        assertTrue(Objects.equals(cr.toString(), "-^---"));

        assertEquals(0, cr.getCorrectLetters().size());
        assertEquals(1, cr.getWrongPositionLetters().size());
        assertEquals(2, cr.getAbsentLetters().size());
    }

}