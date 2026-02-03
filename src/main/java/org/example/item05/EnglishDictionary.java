package org.example.item05;

import java.util.List;

// 더미 클래스
class EnglishDictionary implements Dictionary {
    private List<String> dictionary;

    @Override
    public boolean contains(String word) {
        return dictionary.contains(word);
    }
}
