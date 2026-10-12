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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    @Test
    void defaultIfBlank_ShouldReturnValue_WhenNotBlank() {
        String result = StringUtils.defaultIfBlank("actual", "default");

        assertEquals("actual", result);
    }

    @Test
    void defaultIfBlank_ShouldReturnDefault_WhenValueIsNull() {
        String result = StringUtils.defaultIfBlank(null, "default");

        assertEquals("default", result);
    }

    @Test
    void defaultIfBlank_ShouldReturnDefault_WhenValueIsEmpty() {
        String result = StringUtils.defaultIfBlank("", "default");

        assertEquals("default", result);
    }

    @Test
    void defaultIfBlank_ShouldReturnDefault_WhenValueIsWhitespace() {
        String result = StringUtils.defaultIfBlank("   ", "default");

        assertEquals("default", result);
    }

    @Test
    void defaultIfBlank_ShouldReturnNullDefault_WhenValueIsNull() {
        String result = StringUtils.defaultIfBlank(null, null);

        assertNull(result);
    }

    @Test
    void isBlank_ShouldReturnFalse_WhenValueIsNotBlank() {
        assertTrue(StringUtils.isBlank("hello") == false);
    }

    @Test
    void isBlank_ShouldReturnTrue_WhenValueIsNull() {
        assertTrue(StringUtils.isBlank(null));
    }

    @Test
    void isBlank_ShouldReturnTrue_WhenValueIsEmpty() {
        assertTrue(StringUtils.isBlank(""));
    }

    @Test
    void isBlank_ShouldReturnTrue_WhenValueIsWhitespace() {
        assertTrue(StringUtils.isBlank("   "));
    }
}
