package org.example.item05;

// 🚫 잘못된 예시: 정적 유틸리티를 잘못 사용함 - 유연하지 않고 테스트하기 어려움
public class spellCheckerW {
    // 자원을 직접 생성하여 고정시킴 (강한 결합)
    private static final Dictionary dictionary = new EnglishDictionary();

    private spellCheckerW() {} // 인스턴스화 방지

    public static boolean isValid(String word) {
        return dictionary.contains(word);
    }
}
