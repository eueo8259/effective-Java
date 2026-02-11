package org.example.item06;

import java.util.regex.Pattern;

public class RomanNumerals {
    // 성능 개선의 핵심: 비싼 객체(Pattern)를 static final 필드로 끄집어내어 초기화 시 1번만 컴파일되게 함
    private static final Pattern ROMAN = Pattern.compile(
            "^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$"
    );

    // 1. 좋은 예: 미리 컴파일된 인스턴스를 재사용
    public static boolean isRoman(String s) {
        return ROMAN.matcher(s).matches();
    }

    // 2. 나쁜 예: 호출될 때마다 Pattern 인스턴스를 새로 만듦 (성능 저하의 주범)
    public static boolean isRomanSlow(String s) {
        // String.matches는 내부적으로 항상 Pattern 인스턴스를 새로 생성함
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }
}