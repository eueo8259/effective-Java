package org.example.item05;

import java.util.Objects;

public class SpellCheckerC {
    private final Dictionary dictionary;

    // 핵심: 자원을 직접 만들지 않고, 생성자를 통해 주입받음
    public SpellCheckerC(Dictionary dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    public boolean isValid(String word) {
        return dictionary.contains(word);
    }
}

