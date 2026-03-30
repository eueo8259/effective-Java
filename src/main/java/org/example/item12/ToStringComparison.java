package org.example.item12;

import java.util.*;

public class ToStringComparison {

    // [Before] toString 재정의 없음 → 기본 Object.toString()
    static class PointBefore {
        final int x, y;

        PointBefore(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof PointBefore)) return false;
            PointBefore p = (PointBefore) o;
            return this.x == p.x && this.y == p.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
        // toString 없음 → "PointBefore@1b6d3586" 같은 쓸모없는 문자열 반환
    }

    // toString 재정의 → 유용한 정보 반환
    static class PointAfter {
        final int x, y;

        PointAfter(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof PointAfter)) return false;
            PointAfter p = (PointAfter) o;
            return this.x == p.x && this.y == p.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point(" + x + ", " + y + ")";
        }
    }

    public static void main(String[] args) {
        demonstrateAutoInvocation();
        demonstrateCollectionDisplay();
        demonstrateDebugging();
    }

    // toString이 자동 호출되는 상황 총정리
    private static void demonstrateAutoInvocation() {
        System.out.println("=== toString 자동 호출 상황 ===\n");

        PointBefore before = new PointBefore(3, 4);
        PointAfter  after  = new PointAfter(3, 4);

        // 1. System.out.println
        System.out.println("[println]");
        System.out.println("재정의 전: " + before); // PointBefore@...
        System.out.println("재정의 후: " + after);  // Point(3, 4)

        // 2. 문자열 연결 (+)
        System.out.println("\n[문자열 연결 (+)]");
        String s1 = "좌표는 " + before;
        String s2 = "좌표는 " + after;
        System.out.println(s1);
        System.out.println(s2);

        // 3. String.format / printf
        System.out.println("\n[String.format]");
        System.out.println(String.format("재정의 전: %s", before));
        System.out.println(String.format("재정의 후: %s", after));

        // 4. assert 실패 메시지
        // assert before.equals(new PointBefore(0,0)) : before;
        // → 실패 시 "PointBefore@..."가 출력되어 디버깅 어려움

        // 5. 예외 메시지
        System.out.println("\n[예외 메시지에서]");
        try {
            throw new IllegalArgumentException("잘못된 좌표: " + before);
        } catch (IllegalArgumentException e) {
            System.out.println("재정의 전 예외: " + e.getMessage());
        }
        try {
            throw new IllegalArgumentException("잘못된 좌표: " + after);
        } catch (IllegalArgumentException e) {
            System.out.println("재정의 후 예외: " + e.getMessage());
        }
    }

    // 컬렉션에 담겼을 때 toString의 중요성
    private static void demonstrateCollectionDisplay() {
        System.out.println("\n=== 컬렉션에서의 toString ===\n");

        List<PointBefore> beforeList = List.of(
            new PointBefore(1, 2),
            new PointBefore(3, 4),
            new PointBefore(5, 6)
        );
        List<PointAfter> afterList = List.of(
            new PointAfter(1, 2),
            new PointAfter(3, 4),
            new PointAfter(5, 6)
        );

        System.out.println("재정의 전 리스트: " + beforeList);
        // [PointBefore@..., PointBefore@..., PointBefore@...]

        System.out.println("재정의 후 리스트: " + afterList);
        // [Point(1, 2), Point(3, 4), Point(5, 6)]

        Map<PointAfter, String> map = new HashMap<>();
        map.put(new PointAfter(1, 2), "시작점");
        map.put(new PointAfter(5, 6), "도착점");

        System.out.println("\n재정의 후 맵: " + map);
        // {Point(1, 2)=시작점, Point(5, 6)=도착점}
    }

    // 디버깅 시 로그 품질 비교
    private static void demonstrateDebugging() {
        System.out.println("\n=== 디버깅/로그에서의 차이 ===\n");

        PointBefore before = new PointBefore(10, 20);
        PointAfter  after  = new PointAfter(10, 20);

        // 실무에서 로그를 남길 때
        String logBefore = String.format("[ERROR] 처리 실패한 좌표: %s", before);
        String logAfter  = String.format("[ERROR] 처리 실패한 좌표: %s", after);

        System.out.println(logBefore); // [ERROR] 처리 실패한 좌표: PointBefore@...
        System.out.println(logAfter);  // [ERROR] 처리 실패한 좌표: Point(10, 20)
        System.out.println("\n→ 재정의 후 로그만으로 문제를 파악할 수 있습니다!");
    }
}