package org.example.item4;

// 잘못된 예시: 인스턴스화를 막으려 추상 클래스로 선언함
public abstract class MathUtils {
    
    public static int add(int a, int b) {
        return a + b;
    }

    // 문제점 1: 사용자는 "아, 이 클래스는 상속해서 쓰라는 뜻이구나"라고 오해함
    // 문제점 2: 누군가 상속받은 하위 클래스를 만들면 인스턴스화가 가능해짐
    static class MyMathUtils extends MathUtils {
        // 인스턴스 생성 가능!
    }
}