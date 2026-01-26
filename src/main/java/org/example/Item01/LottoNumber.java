package org.example.Item01;

import java.util.HashMap;
import java.util.Map;

public class LottoNumber {
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;
    
    // 미리 생성된 인스턴스를 저장할 캐시
    private static final Map<Integer, LottoNumber> CACHE = new HashMap<>();

    static {
        for (int i = MIN_NUMBER; i <= MAX_NUMBER; i++) {
            CACHE.put(i, new LottoNumber(i));
        }
    }

    private final int number;

    private LottoNumber(int number) {
        this.number = number;
    }

    // 정적 팩토리 메서드를 통해 캐싱된 객체 반환
    public static LottoNumber of(int number) {
        LottoNumber lottoNumber = CACHE.get(number);
        if (lottoNumber == null) {
            throw new IllegalArgumentException("로또 번호는 1~45 사이여야 합니다.");
        }
        return lottoNumber;
    }
}