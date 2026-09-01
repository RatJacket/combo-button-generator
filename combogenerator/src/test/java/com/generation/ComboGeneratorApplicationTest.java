package com.generation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComboGeneratorApplicationTest {

    @Test
    void shouldParseComboInputIntoIndividualInputs() {
        final List<String> comboInputs = ComboGeneratorApplication.parseComboIntoIndividualInputs("LightRight+MediumHeavy");
        assertEquals(List.of("Light", "Right+Medium", "Heavy"), comboInputs);
    }

    @Test
    void shouldSplitInputByButtonWithPluses() {
        final String[] buttonsWithPluses = ComboGeneratorApplication.splitInputByButtonWithPluses("Light+Medium+Heavy");
        assertArrayEquals(new String[]{"Light", "+", "Medium", "+", "Heavy"}, buttonsWithPluses);
    }
}
