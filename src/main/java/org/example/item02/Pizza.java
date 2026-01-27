package org.example.item02;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

// 최상위 추상 클래스
public abstract class Pizza {
    public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
    final Set<Topping> toppings;

    // 재귀적 타입 한정(Recursive Type Bound)을 사용
    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self(); // this 대신 self()를 반환
        }

        abstract Pizza build();

        // 하위 클래스는 이 메서드를 오버라이딩하여 'this'를 반환해야 함
        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }
}