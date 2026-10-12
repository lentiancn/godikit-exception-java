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

import java.io.Serializable;

/**
 * Interface for error codes.
 * Defines the contract for error code implementation.
 *
 * @apiNote Implementations should ensure that {@link #getCode()} returns a unique identifier
 * across all registered error codes to avoid collisions in the registry.
 * @since 1.0.0
 */
public interface ErrorCode extends Serializable {

    /**
     * Returns the name of this error code.
     *
     * @return the error code name, typically the enum constant name
     * @apiNote For enum implementations, this typically returns {@code name()}.
     */
    String getName();

    /**
     * Returns the unique code identifier.
     *
     * @return the error code value, should be non-null and non-empty
     * @apiNote Code format should be consistent, e.g., 6-digit numeric codes.
     */
    String getCode();

    /**
     * Returns the human-readable description of this error.
     *
     * @return the error description, suitable for display to end users
     * @apiNote Descriptions should be clear and actionable when possible.
     */
    String getDescription();
}
