package org.example.item09;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyUtils02 {
    private static final int BUFFER_SIZE = 8192;

    public static void copy(String src, String dst) {
        // 소괄호 안에 생성된 자원들은 블록이 끝날 때 선언된 반대 순서로 자동 close() 됨
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dst)) {
            
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n = in.read(buf)) >= 0) {
                out.write(buf, 0, n);
            }
        } catch (IOException e) {
            // try-with-resources 구조에서도 catch 블록을 사용하여 다중 예외 처리가 가능함
            System.err.println("파일 복사 중 에러 발생: " + e.getMessage());
            // 필요한 경우 e.getSuppressed()를 통해 억제된 예외들을 배열로 추출 가능
        }
    }
}