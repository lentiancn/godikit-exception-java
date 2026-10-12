package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JDK 8 新特性测试：Base64 编解码（java.util.Base64）。
 * 覆盖基本编解码、URL 安全编解码、MIME 编解码。
 */
class Base64Test {

    @Test
    void basicEncodeDecode() {
        String original = "Hello, Godikit!";
        byte[] originalBytes = original.getBytes(StandardCharsets.UTF_8);

        String encoded = Base64.getEncoder().encodeToString(originalBytes);
        assertEquals("SGVsbG8sIEdvZGlraXQh", encoded);

        byte[] decoded = Base64.getDecoder().decode(encoded);
        assertArrayEquals(originalBytes, decoded);
        assertEquals(original, new String(decoded, StandardCharsets.UTF_8));
    }

    @Test
    void urlSafeEncodeDecode() {
        byte[] data = new byte[]{(byte) 0xFB, (byte) 0xEF, (byte) 0xBE};
        String urlEncoded = Base64.getUrlEncoder().encodeToString(data);
        // URL 安全变体使用 - 和 _ 替代 + 和 /
        String trimmed = urlEncoded.replaceFirst("=+$", "");
        assertEquals("-+-", trimmed);

        byte[] decoded = Base64.getUrlDecoder().decode(urlEncoded);
        assertArrayEquals(data, decoded);
    }

    @Test
    void mimeEncodeDecode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("Godikit");
        }
        byte[] data = sb.toString().getBytes(StandardCharsets.UTF_8);

        String mimeEncoded = Base64.getMimeEncoder().encodeToString(data);
        // MIME 编码器会插入换行符
        assertEquals(true, mimeEncoded.contains("\r\n") || mimeEncoded.contains("\n"));

        byte[] decoded = Base64.getMimeDecoder().decode(mimeEncoded);
        assertArrayEquals(data, decoded);
    }

    @Test
    void encodeWithPadding() {
        String original = "a";
        String encoded = Base64.getEncoder().encodeToString(original.getBytes(StandardCharsets.UTF_8));
        assertEquals("YQ==", encoded);
    }

    @Test
    void encodeWithoutPadding() {
        String original = "a";
        String encoded = Base64.getEncoder().withoutPadding()
                .encodeToString(original.getBytes(StandardCharsets.UTF_8));
        assertEquals("YQ", encoded);
    }
}