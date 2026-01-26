package org.example.Item01;

public interface DiscountPolicy {
    int applyDiscount(int originalPrice);

    // 인터페이스에 정적 팩토리 메서드 포함
    static DiscountPolicy createPolicy(String userGrade) {
        if ("VIP".equalsIgnoreCase(userGrade)) {
            return new VipDiscountPolicy();
        } else {
            return new BasicDiscountPolicy();
        }
    }
}

// 외부에는 구체적인 클래스를 숨길 수 있음 (package-private 활용 가능)
class VipDiscountPolicy implements DiscountPolicy {
    @Override
    public int applyDiscount(int originalPrice) {
        return (int) (originalPrice * 0.9); // 10% 할인
    }
}

class BasicDiscountPolicy implements DiscountPolicy {
    @Override
    public int applyDiscount(int originalPrice) {
        return originalPrice; // 할인 없음
    }
}