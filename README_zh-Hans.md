# godikit-exception

[![License](https://img.shields.io/github/license/lentiancn/godikit-exception-java.svg)](https://github.com/lentiancn/godikit-exception-java/blob/main/LICENSE)
[![Codecov](https://img.shields.io/codecov/c/github/lentiancn/godikit-exception-java.svg)](https://codecov.io/gh/lentiancn/godikit-exception-java)
[![GitHub Release](https://img.shields.io/github/tag/lentiancn/godikit-exception-java.svg?label=release)](https://github.com/lentiancn/godikit-exception-java/releases)

简体中文 | [繁體中文](README_zh-Hant.md) | [English](README.md)

## 为什么选择 godikit-exception？

| 场景      | 不使用 godikit-exception   | 使用 godikit-exception                     |
|---------|-------------------------|------------------------------------------|
| 错误码管理   | 硬编码常量分散在各个类中            | SPI 注册的枚举型错误码                            |
| 错误码查询   | 字符串匹配或枚举 switch 分支      | 直接访问枚举并携带丰富元数据                           |
| 异常类型一致性 | 混合使用受检/非受检异常无规范         | GlobalException + GlobalRuntimeException |
| 错误消息格式  | 各模块格式不一致                | 统一格式 `[来源] [代码描述] 消息`                    |
| 业务错误分类  | 到处使用通用 RuntimeException | 支持 Retryable 标记用于容错系统                    |

## 功能特性

- **插件式错误码**：JDK `ServiceLoader` 自动发现错误码提供者，只需添加 JAR 即可

- **两种异常类型**：`GlobalException`（受检）和 `GlobalRuntimeException`（非受检）

- **内置降级方案**：开箱即用的通用错误码（`SYSTEM_ERROR`、`PARAM_ERROR` 等）

- **统一错误格式**：跨应用一致的错误消息格式

- **堆栈跟踪工具**：一键将堆栈跟踪转换为字符串

- **可重试支持**：标记异常为可重试，用于容错系统

## 架构设计

```
┌──────────────────────────────────────────────────────────────┐
│                         godikit-exception                     │
│  (核心 API: ErrorCode 接口 + GlobalException 类型)           │
│  内置错误码: BaseErrorCode                                   │
└──────────────────────────────────────────────────────────────┘
                              ▲
                      ServiceLoader
                              │
┌──────────────────────────────────────────────────────────────┐
│                        你的应用                               │
│  自定义 ErrorCodeProvider → 注册自定义错误码                   │
│  通过 META-INF/services/ErrorCodeProvider 配置               │
└──────────────────────────────────────────────────────────────┘
```

## 环境要求

- Java 8+
- Maven 3.6+

## 使用示例

### Maven 依赖

```xml
<dependency>
  <groupId>com.godikit.exception</groupId>
  <artifactId>godikit-exception</artifactId>
  <version>1.0.0</version>
</dependency>
```

### 定义错误码

```java
// 通过枚举实现 ErrorCode 接口
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

### 注册错误码提供者（可选）

创建 SPI 配置文件：

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

### 抛出和捕获异常

```java
// 抛出非受检异常
throw new GlobalRuntimeException(MyErrorCode.USER_NOT_FOUND);

// 或带自定义消息
throw new GlobalException("User id: 123", MyErrorCode.USER_NOT_FOUND);

// 异常类型转换
GlobalRuntimeException runtimeEx = GlobalException.toGlobalRuntimeException(ex);
```

### 错误消息格式

```
[unknown] [10001 User Not Found] java.lang.RuntimeException: User id: 123
```

> **注意**：错误来源默认为 "unknown"。格式为：`[errorSource] [code description] message`

### 堆栈跟踪工具

```java
// 将异常转换为堆栈跟踪字符串
String stackTrace = StackTraceUtils.toString(exception);
```

### 可重试异常

```java
// 标记异常为可重试
throw new GlobalRuntimeException(MyErrorCode.NETWORK_ERROR)
    .setRetryable(true);
```

## 内置错误码

库中包含 `BaseErrorCode` 中的通用错误码：

| 代码     | 名称                  | 描述     |
|--------|---------------------|--------|
| 000000 | NO_ERROR            | 无错误    |
| 000010 | SYSTEM_ERROR        | 系统级错误  |
| 000011 | UNKNOWN_ERROR       | 未知错误   |
| 000020 | PARAM_ERROR         | 参数错误   |
| 000021 | PARAM_MISSING_ERROR | 必填参数缺失 |
| 000022 | PARAM_INVALID_ERROR | 参数值无效  |

## 许可证

`godikit-exception` 基于 [MIT 许可证](LICENSE) 发布。

## 相关链接

- 文档: https://www.godikit.com/exception
- 问题反馈: https://github.com/lentiancn/godikit-exception-java/issues
