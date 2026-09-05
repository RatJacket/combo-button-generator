package com.generation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ComboBuilderTest {

    @Test
    void shouldSplitInputByButtonWithPluses() {
        final String[] buttonsWithPluses = ComboBuilder.splitInputByButtonWithPluses("Light+Medium+Heavy");
        assertArrayEquals(new String[]{"Light", "+", "Medium", "+", "Heavy"}, buttonsWithPluses);
    }

    @Test
    void shouldCreateStringArrayOfSingleInputWhenThereAreNoPluses() {
        final String[] buttonsWithPluses = ComboBuilder.splitInputByButtonWithPluses("Light");
        assertArrayEquals(new String[]{"Light"}, buttonsWithPluses);
    }
}
