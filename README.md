# godikit-error

[![License](https://img.shields.io/github/license/lentiancn/godikit-error-java.svg)](https://github.com/lentiancn/godikit-error-java/LICENSE)
[![Codecov](https://img.shields.io/codecov/c/github/lentiancn/godikit-error-java.svg)](https://codecov.io/gh/lentiancn/godikit-error-java)
[![GitHub Release](https://img.shields.io/github/tag/lentiancn/godikit-error-java.svg?label=release)](https://github.com/lentiancn/godikit-error-java/releases)

English

## Features

- **Plugin-based Error Codes**: Use Java SPI to register error codes dynamically
- **Two Exception Types**: `GlobalException` (checked) and `GlobalRuntimeException` (unchecked)
- **Error Source Tracking**: Track where errors originate (server, client, mobile, etc.)
- **Unified Error Format**: Consistent error message format across applications
- **Throwable Utilities**: Convert stack traces to strings easily

## Modules

| Module          | Purpose                                               |
|-----------------|-------------------------------------------------------|
| `godikit-error` | Core API (ErrorCode, GlobalException, ThrowableUtils) |

## Quick Start

### Maven

```xml
<dependency>
    <groupId>com.godikit.exception</groupId>
    <artifactId>godikit-error</artifactId>
    <version>1.0.2</version>
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
# src/main/resources/META-INF/services/com.godikit.exception.code.ErrorCodeProvider
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
[server-a] [10001 User Not Found] java.lang.RuntimeException: User id: 123
```

### Throwable Utils

```java
// Convert exception to stack trace string
String stackTrace = ThrowableUtils.toString(exception);
```

## Requirements

- Java 8+
- Maven 3.6+

## License

`godikit-error` is licensed under the [MIT License](LICENSE).

## Links

- Docs: https://www.godikit.com/error
- Issues: https://github.com/lentiancn/godikit-error-java/issues