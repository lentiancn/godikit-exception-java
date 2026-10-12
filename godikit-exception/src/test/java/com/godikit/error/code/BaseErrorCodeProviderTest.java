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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaseErrorCodeProviderTest {

    private BaseErrorCodeProvider provider;
    private List<ErrorCode> codes;

    @BeforeEach
    void setUp() {
        provider = new BaseErrorCodeProvider();
        codes = new ArrayList<>();
    }

    @Test
    void register_ShouldAddAllBaseErrorCodes() {
        provider.register(codes);

        assertEquals(BaseErrorCode.values().length, codes.size());
    }

    @Test
    void register_ShouldContainNoError() {
        provider.register(codes);

        assertTrue(codes.contains(BaseErrorCode.NO_ERROR));
    }

    @Test
    void register_ShouldContainSystemError() {
        provider.register(codes);

        assertTrue(codes.contains(BaseErrorCode.SYSTEM_ERROR));
    }

    @Test
    void register_ShouldContainUnknownError() {
        provider.register(codes);

        assertTrue(codes.contains(BaseErrorCode.UNKNOWN_ERROR));
    }

    @Test
    void register_ShouldContainAllParamErrors() {
        provider.register(codes);

        assertTrue(codes.contains(BaseErrorCode.PARAM_ERROR));
        assertTrue(codes.contains(BaseErrorCode.PARAM_MISSING_ERROR));
        assertTrue(codes.contains(BaseErrorCode.PARAM_INVALID_ERROR));
    }

    @Test
    void register_ShouldAddToExistingList() {
        List<ErrorCode> existingCodes = new ArrayList<>();
        existingCodes.add(BaseErrorCode.NO_ERROR);

        provider.register(existingCodes);

        assertEquals(BaseErrorCode.values().length + 1, existingCodes.size());
        assertTrue(existingCodes.get(0).equals(BaseErrorCode.NO_ERROR));
    }

    @Test
    void implementsErrorCodeProvider() {
        assertInstanceOf(ErrorCodeProvider.class, provider);
    }
}