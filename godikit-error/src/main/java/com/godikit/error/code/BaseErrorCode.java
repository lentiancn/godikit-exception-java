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

import lombok.Getter;

/**
 * Base error codes for the Godikit framework.
 * Provides common system-level and parameter error codes.
 */
@Getter
public enum BaseErrorCode implements ErrorCode {

    /**
     * No error, operation completed successfully.
     */
    NO_ERROR("000000", "No Error"),

    /**
     * System-level internal error.
     */
    SYSTEM_ERROR("000010", "System Error"),

    /**
     * Unknown error that cannot be classified.
     */
    UNKNOWN_ERROR("000011", "Unknown Error"),

    /**
     * General parameter validation error.
     */
    PARAM_ERROR("000020", "Parameter Error"),

    /**
     * Required parameter is missing.
     */
    PARAM_MISSING_ERROR("000021", "Parameter Missing Error"),

    /**
     * Parameter value is invalid.
     */
    PARAM_INVALID_ERROR("000022", "Parameter Invalid Error");

    private final String name;
    private final String code;
    private final String description;

    BaseErrorCode(String code, String description) {
        this.name = name();
        this.code = code;
        this.description = description;
    }
}
