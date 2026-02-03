package org.example.item05;

import java.util.Objects;
import java.util.function.Supplier;

public class Mosaic {
    private final Tile tile;

    // Tile 자체가 아니라, Tile을 만들어주는 공장(Supplier)을 받음
    public Mosaic(Supplier<? extends Tile> tileFactory) {
        this.tile = Objects.requireNonNull(tileFactory.get());
    }
    // 한정적 와일드카드 타입(? extends Tile)을 사용하여 유연성 확보

    public static void main(String[] args) {
        // Mosaic 생성 시점에 Tile을 생성해서 주입
        Mosaic mosaic = new Mosaic(() -> new Tile());
        // 또는 메서드 참조 사용
        Mosaic mosaic2 = new Mosaic(Tile::new);
    }
}