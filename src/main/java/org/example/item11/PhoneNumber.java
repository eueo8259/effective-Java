package org.example.item11;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * [문제 상황] equals만 재정의하고 hashCode를 재정의하지 않은 클래스
 *
 * Object.hashCode() 명세:
 *  1. equals 비교에 사용되는 정보가 변경되지 않았다면, 같은 객체에 대해 hashCode는 항상 같은 값을 반환해야 한다.
 *  2. equals(Object)가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 같아야 한다. ← 핵심 위반!
 *  3. equals(Object)가 두 객체를 다르다고 판단했더라도, hashCode가 다를 필요는 없다. (하지만 다르면 성능이 좋아진다)
 */
public class PhoneNumber {

    private final short areaCode;   // 지역 코드
    private final short prefix;     // 국번
    private final short lineNum;    // 가입자 번호

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "지역 코드");
        this.prefix   = rangeCheck(prefix,   999, "국번");
        this.lineNum  = rangeCheck(lineNum, 9999, "가입자 번호");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max) {
            throw new IllegalArgumentException(arg + ": " + val);
        }
        return (short) val;
    }

    // 올바른 equals 재정의
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber)) return false;
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum  == lineNum
            && pn.prefix   == prefix
            && pn.areaCode == areaCode;
    }

    // 올바른 hashCode 재정의] - Objects.hash() 활용 (간결하지만 약간 느림)

    @Override
    public int hashCode() {
        return Objects.hash(areaCode, prefix, lineNum);
    }

    // [성능이 중요한 경우] - 직접 계산하는 hashCode (책 권장 방식)
    public int hashCodeManual() {
        int result = Short.hashCode(areaCode);       // 핵심 필드 첫 번째
        result = 31 * result + Short.hashCode(prefix);  // 31: 홀수 소수, 최적화하기 좋음
        result = 31 * result + Short.hashCode(lineNum);
        return result;
        // 31 * i == (i << 5) - i : JVM이 자동으로 최적화해줌
    }

    @Override
    public String toString() {
        return String.format("(%03d) %03d-%04d", areaCode, prefix, lineNum);
    }

    // hashCode 미재정의 시 HashMap에서 발생하는 버그
    public static void main(String[] args) {
        System.out.println("=== equals만 재정의했을 때의 문제 ===\n");
        demonstrateHashCodeProblem();

        System.out.println("\n=== hashCode까지 재정의한 후 ===\n");
        demonstrateHashCodeFix();
    }

    private static void demonstrateHashCodeProblem() {
        // hashCode를 재정의하지 않으면:
        // - equals는 true를 반환하지만
        // - hashCode가 다르면 HashMap/HashSet에서 다른 버킷에 저장됨
        // → 논리적으로 같은 객체임에도 HashMap에서 찾을 수 없음

        Map<PhoneNumber, String> map = new HashMap<>();
        PhoneNumber jenny = new PhoneNumber(707, 867, 5309);
        map.put(jenny, "제니");

        PhoneNumber jennyAgain = new PhoneNumber(707, 867, 5309);

        System.out.println("jenny.equals(jennyAgain): " + jenny.equals(jennyAgain));
        // true

        System.out.println("jenny.hashCode(): "       + jenny.hashCode());
        System.out.println("jennyAgain.hashCode(): "  + jennyAgain.hashCode());
        // hashCode를 올바르게 재정의했다면 같은 값

        String result = map.get(jennyAgain);
        System.out.println("map.get(jennyAgain): "    + result);
        // hashCode 올바르게 재정의 시 → "제니"
        // hashCode 미재정의 시 → null (서로 다른 버킷에 저장됨)
    }

    private static void demonstrateHashCodeFix() {
        Map<PhoneNumber, String> map = new HashMap<>();

        PhoneNumber p1 = new PhoneNumber(707, 867, 5309);
        PhoneNumber p2 = new PhoneNumber(707, 867, 5309);

        map.put(p1, "제니");

        System.out.println("equals 결과: " + p1.equals(p2));                     // true
        System.out.println("hashCode 동일: " + (p1.hashCode() == p2.hashCode())); // true
        System.out.println("map.get(p2): " + map.get(p2));                       // "제니"
        System.out.println("✅ HashMap이 올바르게 동작합니다!");
    }
}