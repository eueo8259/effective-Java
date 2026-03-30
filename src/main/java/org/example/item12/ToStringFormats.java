package org.example.item12;


public class ToStringFormats {

    // [전략 1] 포맷을 명시적으로 고정 + 문서화
    // 장점: 사람이 읽기 좋고, 파싱 가능, 표준 표현으로 활용 가능
    // 단점: 한번 공개하면 평생 이 포맷을 유지해야 함 (변경 시 기존 코드 파괴)
    static class FixedFormatMoney {
        private final long amount;   // 단위: 원
        private final String currency;

        FixedFormatMoney(long amount, String currency) {
            this.amount   = amount;
            this.currency = currency;
        }

        /**
         * 이 금액의 문자열 표현을 반환한다.
         * 문자열은 "[금액] [통화코드]" 형태다.
         * 예: "10000 KRW", "100 USD"
         * 이 포맷은 향후에도 유지되므로 파싱 용도로 사용해도 좋다.
         * 단, 포맷이 바뀔 경우 기존 코드가 깨질 수 있음에 유의할 것.
         */
        @Override
        public String toString() {
            return amount + " " + currency;
        }

        // 포맷을 고정했다면 반드시 접근자 제공
        // → toString 파싱에 의존하지 않아도 되도록
        public long getAmount()     { return amount; }
        public String getCurrency() { return currency; }
    }

    // [전략 2] 포맷 미고정 (유연한 toString)
    // 장점: 향후 정보 추가/표현 변경 자유로움
    // 단점: 파싱 용도로 쓰면 나중에 포맷 변경 시 파싱 코드가 망가짐
    // 적합한 경우: 내부용 객체, 자주 바뀌는 도메인, 개발 중인 클래스
    static class FlexibleProduct {
        private final String name;
        private final int    price;
        private final String category;
        private boolean      onSale;

        FlexibleProduct(String name, int price, String category) {
            this.name     = name;
            this.price    = price;
            this.category = category;
        }

        void setOnSale(boolean onSale) { this.onSale = onSale; }

        /**
         * 이 제품의 문자열 표현을 반환한다.
         * 반환 형식은 명시하지 않으며 향후 변경될 수 있다.
         * 프로그래밍 방식으로 처리하려면 각 접근자(getName 등)를 사용할 것.
         */
        @Override
        public String toString() {
            // 향후 필드가 추가되어도 자유롭게 변경 가능
            return String.format("Product{name='%s', price=%,d원, category='%s'%s}",
                name, price, category, onSale ? ", [SALE]" : "");
        }

        public String getName()     { return name; }
        public int    getPrice()    { return price; }
        public String getCategory() { return category; }
        public boolean isOnSale()   { return onSale; }
    }

    // [전략 3] 정적 팩터리 메서드로 파싱 지원 (포맷 고정 시 권장)
    // toString() 결과를 다시 객체로 복원할 수 있음
    static class Temperature {
        private final double celsius;

        private Temperature(double celsius) {
            this.celsius = celsius;
        }

        public static Temperature ofCelsius(double celsius) {
            return new Temperature(celsius);
        }

        /**
         * "37.5°C" 형태의 문자열을 반환한다. 이 포맷은 고정이다.
         * fromString(t.toString()) == t 가 보장된다.
         */
        @Override
        public String toString() {
            return celsius + "°C";
        }

        /**
         * toString()이 반환한 포맷의 문자열로부터 Temperature를 복원한다.
         * 예: Temperature.fromString("36.5°C")
         */
        public static Temperature fromString(String s) {
            if (!s.endsWith("°C"))
                throw new IllegalArgumentException("올바른 형식이 아닙니다: " + s);
            return new Temperature(Double.parseDouble(s.replace("°C", "")));
        }

        public double getCelsius()    { return celsius; }
        public double getFahrenheit() { return celsius * 9.0 / 5.0 + 32; }
    }

    public static void main(String[] args) {
        // 포맷 고정
        FixedFormatMoney price = new FixedFormatMoney(15000, "KRW");
        System.out.println(price); // "15000 KRW"

        // 포맷 미고정
        FlexibleProduct product = new FlexibleProduct("아메리카노", 4500, "음료");
        product.setOnSale(true);
        System.out.println(product); // Product{name='아메리카노', price=4,500원, category='음료', [SALE]}

        // 포맷 고정 + 역파싱 지원
        Temperature t1 = Temperature.ofCelsius(36.5);
        String s = t1.toString();          // "36.5°C"
        Temperature t2 = Temperature.fromString(s); // 복원
        System.out.println(t1.toString().equals(t2.toString())); // true
    }
}