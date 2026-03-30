package org.example.item12;

import java.util.Arrays;
import java.util.Objects;

public class ToStringSpecialCases {

    // 케이스 1: 보안 민감 정보는 toString에서 제외
    static class UserCredential {
        private final String username;
        private final String password;  // 민감 정보
        private final String email;

        UserCredential(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email    = email;
        }

        // 잘못된 방식: 비밀번호가 로그/출력에 노출됨
        public String toStringBad() {
            return "UserCredential{username='" + username
                + "', password='" + password    // ← 보안 위협!
                + "', email='"    + email + "'}";
        }

        // 올바른 방식: 민감 정보 마스킹 또는 제외
        @Override
        public String toString() {
            return "UserCredential{username='" + username
                + "', password='****'"           // 마스킹
                + ", email='" + email + "'}";
        }
    }

    // 케이스 2: 배열을 포함한 클래스
    static class Team {
        private final String name;
        private final String[] members;

        Team(String name, String... members) {
            this.name    = name;
            this.members = members.clone();
        }

        // 잘못된 방식: 배열 참조값이 출력됨
        public String toStringBad() {
            return "Team{name='" + name + "', members=" + members + "}";
            // → Team{name='Alpha', members=[Ljava.lang.String;@...}
        }

        // 올바른 방식: Arrays.toString() 사용
        @Override
        public String toString() {
            return "Team{name='" + name
                + "', members=" + Arrays.toString(members) + "}";
            // → Team{name='Alpha', members=[Alice, Bob, Carol]}
        }
    }

    // 케이스 3: null 필드 포함 (방어적 처리)
    static class Order {
        private final String orderId;
        private final String productName;
        private final String couponCode;  // null 가능

        Order(String orderId, String productName, String couponCode) {
            this.orderId     = orderId;
            this.productName = productName;
            this.couponCode  = couponCode; // null 허용
        }

        // 잘못된 방식: null 그대로 노출되어 혼란
        public String toStringBad() {
            return "Order{id='" + orderId
                + "', product='" + productName
                + "', coupon='"  + couponCode + "'}";
            // → Order{id='001', product='책', coupon='null'}
        }

        // 올바른 방식 1: null을 명확하게 표시
        @Override
        public String toString() {
            return "Order{id='" + orderId
                + "', product='" + productName
                + "', coupon='"  + Objects.requireNonNullElse(couponCode, "없음") + "'}";
        }

        // 올바른 방식 2: null 필드는 아예 생략
        public String toStringOmitNull() {
            StringBuilder sb = new StringBuilder();
            sb.append("Order{id='").append(orderId).append("'");
            sb.append(", product='").append(productName).append("'");
            if (couponCode != null) {
                sb.append(", coupon='").append(couponCode).append("'");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    // 케이스 4: toString을 재정의하면 안 되는 경우
    //  - 정적 유틸리티 클래스 (인스턴스 자체가 없음)
    //  - 대부분의 열거 타입 (Enum은 이미 완벽한 toString 제공)
    enum Season {
        SPRING, SUMMER, AUTUMN, WINTER;
        // Enum의 기본 toString()은 "SPRING" 같이 이름을 반환 → 재정의 불필요
        // 단, 의미 있는 설명이 필요하다면 재정의 가능
    }

    enum Planet {
        MERCURY(3.303e+23, 2.4397e6),
        VENUS  (4.869e+24, 6.0518e6),
        EARTH  (5.976e+24, 6.37814e6);

        private final double mass;    // 질량 (kg)
        private final double radius;  // 반지름 (m)

        Planet(double mass, double radius) {
            this.mass   = mass;
            this.radius = radius;
        }

        // 필요 시 의미 있는 정보를 포함해 재정의
        @Override
        public String toString() {
            return name() + "{mass=" + mass + "kg, radius=" + radius + "m}";
        }
    }

    public static void main(String[] args) {
        // 보안 민감 정보
        UserCredential cred = new UserCredential("alice", "secret123", "alice@example.com");
        System.out.println("[민감 정보 마스킹]");
        System.out.println(cred); // password='****'

        // 배열 포함
        Team team = new Team("Alpha", "Alice", "Bob", "Carol");
        System.out.println("\n[배열 포함]");
        System.out.println("잘못된 방식: " + team.toStringBad());
        System.out.println("올바른 방식: " + team);

        // null 처리
        Order order1 = new Order("001", "이펙티브 자바", null);
        Order order2 = new Order("002", "클린 코드",    "SAVE10");
        System.out.println("\n[null 필드 처리]");
        System.out.println(order1);               // coupon='없음'
        System.out.println(order1.toStringOmitNull()); // coupon 생략
        System.out.println(order2);               // coupon='SAVE10'

        // Enum
        System.out.println("\n[Enum toString]");
        System.out.println(Season.SPRING);        // "SPRING" (기본 제공)
        System.out.println(Planet.EARTH);         // 재정의된 toString
    }
}