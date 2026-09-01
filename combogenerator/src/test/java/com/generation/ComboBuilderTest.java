package com.generation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComboBuilderTest {

    @Test
    void shouldSplitInputByButtonWithPluses() {
        final String[] buttonsWithPluses = ComboBuilder.splitInputByButtonWithPluses("Light+Medium+Heavy");
        assertArrayEquals(new String[]{"Light", "+", "Medium", "+", "Heavy"}, buttonsWithPluses);
    }
}
