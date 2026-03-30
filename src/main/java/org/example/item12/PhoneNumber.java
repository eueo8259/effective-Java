package org.example.item12;

/**
 * toString의 목적:
 * - 객체를 println, 문자열 연결(+), assert, 디버거 출력 시 자동 호출됨
 * - 기본 Object.toString()은 "PhoneNumber@adbbd" 같은 무의미한 값 반환
 * - 재정의하면 "(707) 867-5309" 같이 읽기 좋은 정보를 제공할 수 있음
 */
public class PhoneNumber {

    private final short areaCode;
    private final short prefix;
    private final short lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999,  "지역 코드");
        this.prefix   = rangeCheck(prefix,   999,  "국번");
        this.lineNum  = rangeCheck(lineNum, 9999, "가입자 번호");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    // =========================================================
    // [핵심] toString 재정의
    //
    // 책 권고 사항:
    //  1. 객체가 가진 주요 정보를 모두 반환할 것
    //  2. 반환 포맷을 문서화할지 여부를 결정할 것
    //     - 포맷 고정 시: 사람이 읽기 좋고 파싱 가능 (but 평생 유지해야 함)
    //     - 포맷 미고정 시: 향후 정보 추가/변경 자유로움
    //  3. toString이 반환한 값에 포함된 정보를 얻어올 수 있는 API를 제공할 것
    //     → getAreaCode(), getPrefix(), getLineNum() 같은 접근자 메서드
    // =========================================================

    /**
     * 이 전화번호의 문자열 표현을 반환한다.
     * 문자열은 "XXX-YYY-ZZZZ" 형태의 12글자로 구성된다.
     * XXX는 지역 코드, YYY는 국번, ZZZZ는 가입자 번호다.
     * 각각의 자리가 부족하면 앞에서부터 0으로 채운다.
     * 예: 지역 코드가 707, 국번이 867, 가입자 번호가 5309라면
     * "707-867-5309"를 반환한다.
     */
    @Override
    public String toString() {
        return String.format("%03d-%03d-%04d", areaCode, prefix, lineNum);
    }

    // toString이 반환한 값의 각 필드를 얻을 수 있는 접근자 제공
    // → toString 파싱에 의존하지 않아도 됨
    public short getAreaCode() { return areaCode; }
    public short getPrefix()   { return prefix; }
    public short getLineNum()  { return lineNum; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PhoneNumber)) return false;
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
    }

    @Override
    public int hashCode() {
        int result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        return result;
    }

    public static void main(String[] args) {
        PhoneNumber jenny = new PhoneNumber(707, 867, 5309);

        // toString이 자동 호출되는 다양한 상황들
        System.out.println(jenny);                        // println → toString 자동 호출
        System.out.println("전화번호: " + jenny);          // 문자열 연결 → toString 자동 호출
        System.out.printf("포맷: %s%n", jenny);           // printf → toString 자동 호출

        // 접근자로 개별 필드에 접근 (toString 파싱 불필요)
        System.out.println("지역 코드: " + jenny.getAreaCode());
        System.out.println("국번: "     + jenny.getPrefix());
        System.out.println("가입자 번호: " + jenny.getLineNum());
    }
}