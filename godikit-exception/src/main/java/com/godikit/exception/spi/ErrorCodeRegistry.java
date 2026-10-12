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
package com.godikit.exception.spi;

import com.godikit.exception.ErrorCode;
import com.godikit.exception.spi.provider.ErrorCodeProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry for managing all error codes.
 * Uses SPI to auto-load error code providers at runtime.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCodeRegistry {

    /**
     * Map storing error codes by their code value.
     */
    private static final Map<String, ErrorCode> ERROR_CODE_MAP = new ConcurrentHashMap<>();

    /**
     * Map storing error codes by their name (for enum-based codes).
     */
    private static final Map<String, ErrorCode> NAME_MAP = new ConcurrentHashMap<>();

    /**
     * Singleton instance.
     */
    private static final ErrorCodeRegistry INSTANCE = new ErrorCodeRegistry();

    /**
     * Flag to track if initialization has completed.
     */
    private static volatile boolean initialized = false;

    /**
     * Returns the singleton instance of ErrorCodeRegistry.
     *
     * @return the singleton instance
     */
    public static ErrorCodeRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Initializes the registry by loading all ErrorCodeProvider implementations via SPI.
     * This method is thread-safe and will only execute once.
     */
    public synchronized void initialize() {
        if (initialized) {
            return;
        }

        ServiceLoader<ErrorCodeProvider> loader = ServiceLoader.load(ErrorCodeProvider.class);
        for (ErrorCodeProvider provider : loader) {
            List<ErrorCode> codes = new ArrayList<>();
            provider.register(codes);
            for (ErrorCode code : codes) {
                register(code);
            }
        }

        initialized = true;
    }

    /**
     * Registers an error code into the registry.
     *
     * @param code the error code to register
     */
    public void register(ErrorCode code) {
        if (code == null) {
            return;
        }
        ERROR_CODE_MAP.put(code.getCode(), code);
        if (code instanceof Enum) {
            NAME_MAP.put(((Enum<?>) code).name(), code);
        }
    }

    /**
     * Retrieves an error code by its code value or name.
     *
     * @param codeOrName the error code value or name
     * @return the error code, or null if not found
     */
    public ErrorCode get(String codeOrName) {
        if (!initialized) {
            initialize();
        }
        ErrorCode errorCode = ERROR_CODE_MAP.get(codeOrName);

        return errorCode != null ? errorCode : NAME_MAP.get(codeOrName);
    }

    /**
     * Retrieves an error code by its code value, or returns a default if not found.
     *
     * @param code the error code value
     * @param def  the default error code to return if not found
     * @return the error code, or the default if not found
     */
    public ErrorCode get(String code, ErrorCode def) {
        ErrorCode errorCode = get(code);
        if (errorCode != null) {
            return errorCode;
        }
        return def;
    }

    /**
     * Returns all registered error codes.
     *
     * @return a list of all registered error codes
     */
    public List<ErrorCode> getAll() {
        if (!initialized) {
            initialize();
        }
        return new ArrayList<>(ERROR_CODE_MAP.values());
    }

    /**
     * Checks if an error code is registered.
     *
     * @param code the error code value to check
     * @return true if the error code is registered, false otherwise
     */
    public boolean contains(String code) {
        if (!initialized) {
            initialize();
        }
        return ERROR_CODE_MAP.containsKey(code);
    }
}
