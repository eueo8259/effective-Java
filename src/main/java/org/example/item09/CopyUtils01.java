package org.example.item09;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyUtils01 {
    private static final int BUFFER_SIZE = 8192;

    public static void copy(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                byte[] buf = new byte[BUFFER_SIZE];
                int n;
                // 만약 여기서 예외가 발생하고 (원인 A)
                while ((n = in.read(buf)) >= 0) {
                    out.write(buf, 0, n);
                }
            } finally {
                // 여기서도 예외가 발생하면 (원인 B), 원인 A의 정보는 완전히 소실됨
                out.close();
            }
        } finally {
            in.close();
        }
    }
}