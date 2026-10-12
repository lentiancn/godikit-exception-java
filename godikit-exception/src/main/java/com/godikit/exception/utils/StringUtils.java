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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class for string operations.
 *
 * @since 1.0.0
 * @deprecated Use org.apache.commons.lang3.StringUtils instead.
 */
@Deprecated
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    /**
     * Returns the value if not blank, otherwise returns the default.
     *
     * <p>A string is considered blank if it is null, empty, or contains only whitespace.</p>
     *
     * @param s   the string to check
     * @param def the default value to return if string is blank
     * @return the string if not blank, otherwise the default value
     * @deprecated Use org.apache.commons.lang3.StringUtils.defaultIfBlank() instead.
     */
    @Deprecated
    public static String defaultIfBlank(String s, String def) {
        return isBlank(s) ? def : s;
    }

    /**
     * Checks if a string is blank.
     *
     * <p>A string is blank if it is null, empty, or contains only whitespace characters.</p>
     *
     * @param s the string to check
     * @return true if the string is blank, false otherwise
     * @deprecated Use org.apache.commons.lang3.StringUtils.isBlank() instead.
     */
    @Deprecated
    public static boolean isBlank(String s) {
        return (s == null) || s.trim().isEmpty();
    }
}
