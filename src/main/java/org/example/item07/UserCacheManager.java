package org.example.item07;

import java.util.Map;
import java.util.WeakHashMap;

public class UserCacheManager {
    
    // WeakHashMap은 키(Key)에 대한 강한 참조가 없어지면 해당 엔트리를 제거함
    private final Map<UserKey, UserData> cache = new WeakHashMap<>();

    public void putUserData(UserKey key, UserData data) {
        cache.put(key, data);
    }

    public UserData getUserData(UserKey key) {
        return cache.get(key);
    }
    
    public int getCacheSize() {
        return cache.size();
    }
}