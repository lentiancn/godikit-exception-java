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
