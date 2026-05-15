package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words;

    public WordleDictionary() {
        words = new ArrayList<>();
    }

    public void addWord(String s) {
        words.add(s);
    }

    public String getWord(int index) {
        return words.get(index);
    }

    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public int getWordsCount() {
        return words.size();
    }
}
