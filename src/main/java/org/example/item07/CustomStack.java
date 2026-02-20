package org.example.item07;

import java.util.Arrays;
import java.util.EmptyStackException;

public class CustomStack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public CustomStack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    // 메모리 누수가 발생하는 잘못된 pop 구현
    public Object popWithLeak() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        // size 위치의 객체는 더 이상 사용되지 않지만, elements 배열이 참조를 유지함
        return elements[--size];
    }

    // 올바른 pop 구현: 다 쓴 참조 해제
    public Object popCorrectly() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object result = elements[--size];
        // 다 쓴 참조를 null 처리하여 GC가 회수할 수 있도록 함
        elements[size] = null; 
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}