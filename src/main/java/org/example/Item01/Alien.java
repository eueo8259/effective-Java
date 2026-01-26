package org.example.Item01;

public class Alien {
    private String type;
    private int health;
    private boolean isBoss;

    // private 생성자로 외부에서 직접 new 사용 차단
    private Alien(String type, int health, boolean isBoss) {
        this.type = type;
        this.health = health;
        this.isBoss = isBoss;
    }

    // 1. 단순 생성자보다 의미가 명확함
    public static Alien createWeakMinion() {
        return new Alien("Minion", 10, false);
    }

    public static Alien createBossMonster() {
        return new Alien("King", 1000, true);
    }
}