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

import com.godikit.error.code.BaseErrorCode;
import com.godikit.error.code.ErrorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionTest {

    @Test
    void defaultConstructor_ShouldHaveSystemError() {
        GlobalException ex = new GlobalException();

        assertEquals(BaseErrorCode.SYSTEM_ERROR, ex.getErrorCode());
        assertEquals("unknown", ex.getErrorSource());
        assertFalse(ex.isRetryable());
    }

    @Test
    void messageConstructor_ShouldHaveSystemError() {
        GlobalException ex = new GlobalException("custom message");

        assertEquals("custom message", ex.getMessage());
        assertEquals(BaseErrorCode.SYSTEM_ERROR, ex.getErrorCode());
    }

    @Test
    void messageConstructor_ShouldFallbackToSystemError_WhenMessageIsNull() {
        GlobalException ex = new GlobalException((String)null);

        assertEquals(BaseErrorCode.SYSTEM_ERROR.getDescription(), ex.getMessage());
    }

    @Test
    void messageCauseConstructor_ShouldHaveSystemError() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalException ex = new GlobalException("message", cause);

        assertEquals("message", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals(BaseErrorCode.SYSTEM_ERROR, ex.getErrorCode());
    }

    @Test
    void causeConstructor_ShouldSetCauseAndSystemError() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalException ex = new GlobalException(cause);

        assertSame(cause, ex.getCause());
        assertEquals(BaseErrorCode.SYSTEM_ERROR, ex.getErrorCode());
    }

    @Test
    void errorCodeConstructor_ShouldSetErrorCode() {
        GlobalException ex = new GlobalException(BaseErrorCode.PARAM_ERROR);

        assertEquals(BaseErrorCode.PARAM_ERROR, ex.getErrorCode());
        assertEquals("Parameter Error", ex.getMessage());
    }

    @Test
    void errorCodeConstructor_ShouldHandleNull() {
        GlobalException ex = new GlobalException((ErrorCode) null);

        assertNull(ex.getErrorCode());
    }

    @Test
    void messageErrorCodeConstructor_ShouldSetBoth() {
        GlobalException ex = new GlobalException("custom", BaseErrorCode.PARAM_ERROR);

        assertEquals("custom", ex.getMessage());
        assertEquals(BaseErrorCode.PARAM_ERROR, ex.getErrorCode());
    }

    @Test
    void messageCauseErrorCodeConstructor_ShouldSetAll() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalException ex = new GlobalException("custom", cause, BaseErrorCode.PARAM_ERROR);

        assertEquals("custom", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals(BaseErrorCode.PARAM_ERROR, ex.getErrorCode());
    }

    @Test
    void causeErrorCodeConstructor_ShouldSetBoth() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalException ex = new GlobalException(cause, BaseErrorCode.PARAM_ERROR);

        assertSame(cause, ex.getCause());
        assertEquals(BaseErrorCode.PARAM_ERROR, ex.getErrorCode());
    }

    @Test
    void messageCodeStringConstructor_ShouldLookupCode() {
        GlobalException ex = new GlobalException("custom", "000020");

        assertEquals("custom", ex.getMessage());
        assertEquals("000020", ex.getErrorCode().getCode());
    }

    @Test
    void messageCauseCodeStringConstructor_ShouldSetAll() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalException ex = new GlobalException("custom", cause, "000020");

        assertEquals("custom", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals("000020", ex.getErrorCode().getCode());
    }

    @Test
    void causeCodeStringConstructor_ShouldSetBoth() {
        RuntimeException cause = new RuntimeException("cause");
        GlobalException ex = new GlobalException(cause, "000020");

        assertSame(cause, ex.getCause());
        assertEquals("000020", ex.getErrorCode().getCode());
    }

    @Test
    void setRetryable_ShouldUpdateFlag() {
        GlobalException ex = new GlobalException();

        assertFalse(ex.isRetryable());

        ex.setRetryable(true);
        assertTrue(ex.isRetryable());

        ex.setRetryable(false);
        assertFalse(ex.isRetryable());
    }

    @Test
    void setRetryable_ShouldReturnThis() {
        GlobalException ex = new GlobalException();

        GlobalException result = ex.setRetryable(true);

        assertSame(ex, result);
    }

    @Test
    void toGlobalRuntimeException_ShouldConvert() {
        GlobalException ex = new GlobalException("test", BaseErrorCode.PARAM_ERROR);
        ex.setRetryable(true);

        GlobalRuntimeException result = GlobalException.toGlobalRuntimeException(ex);

        assertNotNull(result);
        assertEquals("test", result.getMessage());
        assertEquals(BaseErrorCode.PARAM_ERROR, result.getErrorCode());
        assertTrue(result.isRetryable());
    }

    @Test
    void toGlobalRuntimeException_ShouldReturnNull_WhenInputIsNull() {
        GlobalRuntimeException result = GlobalException.toGlobalRuntimeException(null);

        assertNull(result);
    }

    @Test
    void toString_ShouldFormatCorrectly() {
        GlobalException ex = new GlobalException("test", BaseErrorCode.SYSTEM_ERROR);

        String result = ex.toString();

        assertTrue(result.contains("[unknown]"));
        assertTrue(result.contains("000010"));
        assertTrue(result.contains("System Error"));
        assertTrue(result.contains("test"));
    }

    @Test
    void toString_ShouldHandleNullErrorCode() {
        GlobalException ex = new GlobalException((ErrorCode) null);

        String result = ex.toString();

        assertTrue(result.contains("[unknown]"));
        assertTrue(result.contains("unknown"));
    }
}