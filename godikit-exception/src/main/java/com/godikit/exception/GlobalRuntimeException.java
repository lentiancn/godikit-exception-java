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
import com.godikit.error.code.ErrorCodeRegistry;
import lombok.Getter;

import java.util.Optional;

import static com.godikit.exception.utils.StringUtils.defaultIfNull;

/**
 * Unchecked runtime exception for Godikit framework.
 * Carries error source information and associated error code.
 */
@Getter
public class GlobalRuntimeException extends RuntimeException {

    /**
     * The source/origin where the error occurred.
     */
    private final String errorSource;
    /**
     * The associated error code.
     */
    private final ErrorCode errorCode;
    /**
     * Indicates whether the error is retryable.
     */
    private boolean isRetryable;

    /**
     * Default constructor with SYSTEM_ERROR.
     */
    public GlobalRuntimeException() {
        super();
        this.errorSource = "unknown";
        this.errorCode = BaseErrorCode.SYSTEM_ERROR;
    }

    /**
     * Constructs an exception with a message.
     *
     * @param message the error message
     */
    public GlobalRuntimeException(String message) {
        super(defaultIfNull(message, BaseErrorCode.SYSTEM_ERROR.getDescription()));
        this.errorSource = "unknown";
        this.errorCode = BaseErrorCode.SYSTEM_ERROR;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with message and cause.
     *
     * @param message the error message
     * @param cause   the underlying cause
     */
    public GlobalRuntimeException(String message, Throwable cause) {
        super(defaultIfNull(message, BaseErrorCode.SYSTEM_ERROR.getDescription()), cause);
        this.errorSource = "unknown";
        this.errorCode = BaseErrorCode.SYSTEM_ERROR;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception from a cause.
     *
     * @param cause the underlying cause
     */
    public GlobalRuntimeException(Throwable cause) {
        super(cause);
        this.errorSource = "unknown";
        this.errorCode = BaseErrorCode.SYSTEM_ERROR;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with an error code.
     *
     * @param errorCode the error code
     */
    public GlobalRuntimeException(ErrorCode errorCode) {
        super(Optional.ofNullable(errorCode).map(ErrorCode::getDescription).orElse(null));
        this.errorSource = "unknown";
        this.errorCode = errorCode;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with message and error code.
     *
     * @param message   the error message
     * @param errorCode the error code
     */
    public GlobalRuntimeException(String message, ErrorCode errorCode) {
        super(defaultIfNull(message, Optional.ofNullable(errorCode).map(ErrorCode::getDescription).orElse(null)));
        this.errorSource = "unknown";
        this.errorCode = errorCode;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with message, cause, and error code.
     *
     * @param message   the error message
     * @param cause     the underlying cause
     * @param errorCode the error code
     */
    public GlobalRuntimeException(String message, Throwable cause, ErrorCode errorCode) {
        super(defaultIfNull(message, Optional.ofNullable(errorCode).map(ErrorCode::getDescription).orElse(null)), cause);
        this.errorSource = "unknown";
        this.errorCode = errorCode;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with cause and error code.
     *
     * @param cause     the underlying cause
     * @param errorCode the error code
     */
    public GlobalRuntimeException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorSource = "unknown";
        this.errorCode = errorCode;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with message and error code string.
     *
     * @param message the error message
     * @param code    the error code value
     */
    public GlobalRuntimeException(String message, String code) {
        super(defaultIfNull(message, ErrorCodeRegistry.getInstance().get(code, BaseErrorCode.SYSTEM_ERROR).getDescription()));
        this.errorSource = "unknown";
        this.errorCode = ErrorCodeRegistry.getInstance().get(code, BaseErrorCode.SYSTEM_ERROR);
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with message, cause, and error code string.
     *
     * @param message the error message
     * @param cause   the underlying cause
     * @param code    the error code value
     */
    public GlobalRuntimeException(String message, Throwable cause, String code) {
        super(defaultIfNull(message, ErrorCodeRegistry.getInstance().get(code, BaseErrorCode.SYSTEM_ERROR).getDescription()), cause);
        this.errorSource = "unknown";
        this.errorCode = ErrorCodeRegistry.getInstance().get(code, BaseErrorCode.SYSTEM_ERROR);
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with cause and error code string.
     *
     * @param cause the underlying cause
     * @param code  the error code value
     */
    public GlobalRuntimeException(Throwable cause, String code) {
        super(cause);
        this.errorSource = "unknown";
        this.errorCode = ErrorCodeRegistry.getInstance().get(code, BaseErrorCode.SYSTEM_ERROR);
        this.isRetryable = false;
    }

    /**
     * Converts this runtime exception to a checked exception.
     *
     * @param exception the exception to convert
     * @return the converted GlobalException, or null if input is null
     */
    public static GlobalException toGlobalException(GlobalRuntimeException exception) {
        if (exception == null) {
            return null;
        }
        return new GlobalException(
                exception.getMessage(),
                exception.getCause(),
                exception.getErrorCode()
        ).setRetryable(exception.isRetryable);
    }

    /**
     * Sets the retryable flag for this exception.
     *
     * @param retryable the retryable flag value
     * @return this exception instance
     */
    public GlobalRuntimeException setRetryable(boolean retryable) {
        this.isRetryable = retryable;
        return this;
    }

    /**
     * Returns a string representation of this exception.
     * Format: [errorSource] [code description] super.toString()
     *
     * @return the formatted error message
     */
    @Override
    public String toString() {
        String errorCodeStr;

        if (errorCode != null) {
            errorCodeStr = errorCode.getCode() + " " + errorCode.getDescription();
        } else {
            errorCodeStr = "unknown";
        }

        return "[" + errorSource + "]" +
                " [" + errorCodeStr + "] " +
                super.toString();
    }
}
