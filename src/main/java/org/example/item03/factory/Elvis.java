package org.example.item03.factory;

import java.io.Serializable;
import java.util.function.Supplier;

public class Elvis implements Serializable {
    // 1. private static final로 내부 감춤
    private static final Elvis INSTANCE = new Elvis();
    private static final long serialVersionUID = 1L;

    private Elvis() {
        if (INSTANCE != null) {
            throw new IllegalStateException("이미 인스턴스가 존재합니다.");
        }
    }

    // 2. 정적 팩토리 메서드로 인스턴스 반환
    public static Elvis getInstance() {
        return INSTANCE;
    }

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }

    // 3. 메서드 참조를 공급자(Supplier)로 사용 가능
    // 사용 예: Supplier<Elvis> elvisSupplier = Elvis::getInstance;

    // 직렬화 방어
    private Object readResolve() {
        return INSTANCE;
    }
}