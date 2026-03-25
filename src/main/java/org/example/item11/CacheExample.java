package org.example.item11;

import java.util.Objects;

/**
 * hashCode 계산 비용이 클 때 사용하는 지연 초기화 캐싱 패턴
 *
 * 적용 조건:
 * - 객체가 불변일 것
 * - hashCode가 자주 호출될 것
 * - hashCode 계산 비용이 클 것 (필드가 많거나, 연산이 복잡하거나)
 *
 * 주의: 가변 객체에 적용하면 캐싱된 값이 stale해질 수 있음!
 */
public class CacheExample {

    // [패턴 1] 단순 지연 초기화 (단일 스레드 환경)
    static class DocumentSingleThread {
        private final String title;
        private final String author;
        private final String content; // 매우 긴 내용이라고 가정

        // hashCode가 아직 계산되지 않았음을 나타내는 초기값 0
        // 0은 hashCode의 유효한 값이 될 수 있지만, 실제로 계산 결과가 0인 경우는 드물다
        private int hashCode;

        public DocumentSingleThread(String title, String author, String content) {
            this.title   = Objects.requireNonNull(title);
            this.author  = Objects.requireNonNull(author);
            this.content = Objects.requireNonNull(content);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DocumentSingleThread)) return false;
            DocumentSingleThread d = (DocumentSingleThread) o;
            return title.equals(d.title)
                && author.equals(d.author)
                && content.equals(d.content);
        }

        /**
         * 지연 초기화: 처음 호출 시 계산하고, 이후에는 캐싱된 값 반환
         * 단일 스레드 환경에서만 안전
         */
        @Override
        public int hashCode() {
            int result = hashCode;
            if (result == 0) {
                // 처음 호출 시에만 계산
                result = title.hashCode();
                result = 31 * result + author.hashCode();
                result = 31 * result + content.hashCode();
                hashCode = result; // 캐싱
            }
            return result;
        }
    }

    // [패턴 2] 스레드 안전한 지연 초기화
    // 책에서 소개한 방식: volatile + double-checked locking
    static class DocumentThreadSafe {
        private final String title;
        private final String author;
        private final String content;

        // volatile: 멀티스레드에서 캐싱된 값이 올바르게 보임을 보장
        private volatile int hashCode;

        public DocumentThreadSafe(String title, String author, String content) {
            this.title   = Objects.requireNonNull(title);
            this.author  = Objects.requireNonNull(author);
            this.content = Objects.requireNonNull(content);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DocumentThreadSafe)) return false;
            DocumentThreadSafe d = (DocumentThreadSafe) o;
            return title.equals(d.title)
                && author.equals(d.author)
                && content.equals(d.content);
        }

        /**
         * 스레드 안전한 지연 초기화 (Double-Checked Locking)
         *
         * 1차 체크: synchronized 블록 진입 전 (대부분의 경우 여기서 리턴, 빠름)
         * 2차 체크: synchronized 블록 내부 (경합 시 중복 계산 방지)
         */
        @Override
        public int hashCode() {
            int result = hashCode; // volatile 필드를 지역 변수에 복사 (성능 최적화)
            if (result == 0) {             // 1차 체크
                synchronized (this) {
                    result = hashCode;
                    if (result == 0) {     // 2차 체크
                        result = title.hashCode();
                        result = 31 * result + author.hashCode();
                        result = 31 * result + content.hashCode();
                        hashCode = result; // 캐싱
                    }
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return "Document{title='" + title + "', author='" + author + "'}";
        }
    }

    // [패턴 3] record를 사용한 현대적 방식 (Java 16+)
    // record는 equals, hashCode, toString을 자동으로 생성해줌!
    record ModernDocument(String title, String author, String content) {
        // equals, hashCode, toString 자동 생성됨
        // 추가적인 유효성 검사가 필요하면 compact constructor 사용

        public ModernDocument {
            Objects.requireNonNull(title,   "title은 null일 수 없습니다");
            Objects.requireNonNull(author,  "author는 null일 수 없습니다");
            Objects.requireNonNull(content, "content는 null일 수 없습니다");
        }
    }

    public static void main(String[] args) {
        demonstrateLazyInitialization();
        demonstrateRecordAutoGeneration();
    }

    private static void demonstrateLazyInitialization() {
        System.out.println("=== 지연 초기화 캐싱 패턴 ===\n");

        DocumentThreadSafe doc1 = new DocumentThreadSafe(
            "이펙티브 자바",
            "조슈아 블로크",
            "Java 프로그래밍 모범 사례 모음..."
        );
        DocumentThreadSafe doc2 = new DocumentThreadSafe(
            "이펙티브 자바",
            "조슈아 블로크",
            "Java 프로그래밍 모범 사례 모음..."
        );

        System.out.println("첫 번째 hashCode 호출 (계산 발생): " + doc1.hashCode());
        System.out.println("두 번째 hashCode 호출 (캐시 사용): " + doc1.hashCode());
        System.out.println("doc1.equals(doc2): " + doc1.equals(doc2));
        System.out.println("hashCode 동일: " + (doc1.hashCode() == doc2.hashCode()));
        System.out.println("✅ 캐싱 패턴 정상 동작!\n");
    }

    private static void demonstrateRecordAutoGeneration() {
        System.out.println("=== record의 자동 생성 equals/hashCode ===\n");

        ModernDocument r1 = new ModernDocument("이펙티브 자바", "조슈아 블로크", "내용...");
        ModernDocument r2 = new ModernDocument("이펙티브 자바", "조슈아 블로크", "내용...");

        System.out.println("r1.equals(r2)                      : " + r1.equals(r2));
        System.out.println("r1.hashCode() == r2.hashCode()     : " + (r1.hashCode() == r2.hashCode()));
        System.out.println("r1 toString                        : " + r1);
        System.out.println("✅ record는 equals/hashCode/toString을 자동으로 올바르게 구현합니다!");
    }
}