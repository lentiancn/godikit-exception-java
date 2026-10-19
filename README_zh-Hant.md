# godikit-exception

[![License](https://img.shields.io/github/license/lentiancn/godikit-exception-java.svg)](https://github.com/lentiancn/godikit-exception-java/blob/main/LICENSE)
[![Codecov](https://img.shields.io/codecov/c/github/lentiancn/godikit-exception-java.svg)](https://codecov.io/gh/lentiancn/godikit-exception-java)
[![GitHub Release](https://img.shields.io/github/tag/lentiancn/godikit-exception-java.svg?label=release)](https://github.com/lentiancn/godikit-exception-java/releases)

繁體中文 | [English](README.md) | [简体中文](README_zh-Hans.md)

## 為什麼選擇 godikit-exception？

| 場景      | 不使用 godikit-exception   | 使用 godikit-exception                     |
|---------|-------------------------|------------------------------------------|
| 錯誤碼管理   | 硬編碼常量分散在各個類別中           | SPI 註冊的枚舉型錯誤碼                            |
| 錯誤碼查詢   | 字串匹配或枚舉 switch 分支       | 直接存取枚舉並攜帶豐富元資料                           |
| 異常類型一致性 | 混合使用受檢/非受檢異常無規範         | GlobalException + GlobalRuntimeException |
| 錯誤訊息格式  | 各模組格式不一致                | 統一格式 `[來源] [代碼描述] 訊息`                    |
| 業務錯誤分類  | 到處使用通用 RuntimeException | 支援 Retryable 標記用於容錯系統                    |

## 功能特性

- **插件式錯誤碼**：JDK `ServiceLoader` 自動發現錯誤碼提供者，只需新增 JAR 即可
- **兩種異常類型**：`GlobalException`（受檢）和 `GlobalRuntimeException`（非受檢）
- **內建降級方案**：開箱即用的通用錯誤碼（`SYSTEM_ERROR`、`PARAM_ERROR` 等）
- **統一錯誤格式**：跨應用一致的錯誤訊息格式
- **堆疊追蹤工具**：一鍵將堆疊追蹤轉換為字串
- **可重試支援**：標記異常為可重試，用於容錯系統

## 架構設計

```
┌──────────────────────────────────────────────────────────────┐
│                         godikit-exception                     │
│  (核心 API: ErrorCode 介面 + GlobalException 類型)           │
│  內建錯誤碼: BaseErrorCode                                   │
└──────────────────────────────────────────────────────────────┘
                              ▲
                      ServiceLoader
                              │
┌──────────────────────────────────────────────────────────────┐
│                        你的應用                               │
│  自訂 ErrorCodeProvider → 註冊自訂錯誤碼                      │
│  透過 META-INF/services/ErrorCodeProvider 設定               │
└──────────────────────────────────────────────────────────────┘
```

## 環境要求

- Java 8+
- Maven 3.6+

## 使用範例

### Maven 依賴

```xml
<dependency>
  <groupId>com.godikit.exception</groupId>
  <artifactId>godikit-exception</artifactId>
  <version>1.0.0</version>
</dependency>
```

### 定義錯誤碼

```java
// 透過枚舉實作 ErrorCode 介面
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

### 註冊錯誤碼提供者（可選）

建立 SPI 設定檔：

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

### 拋出和捕获異常

```java
// 拋出非受檢異常
throw new GlobalRuntimeException(MyErrorCode.USER_NOT_FOUND);

// 或帶自訂訊息
throw new GlobalException("User id: 123", MyErrorCode.USER_NOT_FOUND);

// 異常類型轉換
GlobalRuntimeException runtimeEx = GlobalException.toGlobalRuntimeException(ex);
```

### 錯誤訊息格式

```
[unknown] [10001 User Not Found] java.lang.RuntimeException: User id: 123
```

> **注意**：錯誤來源預設為 "unknown"。格式為：`[errorSource] [code description] message`

### 堆疊追蹤工具

```java
// 將異常轉換為堆疊追蹤字串
String stackTrace = StackTraceUtils.toString(exception);
```

### 可重試異常

```java
// 標記異常為可重試
throw new GlobalRuntimeException(MyErrorCode.NETWORK_ERROR)
    .setRetryable(true);
```

## 內建錯誤碼

庫中包含 `BaseErrorCode` 中的通用錯誤碼：

| 代碼     | 名稱                  | 描述     |
|--------|---------------------|--------|
| 000000 | NO_ERROR            | 無錯誤    |
| 000010 | SYSTEM_ERROR        | 系統級錯誤  |
| 000011 | UNKNOWN_ERROR       | 未知錯誤   |
| 000020 | PARAM_ERROR         | 參數錯誤   |
| 000021 | PARAM_MISSING_ERROR | 必填參數缺失 |
| 000022 | PARAM_INVALID_ERROR | 參數值無效  |

## 授權條款

`godikit-exception` 基於 [MIT 授權條款](LICENSE) 發布。

## 相關連結

- 文件: https://www.godikit.com/exception
- 問題回饋: https://github.com/lentiancn/godikit-exception-java/issues
