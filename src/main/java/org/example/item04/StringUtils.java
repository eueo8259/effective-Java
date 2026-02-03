package org.example.item04;

// 상속도 막기 위해 final로 선언하는 것을 권장 (선택사항)
public final class StringUtils {

    // 1. private 생성자로 외부 인스턴스화 차단
    private StringUtils() {
        // 2. 클래스 내부에서의 실수 및 리플렉션 공격 방어
        throw new AssertionError("유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    // --- 유틸리티 메서드들 ---

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String reverse(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return new StringBuilder(str).reverse().toString();
    }
}