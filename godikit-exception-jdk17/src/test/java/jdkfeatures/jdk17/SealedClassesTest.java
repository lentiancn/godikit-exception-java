package jdkfeatures.jdk17;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JDK 17 permanent language feature test: Sealed Classes (JEP 409).
 * <p>
 * Compile and run with JDK 17. No preview flags are required.
 */
class SealedClassesTest {

    @Test
    void sealedClassRestrictsItsDirectSubclasses() {
        assertTrue(Shape.class.isSealed());

        Set<Class<?>> permitted = Arrays.stream(Shape.class.getPermittedSubclasses()).collect(Collectors.toSet());

        assertEquals(Set.of(Circle.class, Rectangle.class, ExtendableShape.class), permitted);
    }

    @Test
    void finalPermittedSubclassCannotBeExtended() {
        assertTrue(Modifier.isFinal(Circle.class.getModifiers()));
        assertTrue(Modifier.isFinal(Rectangle.class.getModifiers()));
        assertFalse(Circle.class.isSealed());
        assertFalse(Rectangle.class.isSealed());
    }

    @Test
    void sealedPermittedSubclassCanRestrictItsOwnChildren() {
        assertTrue(ExtendableShape.class.isSealed());

        Set<Class<?>> permitted = Arrays.stream(ExtendableShape.class.getPermittedSubclasses()).collect(Collectors.toSet());

        assertEquals(Set.of(RoundedShape.class), permitted);
    }

    @Test
    void sealedInterfaceRestrictsItsDirectImplementations() {
        assertTrue(Payment.class.isSealed());

        Set<Class<?>> permitted = Arrays.stream(Payment.class.getPermittedSubclasses()).collect(Collectors.toSet());

        assertEquals(Set.of(CardPayment.class, PayPalPayment.class, ExtensiblePayment.class), permitted);
    }

    @Test
    void nonSealedPermittedSubclassCanBeExtended() {
        assertFalse(ExtensiblePayment.class.isSealed());
        assertFalse(Modifier.isFinal(ExtensiblePayment.class.getModifiers()));
        assertTrue(new CustomPayment() instanceof ExtensiblePayment);
        assertTrue(new CustomPayment() instanceof Payment);
    }

    @Test
    void permittedTypesAreDirectSubtypes() {
        assertTrue(Shape.class.isAssignableFrom(Circle.class));
        assertTrue(Shape.class.isAssignableFrom(RoundedShape.class));

        // RoundedShape is an indirect subtype of Shape, not a direct permitted subtype.
        Set<Class<?>> directPermitted = Arrays.stream(Shape.class.getPermittedSubclasses()).collect(Collectors.toSet());

        assertFalse(directPermitted.contains(RoundedShape.class));
        assertTrue(directPermitted.contains(ExtendableShape.class));
    }

    // 3. sealed interface + permits
    sealed interface Payment permits CardPayment, PayPalPayment, ExtensiblePayment {
    }

    // 1. sealed class + permits + final direct subclass
    sealed abstract static class Shape permits Circle, Rectangle, ExtendableShape {
    }

    static final class Circle extends Shape {
    }

    static final class Rectangle extends Shape {
    }

    // 2. A permitted subclass may itself be sealed.
    sealed static class ExtendableShape extends Shape permits RoundedShape {
    }

    static final class RoundedShape extends ExtendableShape {
    }

    static final class CardPayment implements Payment {
    }

    static final class PayPalPayment implements Payment {
    }

    // 4. non-sealed explicitly reopens inheritance for this branch.
    non-sealed static class ExtensiblePayment implements Payment {
    }

    static final class CustomPayment extends ExtensiblePayment {
    }
}
