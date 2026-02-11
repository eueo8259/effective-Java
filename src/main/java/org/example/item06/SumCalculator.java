package org.example.item06;

public class SumCalculator {

    // 1. 나쁜 예: 오토박싱으로 인한 불필요한 객체 생성
    public long sumSlow() {
        // Long(래퍼 클래스)으로 선언한 것이 치명적 실수
        Long sum = 0L; 
        
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            // 여기서 i가 더해질 때마다 새로운 Long 인스턴스가 생성됨 (총 약 21억 개)
            sum += i; 
        }
        return sum;
    }

    // 2. 좋은 예: 기본 타입 사용
    public long sumFast() {
        // long(기본 타입) 사용
        long sum = 0L; 
        
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            // 객체 생성 없이 값만 누적됨
            sum += i;
        }
        return sum;
    }
}