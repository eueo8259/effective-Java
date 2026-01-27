package org.example.item02;

public class NutritionFacts {
    // 1. 모든 필드는 final로 선언하여 불변 객체(Immutable Object)로 만듭니다.
    private final int servingSize;  // 필수
    private final int servings;     // 필수
    private final int calories;     // 선택
    private final int fat;          // 선택
    private final int sodium;       // 선택
    private final int carbohydrate; // 선택

    // 2. 정적 내부 클래스로 Builder를 정의합니다.
    public static class Builder {
        // 필수 매개변수
        private final int servingSize;
        private final int servings;

        // 선택 매개변수 - 기본값으로 초기화
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        // 3. 필수 매개변수는 Builder의 생성자로 받습니다.
        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        // 4. 선택 매개변수는 메서드를 통해 설정하고, 'this'를 반환하여 메서드 체이닝을 지원합니다.
        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public Builder sodium(int val) {
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        // 5. 최종적으로 객체를 생성하여 반환합니다.
        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    // 6. 생성자는 private으로 닫고, 빌더를 통해서만 생성되도록 합니다.
    private NutritionFacts(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }
    
    // toString() 추가 추천 (테스트용)
}