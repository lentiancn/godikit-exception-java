/*
 * MIT License
 *
 * Copyright (c) 2026 Len (田隆)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.godikit.exception.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThrowableUtilsTest {

    @Test
    void toString_ExpectNullString_WhenInputIsNull() {
        String result = StackTraceUtils.toString(null);
        assertEquals("null", result);
    }

    @Test
    void toString_ExpectStackTrace_WhenNormalException() {
        NullPointerException npe = new NullPointerException("test message");
        String result = StackTraceUtils.toString(npe);

        assertNotNull(result);
        assertTrue(result.contains("java.lang.NullPointerException"));
        assertTrue(result.contains("test message"));
    }

    @Test
    void toString_ExpectChainedStackTrace_WhenExceptionWithCause() {
        RuntimeException cause = new IllegalArgumentException("cause message");
        RuntimeException exception = new RuntimeException("wrapper message", cause);
        String result = StackTraceUtils.toString(exception);

        assertNotNull(result);
        assertTrue(result.contains("RuntimeException"));
        assertTrue(result.contains("wrapper message"));
        assertTrue(result.contains("IllegalArgumentException"));
        assertTrue(result.contains("cause message"));
    }

    @Test
    void toString_ExpectClassNameOnly_WhenExceptionWithEmptyMessage() {
        RuntimeException exception = new RuntimeException();
        String result = StackTraceUtils.toString(exception);

        assertNotNull(result);
        assertTrue(result.contains("RuntimeException"));
    }

    @Test
    void toString_ExpectFallbackToToString_WhenPrintStackTraceFails() {
        RuntimeException exception = new RuntimeException("fallback test") {
            @Override
            public void printStackTrace(java.io.PrintWriter s) {
                throw new RuntimeException("printStackTrace failed");
            }
        };

        String result = StackTraceUtils.toString(exception);

        assertNotNull(result);
        assertEquals(exception.toString(), result);
    }
}