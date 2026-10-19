# godikit-exception

[![License](https://img.shields.io/github/license/lentiancn/godikit-exception-java.svg)](https://github.com/lentiancn/godikit-exception-java/blob/main/LICENSE)
[![Codecov](https://img.shields.io/codecov/c/github/lentiancn/godikit-exception-java.svg)](https://codecov.io/gh/lentiancn/godikit-exception-java)
[![GitHub Release](https://img.shields.io/github/tag/lentiancn/godikit-exception-java.svg?label=release)](https://github.com/lentiancn/godikit-exception-java/releases)

English | [简体中文](README_zh-Hans.md) | [繁體中文](README_zh-Hant.md)

## Why godikit-exception?

| Scenario                      | Without godikit-exception                    | With godikit-exception                        |
|-------------------------------|----------------------------------------------|-----------------------------------------------|
| Error code management         | Hardcoded constants scattered across classes | SPI-registered enum-based codes               |
| Error code lookup             | String matching or enum switch blocks        | Direct enum access with rich metadata         |
| Exception type consistency    | Mixed checked/unchecked without rules        | GlobalException + GlobalRuntimeException      |
| Error message format          | Inconsistent across modules                  | Unified `[source] [code description] message` |
| Business error categorization | Generic RuntimeException everywhere          | Retryable flag for fault-tolerant systems     |

## Features

- **Plugin-based Error Codes**: JDK `ServiceLoader` auto-discovers error code providers — just add a JAR
- **Two Exception Types**: `GlobalException` (checked) and `GlobalRuntimeException` (unchecked)
- **Built-in Fallback**: Common error codes included out of the box (`SYSTEM_ERROR`, `PARAM_ERROR`, etc.)
- **Unified Error Format**: Consistent error message format across applications
- **Stack Trace Utilities**: Convert stack traces to strings with one call
- **Retryable Support**: Flag exceptions as retryable for fault-tolerant systems

## Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                         godikit-exception                     │
│  (Core API: ErrorCode interface + GlobalException types)     │
│  Built-in error codes: BaseErrorCode                         │
└──────────────────────────────────────────────────────────────┘
                              ▲
                      ServiceLoader
                              │
┌──────────────────────────────────────────────────────────────┐
│        Your Application                                       │
│  Custom ErrorCodeProvider → registers custom error codes       │
│  Configured via META-INF/services/ErrorCodeProvider          │
└──────────────────────────────────────────────────────────────┘
```

## Requirements

- Java 8+
- Maven 3.6+

## Usage

### Maven

```xml
<dependency>
  <groupId>com.godikit.exception</groupId>
  <artifactId>godikit-exception</artifactId>
  <version>1.0.0</version>
</dependency>
```

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

### Register Error Code Provider (Optional)

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
// Throw unchecked exception
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
    .setRetryable(true);
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

## License

`godikit-exception` is licensed under the [MIT License](LICENSE).

## Links

- Docs: https://www.godikit.com/exception
- Issues: https://github.com/lentiancn/godikit-exception-java/issues
