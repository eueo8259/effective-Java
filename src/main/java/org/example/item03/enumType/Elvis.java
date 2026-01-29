package org.example.item03.enumType;

public enum Elvis {
    INSTANCE;

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }
    
    // 필요한 비즈니스 로직 추가 가능
    public void sing() {
        System.out.println("I'll have a blue Christmas without you~");
    }
}