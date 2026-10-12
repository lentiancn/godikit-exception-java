package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        // {0xFB, 0xEF, 0xBE} 的四个 6 位组均为 62
        // 标准 Base64 中 62 = '+'，URL-safe 中 62 = '-'
        byte[] plusData = new byte[]{(byte) 0xFB, (byte) 0xEF, (byte) 0xBE};
        String plusEncoded = Base64.getUrlEncoder().encodeToString(plusData);
        assertEquals("----", plusEncoded);
        assertArrayEquals(plusData, Base64.getUrlDecoder().decode(plusEncoded));

        // {0x3F, 0x3F, 0x3F} 的最后一个 6 位组为 63
        // 标准 Base64 中 63 = '/'，URL-safe 中 63 = '_'
        byte[] slashData = new byte[]{(byte) 0x3F, (byte) 0x3F, (byte) 0x3F};
        String slashEncoded = Base64.getUrlEncoder().encodeToString(slashData);
        assertEquals("Pz8_", slashEncoded);
        assertArrayEquals(slashData, Base64.getUrlDecoder().decode(slashEncoded));
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
        assertTrue(mimeEncoded.contains("\r\n") || mimeEncoded.contains("\n"));

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