package org.example.item08;

import java.lang.ref.Cleaner;

public class Room implements AutoCloseable {
    // 1. Cleaner 인스턴스 생성 (보통 캐싱하여 공유하거나 정적 필드로 둠)
    private static final Cleaner cleaner = Cleaner.create();

    // 2. 청소가 필요한 자원을 보관하고, 실제 청소 로직을 수행할 정적 중첩 클래스
    // 절대 외부 클래스(Room)의 인스턴스를 참조해서는 안 됩니다.
    private static class State implements Runnable {
        int numJunkPiles; // 청소해야 할 쓰레기 수 (예: 비메모리 자원)

        State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }

        // close 메서드나 cleaner가 호출하는 정리 로직
        @Override
        public void run() {
            System.out.println("방 청소 진행 중... (쓰레기 " + numJunkPiles + "개 처리)");
            numJunkPiles = 0;
        }
    }

    private final State state;
    private final Cleaner.Cleanable cleanable;

    public Room(int numJunkPiles) {
        this.state = new State(numJunkPiles);
        // 3. Room 인스턴스와 State(자원 및 정리 로직)를 cleaner에 등록
        this.cleanable = cleaner.register(this, state);
    }

    // 4. AutoCloseable 구현: 클라이언트가 명시적으로 자원을 해제할 때 호출됨
    @Override
    public void close() {
        cleanable.clean();
    }
}