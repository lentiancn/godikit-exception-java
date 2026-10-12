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

import com.godikit.exception.spi.ErrorCodeRegistry;
import com.godikit.exception.spi.provider.BaseErrorCode;
import lombok.Getter;

import java.util.Optional;

import static com.godikit.exception.utils.StringUtils.defaultIfBlank;

/**
 * Checked exception for Godikit framework.
 * Carries error source information and associated error code.
 *
 * @apiNote This is a checked exception suitable for recoverable errors.
 *          For unrecoverable errors, consider using {@link GlobalRuntimeException}.
 * @see GlobalRuntimeException
 * @since 1.0.0
 */
@Getter
public class GlobalException extends Exception {

    /**
     * The source or origin module/class where the error occurred.
     * Defaults to {@code "unknown"} when not explicitly provided.
     *
     * @apiNote This field is currently reserved for future use.
     */
    private final String errorSource;
    /**
     * The associated error code for categorizing the exception.
     * May be null if no error code was provided during construction.
     */
    private final ErrorCode errorCode;
    /**
     * Indicates whether the error is retryable.
     * Can be modified via {@link #setRetryable(boolean)} after construction.
     */
    private boolean isRetryable;

    /**
     * Default constructor that creates an exception with SYSTEM_ERROR.
     * The error source defaults to "unknown" and retryable is false.
     */
    public GlobalException() {
        super();
        this.errorSource = "unknown";
        this.errorCode = BaseErrorCode.SYSTEM_ERROR;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with a custom message.
     * If message is null, falls back to SYSTEM_ERROR description.
     *
     * @param message the error message, may be null
     */
    public GlobalException(String message) {
        super(defaultIfBlank(message, BaseErrorCode.SYSTEM_ERROR.getDescription()));
        this.errorSource = "unknown";
        this.errorCode = BaseErrorCode.SYSTEM_ERROR;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with both message and cause.
     *
     * @param message the error message, may be null
     * @param cause   the underlying cause of this exception, may be null
     */
    public GlobalException(String message, Throwable cause) {
        super(defaultIfBlank(message, BaseErrorCode.SYSTEM_ERROR.getDescription()), cause);
        this.errorSource = "unknown";
        this.errorCode = BaseErrorCode.SYSTEM_ERROR;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception from a cause.
     * The message will be derived from the cause's message.
     *
     * @param cause the underlying cause of this exception, may be null
     */
    public GlobalException(Throwable cause) {
        super(cause);
        this.errorSource = "unknown";
        this.errorCode = BaseErrorCode.SYSTEM_ERROR;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with only an error code.
     *
     * @param errorCode the error code, may be null
     */
    public GlobalException(ErrorCode errorCode) {
        super(Optional.ofNullable(errorCode).map(ErrorCode::getDescription).orElse(null));
        this.errorSource = "unknown";
        this.errorCode = errorCode;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with both message and error code.
     *
     * @param message   the error message, may be null
     * @param errorCode the error code, may be null
     */
    public GlobalException(String message, ErrorCode errorCode) {
        super(defaultIfBlank(message, Optional.ofNullable(errorCode).map(ErrorCode::getDescription).orElse(null)));
        this.errorSource = "unknown";
        this.errorCode = errorCode;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with message, cause, and error code.
     *
     * @param message   the error message, may be null
     * @param cause     the underlying cause of this exception, may be null
     * @param errorCode the error code, may be null
     */
    public GlobalException(String message, Throwable cause, ErrorCode errorCode) {
        super(defaultIfBlank(message, Optional.ofNullable(errorCode).map(ErrorCode::getDescription).orElse(null)), cause);
        this.errorSource = "unknown";
        this.errorCode = errorCode;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with cause and error code.
     *
     * @param cause     the underlying cause of this exception, may be null
     * @param errorCode the error code, may be null
     */
    public GlobalException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorSource = "unknown";
        this.errorCode = errorCode;
        this.isRetryable = false;
    }

    /**
     * Constructs an exception with message and error code string.
     * The code is looked up in the ErrorCodeRegistry.
     *
     * @param message the error message, may be null
     * @param code    the error code value to lookup in registry
     */
    public GlobalException(String message, String code) {
        super(defaultIfBlank(message, ErrorCodeRegistry.getInstance().get(code, BaseErrorCode.SYSTEM_ERROR).getDescription()));
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
    public GlobalException(String message, Throwable cause, String code) {
        super(defaultIfBlank(message, ErrorCodeRegistry.getInstance().get(code, BaseErrorCode.SYSTEM_ERROR).getDescription()), cause);
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
    public GlobalException(Throwable cause, String code) {
        super(cause);
        this.errorSource = "unknown";
        this.errorCode = ErrorCodeRegistry.getInstance().get(code, BaseErrorCode.SYSTEM_ERROR);
        this.isRetryable = false;
    }

    /**
     * Converts this checked exception to a runtime exception.
     *
     * @param exception the exception to convert
     * @return the converted GlobalRuntimeException, or null if input is null
     */
    public static GlobalRuntimeException toGlobalRuntimeException(GlobalException exception) {
        if (exception == null) {
            return null;
        }
        return new GlobalRuntimeException(
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
    public GlobalException setRetryable(boolean retryable) {
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
