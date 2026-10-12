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
package com.godikit.error.code;

import com.godikit.exception.errorcode.BaseErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class BaseErrorCodeTest {

    @Test
    void noError_ShouldHaveCorrectValues() {
        assertEquals("NO_ERROR", BaseErrorCode.NO_ERROR.getName());
        assertEquals("000000", BaseErrorCode.NO_ERROR.getCode());
        assertEquals("No Error", BaseErrorCode.NO_ERROR.getDescription());
    }

    @Test
    void systemError_ShouldHaveCorrectValues() {
        assertEquals("SYSTEM_ERROR", BaseErrorCode.SYSTEM_ERROR.getName());
        assertEquals("000010", BaseErrorCode.SYSTEM_ERROR.getCode());
        assertEquals("System Error", BaseErrorCode.SYSTEM_ERROR.getDescription());
    }

    @Test
    void unknownError_ShouldHaveCorrectValues() {
        assertEquals("UNKNOWN_ERROR", BaseErrorCode.UNKNOWN_ERROR.getName());
        assertEquals("000011", BaseErrorCode.UNKNOWN_ERROR.getCode());
        assertEquals("Unknown Error", BaseErrorCode.UNKNOWN_ERROR.getDescription());
    }

    @Test
    void paramError_ShouldHaveCorrectValues() {
        assertEquals("PARAM_ERROR", BaseErrorCode.PARAM_ERROR.getName());
        assertEquals("000020", BaseErrorCode.PARAM_ERROR.getCode());
        assertEquals("Parameter Error", BaseErrorCode.PARAM_ERROR.getDescription());
    }

    @Test
    void paramMissingError_ShouldHaveCorrectValues() {
        assertEquals("PARAM_MISSING_ERROR", BaseErrorCode.PARAM_MISSING_ERROR.getName());
        assertEquals("000021", BaseErrorCode.PARAM_MISSING_ERROR.getCode());
        assertEquals("Parameter Missing Error", BaseErrorCode.PARAM_MISSING_ERROR.getDescription());
    }

    @Test
    void paramInvalidError_ShouldHaveCorrectValues() {
        assertEquals("PARAM_INVALID_ERROR", BaseErrorCode.PARAM_INVALID_ERROR.getName());
        assertEquals("000022", BaseErrorCode.PARAM_INVALID_ERROR.getCode());
        assertEquals("Parameter Invalid Error", BaseErrorCode.PARAM_INVALID_ERROR.getDescription());
    }

    @ParameterizedTest
    @EnumSource(BaseErrorCode.class)
    void allCodes_ShouldImplementErrorCode(BaseErrorCode errorCode) {
        assertInstanceOf(ErrorCode.class, errorCode);
        assertNotNull(errorCode.getName());
        assertNotNull(errorCode.getCode());
        assertNotNull(errorCode.getDescription());
        assertFalse(errorCode.getCode().isEmpty());
        assertFalse(errorCode.getDescription().isEmpty());
    }

    @Test
    void allCodes_ShouldHaveUniqueCode() {
        long uniqueCount = java.util.Arrays.stream(BaseErrorCode.values())
                .map(BaseErrorCode::getCode)
                .distinct()
                .count();
        assertEquals(BaseErrorCode.values().length, uniqueCount);
    }

    @Test
    void allCodes_ShouldHaveValidSixDigitCode() {
        for (BaseErrorCode errorCode : BaseErrorCode.values()) {
            assertTrue(errorCode.getCode().matches("\\d{6}"),
                    "Code should be 6 digits: " + errorCode.getCode());
        }
    }

    @Test
    void systemError_ShouldBeDefaultError() {
        ErrorCode defaultError = BaseErrorCode.SYSTEM_ERROR;
        assertEquals("000010", defaultError.getCode());
    }
}