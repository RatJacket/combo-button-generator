package com.generation;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ComboGeneratorApplicationTest {

    @Test
    void shouldParseComboInputIntoIndividualInputs() {
        final List<String> comboInputs = ComboGeneratorApplication.parseComboIntoIndividualInputs("LightRight+MediumHeavy");
        assertEquals(List.of("Light", "Right+Medium", "Heavy"), comboInputs);
    }

    @Test
    void shouldCreateCombinedComboImage() throws IOException {
        final BufferedImage combinedComboImage = ComboGeneratorApplication.createCombinedComboImage(
                "UpDownLeftRight+LightMediumHeavy+Assist",
                "",
                10);

        final BufferedImage expectedComboImage = ImageIO.read(
                Objects.requireNonNull(this.getClass().getResourceAsStream("/exampleCombo.png")));

        final int expectedComboImageWidth = expectedComboImage.getWidth();
        assertEquals(expectedComboImageWidth, combinedComboImage.getWidth(), "Width mismatch");

        final int expectedComboImageHeight = expectedComboImage.getHeight();
        assertEquals(expectedComboImageHeight, combinedComboImage.getHeight(), "Height mismatch");

        final int[] expectedPixels = new int[expectedComboImageWidth * expectedComboImageHeight];
        final int[] actualPixels = new int[expectedComboImageWidth * expectedComboImageHeight];

        expectedComboImage.getRGB(0, 0, expectedComboImageWidth, expectedComboImageHeight, expectedPixels, 0, expectedComboImageWidth);
        combinedComboImage.getRGB(0, 0, expectedComboImageWidth, expectedComboImageHeight, actualPixels, 0, expectedComboImageWidth);

        assertArrayEquals(expectedPixels, actualPixels, "Pixels do not match");
    }
}
