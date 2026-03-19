package org.example.item10;

public final class PhoneNumber {
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = (short) areaCode;
        this.prefix   = (short) prefix;
        this.lineNum  = (short) lineNum;
    }

    @Override
    public boolean equals(Object o) {
        // 1단계: == 연산자를 사용해 입력이 자기 자신의 참조인지 확인 (성능 최적화)
        if (o == this) {
            return true;
        }

        // 2단계: instanceof 연산자로 입력이 올바른 타입인지 확인 (묵시적 null 체크 포함)
        if (!(o instanceof PhoneNumber)) {
            return false;
        }

        // 3단계: 입력을 올바른 타입으로 형변환 (2단계 덕분에 100% 안전함)
        PhoneNumber pn = (PhoneNumber) o;

        // 4단계: 입력 객체와 자기 자신의 대응되는 '핵심' 필드들이 모두 일치하는지 검사
        // 성능 최적화를 위해 다를 가능성이 크거나 비교 비용이 싼 필드부터 먼저 비교
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
    }

    // 주의: equals를 재정의할 때는 반드시 hashCode도 재정의해야 함 (아이템 11)
    @Override
    public int hashCode() {
        int result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        return result;
    }
}