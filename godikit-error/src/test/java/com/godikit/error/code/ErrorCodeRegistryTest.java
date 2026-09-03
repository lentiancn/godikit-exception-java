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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ErrorCodeRegistryTest {

    private ErrorCodeRegistry registry;

    @BeforeEach
    void setUp() throws Exception {
        registry = ErrorCodeRegistry.getInstance();
        resetRegistry();
    }

    @AfterEach
    void tearDown() throws Exception {
        resetRegistry();
    }

    private void resetRegistry() throws Exception {
        Field initializedField = ErrorCodeRegistry.class.getDeclaredField("initialized");
        initializedField.setAccessible(true);
        initializedField.setBoolean(registry, false);

        Field codeMapField = ErrorCodeRegistry.class.getDeclaredField("CODE_MAP");
        codeMapField.setAccessible(true);
        ((Map<?, ?>) codeMapField.get(registry)).clear();

        Field nameMapField = ErrorCodeRegistry.class.getDeclaredField("NAME_MAP");
        nameMapField.setAccessible(true);
        ((Map<?, ?>) nameMapField.get(registry)).clear();
    }

    @Test
    void getInstance_ShouldReturnSingleton() {
        ErrorCodeRegistry instance1 = ErrorCodeRegistry.getInstance();
        ErrorCodeRegistry instance2 = ErrorCodeRegistry.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    void initialize_ShouldRegisterBaseErrorCodes() {
        registry.initialize();
        List<ErrorCode> codes = registry.getAll();

        assertFalse(codes.isEmpty());
        assertTrue(codes.contains(BaseErrorCode.SYSTEM_ERROR));
    }

    @Test
    void initialize_ShouldOnlyExecuteOnce() {
        registry.initialize();
        int size1 = registry.getAll().size();

        registry.initialize();
        int size2 = registry.getAll().size();

        assertEquals(size1, size2);
    }

    @Test
    void register_ShouldAddErrorCode() {
        registry.initialize();
        int size = registry.getAll().size();

        registry.register(new TestErrorCode("888888", "Test"));

        assertEquals(size + 1, registry.getAll().size());
        assertTrue(registry.contains("888888"));
    }

    @Test
    void register_ShouldIgnoreNull() {
        registry.initialize();
        int size = registry.getAll().size();

        registry.register(null);

        assertEquals(size, registry.getAll().size());
    }

    @Test
    void register_ShouldAddToNameMap_WhenEnum() {
        registry.initialize();
        int size = registry.getAll().size();

        registry.register(BaseErrorCode.UNKNOWN_ERROR);

        assertEquals(size, registry.getAll().size());
        assertNotNull(registry.get("UNKNOWN_ERROR"));
    }

    @Test
    void get_ShouldReturnCodeByCodeValue() {
        registry.initialize();

        ErrorCode result = registry.get("000010");

        assertNotNull(result);
        assertEquals("000010", result.getCode());
        assertEquals("System Error", result.getDescription());
    }

    @Test
    void get_ShouldReturnCodeByName() {
        registry.initialize();

        ErrorCode result = registry.get("SYSTEM_ERROR");

        assertNotNull(result);
        assertEquals("SYSTEM_ERROR", result.getName());
    }

    @Test
    void get_ShouldReturnNull_WhenNotFound() {
        registry.initialize();

        ErrorCode result = registry.get("NOT_EXIST");

        assertNull(result);
    }

    @Test
    void get_ShouldAutoInitialize_WhenNotInitialized() {
        ErrorCode result = registry.get("000010");

        assertNotNull(result);
        assertEquals("System Error", result.getDescription());
    }

    @Test
    void get_WithDefault_ShouldReturnDefault_WhenNotFound() {
        registry.initialize();

        ErrorCode result = registry.get("999999", BaseErrorCode.UNKNOWN_ERROR);

        assertSame(BaseErrorCode.UNKNOWN_ERROR, result);
    }

    @Test
    void get_WithDefault_ShouldReturnFoundCode() {
        registry.initialize();

        ErrorCode result = registry.get("000010", BaseErrorCode.UNKNOWN_ERROR);

        assertSame(BaseErrorCode.SYSTEM_ERROR, result);
    }

    @Test
    void getAll_ShouldReturnAllCodes() {
        registry.initialize();
        List<ErrorCode> codes = registry.getAll();

        assertFalse(codes.isEmpty());
        assertEquals(BaseErrorCode.values().length, codes.size());
    }

    @Test
    void getAll_ShouldReturnCopy() {
        registry.initialize();
        List<ErrorCode> codes1 = registry.getAll();
        List<ErrorCode> codes2 = registry.getAll();

        assertNotSame(codes1, codes2);
    }

    @Test
    void getAll_ShouldAutoInitialize() {
        List<ErrorCode> codes = registry.getAll();

        assertFalse(codes.isEmpty());
    }

    @Test
    void contains_ShouldReturnTrue_WhenRegistered() {
        registry.initialize();

        assertTrue(registry.contains("000010"));
        assertTrue(registry.contains("000020"));
    }

    @Test
    void contains_ShouldReturnFalse_WhenNotRegistered() {
        registry.initialize();

        assertFalse(registry.contains("999999"));
    }

    @Test
    void contains_ShouldAutoInitialize() {
        assertTrue(registry.contains("000010"));
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