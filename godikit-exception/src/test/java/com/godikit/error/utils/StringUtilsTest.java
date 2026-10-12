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

class StringUtilsTest {

    @Test
    void defaultIfNull_ShouldReturnValue_WhenNotNull() {
        String result = StringUtils.defaultIfNull("actual", "default");

        assertEquals("actual", result);
    }

    @Test
    void defaultIfNull_ShouldReturnDefault_WhenValueIsNull() {
        String result = StringUtils.defaultIfNull(null, "default");

        assertEquals("default", result);
    }

    @Test
    void defaultIfNull_ShouldReturnNullDefault_WhenValueIsNull() {
        String result = StringUtils.defaultIfNull(null, null);

        assertNull(result);
    }

    @Test
    void defaultIfNull_ShouldReturnEmptyString_WhenValueIsEmpty() {
        String result = StringUtils.defaultIfNull("", "default");

        assertEquals("", result);
    }
}