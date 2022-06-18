package com.gstuer.modelmerging.framework.surrogate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

public abstract class ReplaceableTest<T extends Replaceable> {
    @Test
    public void testIsPlaceholderNonPlaceholder() {
        // Test data
        T entity = getUniqueNonPlaceholder();

        // Assertions
        assertFalse(entity.isPlaceholder());
    }

    @Test
    public void testIsPlaceholderWithPlaceholder() {
        // Test data
        T entity = getPlaceholderOf(getUniqueNonPlaceholder());

        // Assertions
        assertTrue(entity.isPlaceholder());
    }

    @Test
    public void testCanReplaceEqualReplaceable() {
        // Test data
        T nonPlaceholder = getUniqueNonPlaceholder();
        T placeholder = getPlaceholderOf(getUniqueNonPlaceholder());

        // Assertions
        assertTrue(nonPlaceholder.canReplace(nonPlaceholder));
        assertTrue(placeholder.canReplace(placeholder));
    }

    @Test
    public void testCanReplaceNull() {
        // Test data
        T entity = getPlaceholderOf(getUniqueNonPlaceholder());

        // Assertions
        assertFalse(entity.canReplace(null));
    }

    @Test
    public void testReplaceNullOriginal() {
        // Test data
        T original = getUniqueNonPlaceholder();
        T replacement = getUniqueNonPlaceholder();

        // Assertions
        assertThrows(IllegalArgumentException.class, () -> original.replace(null, replacement));
    }

    @Test
    public void testReplaceEqualOriginalEqualReplacement() {
        // Test data
        T original = getUniqueNonPlaceholder();

        // Execution
        Replaceable replaceResult = original.replace(original, original);

        // Assertions
        assertEquals(original, replaceResult);
    }

    @Test
    public void testReplaceEqualOriginalOtherReplacementSameType() {
        // Test data
        T replacement = getUniqueNonPlaceholder();
        T original = getPlaceholderOf(replacement);

        // Execution
        Replaceable replaceResult = original.replace(original, replacement);

        // Assertions
        assertEquals(replacement, replaceResult);
    }

    @Test
    public void testIsPlaceholderOfEqualNonPlaceholderReplaceable() {
        // Test data
        T entity = getUniqueNonPlaceholder();

        // Assertions
        assertFalse(entity.isPlaceholderOf(entity));
    }

    @Test
    public void testIsPlaceholderOfEqualPlaceholderReplaceable() {
        // Test data
        T entity = getPlaceholderOf(getUniqueNonPlaceholder());

        // Assertions
        assertFalse(entity.isPlaceholderOf(entity));
    }

    @Test
    public void testIsPlaceholderOfValidPlaceholderWrongDirection() {
        // Test data
        T replacement = getUniqueNonPlaceholder();
        T original = getPlaceholderOf(replacement);

        // Assertions
        assertFalse(replacement.isPlaceholderOf(original));
    }

    @Test
    public void testIsPlaceholderOfValidPlaceholder() {
        // Test data
        T replacement = getUniqueNonPlaceholder();
        T original = getPlaceholderOf(replacement);

        // Assertions
        assertTrue(original.isPlaceholderOf(replacement));
    }

    @Test
    public void testIsPlaceholderDifferentType() {
        // Test data
        T nonPlaceholder = getUniqueNonPlaceholder();
        T placeholder = getPlaceholderOf(nonPlaceholder);
        SimpleReplaceable differentTypeReplaceable = new SimpleReplaceable(false);

        // Assertions
        assertFalse(nonPlaceholder.isPlaceholderOf(differentTypeReplaceable));
        assertFalse(placeholder.isPlaceholderOf(differentTypeReplaceable));
    }

    @Test
    public void testEqualsNull() {
        // Test data
        T entity = getUniqueNonPlaceholder();

        // Assertions
        assertFalse(entity.equals(null));
    }

    @Test
    public void testEqualsAndHashCodeSameReference() {
        // Test data
        T entity = getUniqueNonPlaceholder();

        // Assertions
        assertTrue(entity.equals(entity));
        assertEquals(entity.hashCode(), entity.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeDifferentType() {
        // Test data
        T entity = getUniqueNonPlaceholder();
        SimpleReplaceable differentTypeReplaceable = new SimpleReplaceable(false);

        // Assertions
        assertFalse(entity.equals(differentTypeReplaceable));
        assertNotEquals(entity.hashCode(), differentTypeReplaceable.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeUnequalReplaceables() {
        // Test data
        T firstEntity = getUniqueNonPlaceholder();
        T secondEntity = getUniqueNonPlaceholder();
        T thirdEntity = getPlaceholderOf(getUniqueNonPlaceholder());
        T fourthEntity = getPlaceholderOf(getUniqueNonPlaceholder());

        // Assertions - Non placeholder entities
        assertFalse(firstEntity.equals(secondEntity));
        assertFalse(secondEntity.equals(firstEntity));
        assertNotEquals(firstEntity.hashCode(), secondEntity.hashCode());

        // Assertions - Mixed entities
        assertFalse(firstEntity.equals(fourthEntity));
        assertFalse(fourthEntity.equals(secondEntity));
        assertNotEquals(firstEntity.hashCode(), fourthEntity.hashCode());
        assertNotEquals(secondEntity.hashCode(), fourthEntity.hashCode());

        // Assertions - Placeholder entities
        assertFalse(thirdEntity.equals(fourthEntity));
        assertFalse(fourthEntity.equals(thirdEntity));
        assertNotEquals(thirdEntity.hashCode(), fourthEntity.hashCode());
    }

    @Test
    public void testEqualsAndHashCodePlaceholderAndNonPlaceholder() {
        // Test data
        T nonPlaceholder = getUniqueNonPlaceholder();
        T placeholder = getPlaceholderOf(nonPlaceholder);

        // Assertions
        assertFalse(nonPlaceholder.equals(placeholder));
        assertFalse(placeholder.equals(nonPlaceholder));
        assertNotEquals(nonPlaceholder.hashCode(), placeholder.hashCode());
    }

    protected abstract T getUniqueNonPlaceholder();

    protected abstract T getPlaceholderOf(T replaceable);

    protected class SimpleReplaceable extends Replaceable {
        protected SimpleReplaceable(boolean isPlaceholder) {
            super(isPlaceholder);
        }

        @Override
        public boolean canReplace(Replaceable replaceable) {
            return Objects.equals(this, replaceable);
        }

        @Override
        public <S extends Replaceable> Replaceable replace(S original, S replacement) {
            if (Objects.equals(this, original)) {
                return replacement;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public boolean isPlaceholderOf(Replaceable replaceable) {
            return false;
        }
    }
}