# godikit-exception

[![License](https://img.shields.io/github/license/lentiancn/godikit-exception-java.svg)](https://github.com/lentiancn/godikit-exception-java/blob/main/LICENSE)
[![Codecov](https://img.shields.io/codecov/c/github/lentiancn/godikit-exception-java.svg)](https://codecov.io/gh/lentiancn/godikit-exception-java)
[![GitHub Release](https://img.shields.io/github/tag/lentiancn/godikit-exception-java.svg?label=release)](https://github.com/lentiancn/godikit-exception-java/releases)

English

## Features

- **Plugin-based Error Codes**: Use Java SPI to register error codes dynamically
- **Two Exception Types**: `GlobalException` (checked) and `GlobalRuntimeException` (unchecked)
- **Built-in Error Codes**: Common error codes included (`SYSTEM_ERROR`, `PARAM_ERROR`, etc.)
- **Unified Error Format**: Consistent error message format across applications
- **Stack Trace Utilities**: Convert stack traces to strings easily
- **Retryable Support**: Flag exceptions as retryable for fault-tolerant systems

## Modules

| Module              | Purpose                                                                        |
|---------------------|--------------------------------------------------------------------------------|
| `godikit-exception` | Core API (ErrorCode, GlobalException, GlobalRuntimeException, StackTraceUtils) |

## Quick Start

### Maven

```xml
<dependency>
    <groupId>com.godikit.exception</groupId>
    <artifactId>godikit-exception</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

### Define Error Codes

```java
// Implement ErrorCode interface with enum
public enum MyErrorCode implements ErrorCode {
    USER_NOT_FOUND("10001", "User Not Found"),
    INVALID_PARAM("10002", "Invalid Parameter");

    private final String code;
    private final String description;

    MyErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getName() {
        return name();
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
```

### Register Error Code Provider

Create SPI configuration file:

```
# src/main/resources/META-INF/services/com.godikit.exception.spi.provider.ErrorCodeProvider
com.example.myapp.MyErrorCodeProvider
```

```java
public class MyErrorCodeProvider implements ErrorCodeProvider {
    @Override
    public void register(List<ErrorCode> codes) {
        codes.add(MyErrorCode.USER_NOT_FOUND);
        codes.add(MyErrorCode.INVALID_PARAM);
    }
}
```

### Throw and Catch Exceptions

```java
// Throw exception
throw new GlobalRuntimeException(MyErrorCode.USER_NOT_FOUND);

// Or with message
throw new GlobalException("User id: 123", MyErrorCode.USER_NOT_FOUND);

// Convert between exception types
GlobalRuntimeException runtimeEx = GlobalException.toGlobalRuntimeException(ex);
```

### Error Message Format

```
[unknown] [10001 User Not Found] java.lang.RuntimeException: User id: 123
```

> **Note**: The error source defaults to "unknown". The format is: `[errorSource] [code description] message`

### StackTrace Utils

```java
// Convert exception to stack trace string
String stackTrace = StackTraceUtils.toString(exception);
```

### Retryable Exceptions

```java
// Mark an exception as retryable
throw new GlobalRuntimeException(MyErrorCode.NETWORK_ERROR)
    .

setRetryable(true);
```

## Built-in Error Codes

The library includes common error codes in `BaseErrorCode`:

| Code   | Name                | Description            |
|--------|---------------------|------------------------|
| 000000 | NO_ERROR            | No error               |
| 000010 | SYSTEM_ERROR        | System-level error     |
| 000011 | UNKNOWN_ERROR       | Unknown error          |
| 000020 | PARAM_ERROR         | Parameter error        |
| 000021 | PARAM_MISSING_ERROR | Required param missing |
| 000022 | PARAM_INVALID_ERROR | Invalid param value    |

## Requirements

- Java 8+
- Maven 3.6+

## License

`godikit-exception` is licensed under the [MIT License](LICENSE).

## Links

- Docs: https://www.godikit.com/exception
- Issues: https://github.com/lentiancn/godikit-exception-java/issues
