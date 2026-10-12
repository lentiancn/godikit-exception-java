package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JDK 8 新特性测试：CompletableFuture。
 * 覆盖 supplyAsync/runAsync、thenApply/thenAccept/thenCompose、组合与异常处理。
 */
class CompletableFutureTest {

    @Test
    void supplyAsync() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
        assertEquals("Hello", future.get());
    }

    @Test
    void thenApplyChain() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello")
                .thenApply(String::toUpperCase)
                .thenApply(s -> s + " WORLD");
        assertEquals("HELLO WORLD", future.get());
    }

    @Test
    void thenAccept() throws Exception {
        StringBuilder sb = new StringBuilder();
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> "done")
                .thenAccept(sb::append);
        future.get();
        assertEquals("done", sb.toString());
    }

    @Test
    void thenCompose() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
        assertEquals("Hello World", future.get());
    }

    @Test
    void thenCombine() throws Exception {
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> "World");

        CompletableFuture<String> combined = first.thenCombine(second, (a, b) -> a + " " + b);
        assertEquals("Hello World", combined.get());
    }

    @Test
    void allOf() throws Exception {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "A");
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "B");
        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> "C");

        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2, f3);
        all.get();

        assertTrue(f1.isDone() && f2.isDone() && f3.isDone());
    }

    @Test
    void exceptionHandling() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Boom");
        }).exceptionally(ex -> "recovered");

        assertEquals("recovered", future.get());
    }

    @Test
    void handleException() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("error");
        }).handle((result, ex) -> ex != null ? "handled" : result);

        assertEquals("handled", future.get());
    }

    @Test
    void completeWithTimeout() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "result";
        });
        String result = future.get(5, TimeUnit.SECONDS);
        assertEquals("result", result);
    }
}