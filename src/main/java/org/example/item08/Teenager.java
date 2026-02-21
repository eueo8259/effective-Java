package org.example.item08;


public class Teenager {
    public static void main(String[] args) {
        // try-with-resources 없이 객체 생성 후 방치
        new Room(99);
        System.out.println("방 사용 종료. 청소는 알아서 되겠지...");
        
        // System.gc()를 호출하더라도 cleaner의 실행을 보장할 수 없습니다.
        // 출력 결과에 "방 청소 진행 중..."이 나타날지 여부는 확신할 수 없습니다.
    }
}