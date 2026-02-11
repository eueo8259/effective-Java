package org.example.item06;

public class Greeting {
    
    // 1. 절대 하지 말아야 할 방식
    // 실행될 때마다 새로운 String 인스턴스를 힙 메모리에 만듦
    public String createNewString() {
        return new String("Hello"); 
    }

    // 2. 권장 방식
    // 가상머신 안에서 이 문자열을 사용하는 모든 코드가 같은 객체를 재사용함이 보장됨
    public String useStringPool() {
        return "Hello";
    }
}