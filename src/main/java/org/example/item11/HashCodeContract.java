package org.example.item11;
import java.util.*;

/**
 * hashCode의 일반 규약 3가지를 직접 검증하는 예시
 *
 * 규약 1 [일관성]  : 변경되지 않는 한 항상 같은 hashCode를 반환해야 한다.
 * 규약 2 [등치 일치]: equals가 true이면 hashCode도 반드시 같아야 한다.
 * 규약 3 [비등치]  : equals가 false라도 hashCode가 달라야 할 필요는 없다.
 *                    (단, 다르면 HashMap 성능이 좋아진다)
 */
public class HashCodeContract {

    // 규약 2 위반 예시: equals는 재정의했지만 hashCode는 재정의 안 함
    static class BrokenPoint {
        final int x, y;

        BrokenPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof BrokenPoint)) return false;
            BrokenPoint p = (BrokenPoint) o;
            return this.x == p.x && this.y == p.y;
        }
        // hashCode 재정의 없음 → Object.hashCode() 사용 (객체 주소 기반)
    }

    // 올바른 구현: equals와 hashCode 모두 재정의
    static class CorrectPoint {
        final int x, y;

        CorrectPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof CorrectPoint)) return false;
            CorrectPoint p = (CorrectPoint) o;
            return this.x == p.x && this.y == p.y;
        }

        @Override
        public int hashCode() {
            // 전형적인 hashCode 계산 방식
            int result = Integer.hashCode(x);
            result = 31 * result + Integer.hashCode(y);
            return result;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    // 규약 3: 최악의 합법적 hashCode - 항상 42 반환
    // 동작은 하지만 HashMap을 O(n)으로 만드는 안티패턴
    static class WorstHashCodePoint {
        final int x, y;

        WorstHashCodePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof WorstHashCodePoint)) return false;
            WorstHashCodePoint p = (WorstHashCodePoint) o;
            return this.x == p.x && this.y == p.y;
        }

        @Override
        public int hashCode() {
            return 42; // 합법이지만 성능 최악: 모든 객체가 하나의 버킷에 쌓임
        }
    }

    public static void main(String[] args) {
        checkContractViolation();
        checkCorrectImplementation();
        demonstratePerformanceDifference();
    }

    // 규약 2 위반 시 HashSet/HashMap이 망가지는 현상
    private static void checkContractViolation() {
        System.out.println("=== [규약 위반] BrokenPoint: equals만 재정의 ===\n");

        BrokenPoint p1 = new BrokenPoint(1, 2);
        BrokenPoint p2 = new BrokenPoint(1, 2);

        System.out.println("p1.equals(p2)  : " + p1.equals(p2));
        System.out.println("p1.hashCode()  : " + p1.hashCode());
        System.out.println("p2.hashCode()  : " + p2.hashCode());
        System.out.println("hashCode 동일   : " + (p1.hashCode() == p2.hashCode()));

        Set<BrokenPoint> set = new HashSet<>();
        set.add(p1);
        set.add(p2);

        System.out.println("\nHashSet에 (1,2) 두 번 추가 후 size: " + set.size());
        // 예상: 1, 실제: 2 (hashCode가 다르므로 중복으로 인식하지 못함)
        System.out.println("→ 같은 값임에도 중복 제거가 안 됩니다!\n");
    }

    // 올바른 구현 검증
    private static void checkCorrectImplementation() {
        System.out.println("=== [올바른 구현] CorrectPoint: equals + hashCode 모두 재정의 ===\n");

        CorrectPoint p1 = new CorrectPoint(1, 2);
        CorrectPoint p2 = new CorrectPoint(1, 2);
        CorrectPoint p3 = new CorrectPoint(3, 4);

        // 규약 1: 일관성
        System.out.println("[규약 1 - 일관성]");
        System.out.println("p1.hashCode() 반복 호출: "
            + p1.hashCode() + ", " + p1.hashCode() + ", " + p1.hashCode());

        // 규약 2: equals true → hashCode 동일
        System.out.println("\n[규약 2 - 등치 일치]");
        System.out.println("p1.equals(p2)          : " + p1.equals(p2));
        System.out.println("p1.hashCode()==p2.hashCode(): " + (p1.hashCode() == p2.hashCode()));

        // 규약 3: equals false여도 hashCode 달라도 되고 같아도 됨 (다르면 성능 향상)
        System.out.println("\n[규약 3 - 비등치]");
        System.out.println("p1.equals(p3)          : " + p1.equals(p3));
        System.out.println("p1.hashCode() != p3.hashCode(): " + (p1.hashCode() != p3.hashCode()));
        System.out.println("→ 다른 hashCode로 성능 최적화 가능");

        // HashSet 정상 동작 확인
        Set<CorrectPoint> set = new HashSet<>();
        set.add(p1);
        set.add(p2);
        set.add(p3);
        System.out.println("\nHashSet size (p1, p2(중복), p3): " + set.size());
        System.out.println("→ ✅ 중복 제거 정상 동작!\n");
    }

    // 좋은 hashCode vs 최악의 hashCode 성능 비교
    private static void demonstratePerformanceDifference() {
        System.out.println("=== [성능 비교] 좋은 hashCode vs 항상 42 반환 ===\n");

        int count = 100_000;

        // 좋은 hashCode
        Map<CorrectPoint, Integer> goodMap = new HashMap<>();
        long start = System.nanoTime();
        for (int i = 0; i < count; i++) {
            goodMap.put(new CorrectPoint(i, i), i);
        }
        for (int i = 0; i < count; i++) {
            goodMap.get(new CorrectPoint(i, i));
        }
        long goodTime = System.nanoTime() - start;

        // 최악의 hashCode (항상 42)
        Map<WorstHashCodePoint, Integer> worstMap = new HashMap<>();
        start = System.nanoTime();
        for (int i = 0; i < count / 100; i++) { // 10배 적은 수로도 비교
            worstMap.put(new WorstHashCodePoint(i, i), i);
        }
        for (int i = 0; i < count / 100; i++) {
            worstMap.get(new WorstHashCodePoint(i, i));
        }
        long worstTime = System.nanoTime() - start;

        System.out.printf("좋은 hashCode  (%,d건): %,d ms%n", count, goodTime / 1_000_000);
        System.out.printf("항상 42 반환  (%,d건): %,d ms%n", count / 100, worstTime / 1_000_000);
        System.out.println("→ 항상 42 반환 시 모든 객체가 하나의 버킷에 쌓여 O(n) 성능 저하 발생");
    }
}