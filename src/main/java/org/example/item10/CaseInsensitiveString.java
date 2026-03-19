package org.example.item10;

import java.util.Objects;

public final class CaseInsensitiveString {
    private final String s;

    public CaseInsensitiveString(String s) {
        this.s = Objects.requireNonNull(s);
    }

    // 대칭성 위반: CaseInsensitiveString은 String을 알지만, String은 CaseInsensitiveString을 모름
    @Override
    public boolean equals(Object o) {
        if (o instanceof CaseInsensitiveString) {
            return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
        }
        if (o instanceof String) { // 한 방향으로만 작동하는 원인
            return s.equalsIgnoreCase((String) o);
        }
        return false;
    }
}