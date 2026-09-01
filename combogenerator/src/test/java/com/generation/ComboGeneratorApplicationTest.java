package com.generation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComboGeneratorApplicationTest {

    @Test
    void shouldParseComboInputIntoIndividualInputs() {
        List<String> comboInputs = ComboGeneratorApplication.parseComboIntoIndividualInputs("A1B+2C3");
        assertEquals(comboInputs, List.of("A", "1", "B+2", "C", "4"));
    }
}
