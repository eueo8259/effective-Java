package org.example.item03.field;

import java.io.Serializable;

public class Elvis implements Serializable {
    // 1. public static final 필드로 인스턴스 제공
    public static final Elvis INSTANCE = new Elvis();
    private static final long serialVersionUID = 1L;

    private Elvis() {
        // 리플렉션 공격 방어 코드
        // (이미 인스턴스가 있는데 생성자가 호출되면 예외 발생)
        if (INSTANCE != null) {
            throw new IllegalStateException("이미 인스턴스가 존재합니다.");
        }
    }

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }
    
    // 직렬화 - 역직렬화 시 새로운 인스턴스가 생기는 것을 방지
    // 이 메서드가 있으면 역직렬화 시 기존 INSTANCE를 반환함
    private Object readResolve() {
        return INSTANCE;
    }
}