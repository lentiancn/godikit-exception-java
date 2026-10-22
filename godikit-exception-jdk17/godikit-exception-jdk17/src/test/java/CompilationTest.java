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

import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Verifies that the compiled CompilationTest class targets Java 17.
 * The test itself does not need to run on a Java 17 JVM.
 */
class CompilationTest {

    @Test
    void compiledClassShouldTargetJava17() throws IOException {
        try (InputStream resource = CompilationTest.class
            .getResourceAsStream("/CompilationTest.class")) {
            assertNotNull(resource, "Could not find CompilationTest.class on the classpath");

            try (DataInputStream input = new DataInputStream(resource)) {
                assertEquals(0xCAFEBABE, input.readInt(),
                    "Invalid class file: incorrect magic number");

                int minorVersion = input.readUnsignedShort();
                int majorVersion = input.readUnsignedShort();

                assertEquals(61, majorVersion,
                    "Expected Java 17 bytecode (major version 61), but found class file version "
                        + majorVersion + "." + minorVersion);
                assertEquals(0, minorVersion,
                    "Expected a minor version of 0 for a Java 17 class file");
            }
        }
    }
}
