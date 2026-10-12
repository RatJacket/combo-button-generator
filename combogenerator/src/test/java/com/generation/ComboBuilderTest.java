package com.generation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ComboBuilderTest {

    @Test
    void shouldSplitInputByButtonWithPluses() {
        final String[] buttonsWithPluses = ComboBuilder.splitInputByButtonWithSpecialSymbols("Light+Medium+Heavy");
        assertArrayEquals(new String[]{"Light", "+", "Medium", "+", "Heavy"}, buttonsWithPluses);
    }

    @Test
    void shouldSplitInputByButtonWithPeriods() {
        final String[] buttonsWithPluses = ComboBuilder.splitInputByButtonWithSpecialSymbols("Light.Medium.Heavy");
        assertArrayEquals(new String[]{"Light", ".", "Medium", ".", "Heavy"}, buttonsWithPluses);
    }

    @Test
    void shouldSplitInputByButtonWithSpecialSymbolsAndPeriods() {
        final String[] buttonsWithPluses = ComboBuilder.splitInputByButtonWithSpecialSymbols("Light+Medium.Heavy");
        assertArrayEquals(new String[]{"Light", "+", "Medium", ".", "Heavy"}, buttonsWithPluses);
    }

    @Test
    void shouldCreateStringArrayOfSingleInputWhenThereAreNoSpecialSymbols() {
        final String[] buttonsWithPluses = ComboBuilder.splitInputByButtonWithSpecialSymbols("Light");
        assertArrayEquals(new String[]{"Light"}, buttonsWithPluses);
    }
}
