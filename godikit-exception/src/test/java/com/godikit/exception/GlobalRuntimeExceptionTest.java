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
package com.godikit.exception;

import com.godikit.exception.spi.provider.BaseErrorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalRuntimeExceptionTest {

    @Test
    void defaultConstructor_ShouldHaveSystemError() {
        GlobalRuntimeException ex = new GlobalRuntimeException();

        assertEquals(BaseErrorCode.SYSTEM_ERROR, ex.getErrorCode());
        assertEquals("unknown", ex.getErrorSource());
        assertFalse(ex.isRetryable());
    }

    @Test
    void messageConstructor_ShouldHaveSystemError() {
        GlobalRuntimeException ex = new GlobalRuntimeException("custom message");

        assertEquals("custom message", ex.getMessage());
        assertEquals(BaseErrorCode.SYSTEM_ERROR, ex.getErrorCode());
    }

    @Test
    void messageConstructor_ShouldFallbackToSystemError_WhenMessageIsNull() {
        GlobalRuntimeException ex = new GlobalRuntimeException((String) null);

        assertEquals(BaseErrorCode.SYSTEM_ERROR.getDescription(), ex.getMessage());
    }

    @Test
    void messageCauseConstructor_ShouldHaveSystemError() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalRuntimeException ex = new GlobalRuntimeException("message", cause);

        assertEquals("message", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals(BaseErrorCode.SYSTEM_ERROR, ex.getErrorCode());
    }

    @Test
    void causeConstructor_ShouldSetCauseAndSystemError() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalRuntimeException ex = new GlobalRuntimeException(cause);

        assertSame(cause, ex.getCause());
        assertEquals(BaseErrorCode.SYSTEM_ERROR, ex.getErrorCode());
    }

    @Test
    void errorCodeConstructor_ShouldSetErrorCode() {
        GlobalRuntimeException ex = new GlobalRuntimeException(BaseErrorCode.PARAM_ERROR);

        assertEquals(BaseErrorCode.PARAM_ERROR, ex.getErrorCode());
        assertEquals("Parameter Error", ex.getMessage());
    }

    @Test
    void errorCodeConstructor_ShouldHandleNull() {
        GlobalRuntimeException ex = new GlobalRuntimeException((ErrorCode) null);

        assertNull(ex.getErrorCode());
    }

    @Test
    void messageErrorCodeConstructor_ShouldSetBoth() {
        GlobalRuntimeException ex = new GlobalRuntimeException("custom", BaseErrorCode.PARAM_ERROR);

        assertEquals("custom", ex.getMessage());
        assertEquals(BaseErrorCode.PARAM_ERROR, ex.getErrorCode());
    }

    @Test
    void messageCauseErrorCodeConstructor_ShouldSetAll() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalRuntimeException ex = new GlobalRuntimeException("custom", cause, BaseErrorCode.PARAM_ERROR);

        assertEquals("custom", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals(BaseErrorCode.PARAM_ERROR, ex.getErrorCode());
    }

    @Test
    void causeErrorCodeConstructor_ShouldSetBoth() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalRuntimeException ex = new GlobalRuntimeException(cause, BaseErrorCode.PARAM_ERROR);

        assertSame(cause, ex.getCause());
        assertEquals(BaseErrorCode.PARAM_ERROR, ex.getErrorCode());
    }

    @Test
    void messageCodeStringConstructor_ShouldLookupCode() {
        GlobalRuntimeException ex = new GlobalRuntimeException("custom", "000020");

        assertEquals("custom", ex.getMessage());
        assertEquals("000020", ex.getErrorCode().getCode());
    }

    @Test
    void messageCauseCodeStringConstructor_ShouldSetAll() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalRuntimeException ex = new GlobalRuntimeException("custom", cause, "000020");

        assertEquals("custom", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals("000020", ex.getErrorCode().getCode());
    }

    @Test
    void causeCodeStringConstructor_ShouldSetBoth() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalRuntimeException ex = new GlobalRuntimeException(cause, "000020");

        assertSame(cause, ex.getCause());
        assertEquals("000020", ex.getErrorCode().getCode());
    }

    @Test
    void setRetryable_ShouldUpdateFlag() {
        GlobalRuntimeException ex = new GlobalRuntimeException();

        assertFalse(ex.isRetryable());

        ex.setRetryable(true);
        assertTrue(ex.isRetryable());

        ex.setRetryable(false);
        assertFalse(ex.isRetryable());
    }

    @Test
    void setRetryable_ShouldReturnThis() {
        GlobalRuntimeException ex = new GlobalRuntimeException();

        GlobalRuntimeException result = ex.setRetryable(true);

        assertSame(ex, result);
    }

    @Test
    void toGlobalException_ShouldConvert() {
        GlobalRuntimeException ex = new GlobalRuntimeException("test", BaseErrorCode.PARAM_ERROR);
        ex.setRetryable(true);

        GlobalException result = GlobalRuntimeException.toGlobalException(ex);

        assertNotNull(result);
        assertEquals("test", result.getMessage());
        assertEquals(BaseErrorCode.PARAM_ERROR, result.getErrorCode());
        assertTrue(result.isRetryable());
    }

    @Test
    void toGlobalException_ShouldReturnNull_WhenInputIsNull() {
        GlobalException result = GlobalRuntimeException.toGlobalException(null);

        assertNull(result);
    }

    @Test
    void toString_ShouldFormatCorrectly() {
        GlobalRuntimeException ex = new GlobalRuntimeException("test", BaseErrorCode.SYSTEM_ERROR);

        String result = ex.toString();

        assertTrue(result.contains("[unknown]"));
        assertTrue(result.contains("000010"));
        assertTrue(result.contains("System Error"));
        assertTrue(result.contains("test"));
    }

    @Test
    void toString_ShouldHandleNullErrorCode() {
        GlobalRuntimeException ex = new GlobalRuntimeException((ErrorCode) null);

        String result = ex.toString();

        assertTrue(result.contains("[unknown]"));
        assertTrue(result.contains("unknown"));
    }

    @Test
    void shouldExtendRuntimeException() {
        GlobalRuntimeException ex = new GlobalRuntimeException();

        assertInstanceOf(RuntimeException.class, ex);
    }
}
