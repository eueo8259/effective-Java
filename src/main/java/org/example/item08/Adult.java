package org.example.item08;

import com.effective.item8.resource.Room;

public class Adult {
    public static void main(String[] args) {
        // try-with-resources를 사용하여 블록 종료 시 자동으로 close() 호출 보장
        try (Room myRoom = new Room(7)) {
            System.out.println("방 사용 중...");
        }
        // 출력 결과:
        // 방 사용 중...
        // 방 청소 진행 중... (쓰레기 7개 처리)
    }
}