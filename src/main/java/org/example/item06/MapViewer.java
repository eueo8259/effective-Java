package org.example.item06;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapViewer {
    public void demonstrateKeySet() {
        Map<String, Integer> menu = new HashMap<>();
        menu.put("Burger", 10000);
        menu.put("Coke", 2000);

        // keySet은 매번 새로운 Set을 만드는 것이 아니라, 
        // 원본 Map을 대변하는 뷰(View) 객체를 반환하거나 캐싱된 객체를 반환함
        Set<String> keys1 = menu.keySet();
        Set<String> keys2 = menu.keySet();

        // 따라서 keys1과 keys2는 기능적으로 동일하며, 구현에 따라 같은 객체일 수 있음
        // keys1을 수정하면 원본 menu도 바뀜 (주의 필요)
        keys1.remove("Burger"); 
        
        System.out.println(menu.size()); // 1 출력 (Burger가 삭제됨)
    }
}