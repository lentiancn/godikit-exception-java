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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorCodeRegistryTest {

    private ErrorCodeRegistry registry;

    @BeforeEach
    void setUp() {
        registry = ErrorCodeRegistry.getInstance();
    }

    @Test
    void getInstance_ShouldReturnSingleton() {
        ErrorCodeRegistry instance1 = ErrorCodeRegistry.getInstance();
        ErrorCodeRegistry instance2 = ErrorCodeRegistry.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    void register_ShouldAddErrorCodeToMap() {
        registry.initialize();
        int initialSize = registry.getAll().size();

        ErrorCode customCode = new TestErrorCode("999999", "Test Error");
        registry.register(customCode);

        assertEquals(initialSize + 1, registry.getAll().size());
        assertTrue(registry.contains("999999"));
    }

    @Test
    void register_ShouldIgnoreNullCode() {
        registry.initialize();
        int initialSize = registry.getAll().size();

        registry.register(null);

        assertEquals(initialSize, registry.getAll().size());
    }

    @Test
    void get_ShouldReturnCodeByValue() {
        registry.initialize();

        ErrorCode result = registry.get("000020");

        assertNotNull(result);
        assertEquals("000020", result.getCode());
        assertEquals("Parameter Error", result.getDescription());
    }

    @Test
    void get_ShouldReturnCodeByName() {
        registry.initialize();

        ErrorCode result = registry.get("SYSTEM_ERROR");

        assertNotNull(result);
        assertEquals("SYSTEM_ERROR", result.getName());
        assertEquals("000010", result.getCode());
    }

    @Test
    void get_ShouldReturnDefaultWhenNotFound() {
        registry.initialize();

        ErrorCode result = registry.get("NOT_EXIST", BaseErrorCode.SYSTEM_ERROR);

        assertSame(BaseErrorCode.SYSTEM_ERROR, result);
    }

    @Test
    void get_ShouldReturnNullWhenNotFoundWithoutDefault() {
        registry.initialize();

        ErrorCode result = registry.get("NOT_EXIST");

        assertNull(result);
    }

    @Test
    void get_ShouldAutoInitializeOnFirstCall() {
        ErrorCode result = registry.get("000000");

        assertNotNull(result);
        assertEquals("NO_ERROR", result.getName());
    }

    @Test
    void contains_ShouldReturnTrueForRegisteredCode() {
        registry.initialize();

        assertTrue(registry.contains("000010"));
        assertTrue(registry.contains("000020"));
    }

    @Test
    void contains_ShouldReturnFalseForUnregisteredCode() {
        registry.initialize();

        assertFalse(registry.contains("888888"));
        assertFalse(registry.contains("NOT_EXIST"));
    }

    @Test
    void getAll_ShouldReturnAllRegisteredCodes() {
        registry.initialize();
        List<ErrorCode> allCodes = registry.getAll();

        assertNotNull(allCodes);
        assertFalse(allCodes.isEmpty());
        assertTrue(allCodes.size() >= BaseErrorCode.values().length);
    }

    @Test
    void getAll_ShouldContainBaseErrorCodes() {
        registry.initialize();
        List<ErrorCode> allCodes = registry.getAll();

        assertTrue(allCodes.contains(BaseErrorCode.SYSTEM_ERROR));
        assertTrue(allCodes.contains(BaseErrorCode.PARAM_ERROR));
    }

    @Test
    void initialize_ShouldOnlyExecuteOnce() {
        registry.initialize();
        int sizeAfterFirstInit = registry.getAll().size();

        registry.initialize();
        int sizeAfterSecondInit = registry.getAll().size();

        assertEquals(sizeAfterFirstInit, sizeAfterSecondInit);
    }

    @Test
    void register_ShouldOverrideExistingCode() {
        registry.initialize();

        ErrorCode original = registry.get("000010");
        assertEquals("System Error", original.getDescription());

        ErrorCode newCode = new TestErrorCode("000010", "Overridden Error");
        registry.register(newCode);

        ErrorCode updated = registry.get("000010");
        assertEquals("Overridden Error", updated.getDescription());
    }

    private static class TestErrorCode implements ErrorCode {
        private final String code;
        private final String description;

        TestErrorCode(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String getName() {
            return "TEST_ERROR";
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}