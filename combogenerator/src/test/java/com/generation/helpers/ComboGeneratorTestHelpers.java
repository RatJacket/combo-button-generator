package com.generation.helpers;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComboGeneratorTestHelpers {
    public static void compareBufferedImages(final BufferedImage expected, final BufferedImage actual) {
        final int expectedComboImageWidth = expected.getWidth();
        assertEquals(expectedComboImageWidth, actual.getWidth(), "Width mismatch");

        final int expectedComboImageHeight = expected.getHeight();
        assertEquals(expectedComboImageHeight, actual.getHeight(), "Height mismatch");

        final int[] expectedPixels = new int[expectedComboImageWidth * expectedComboImageHeight];
        final int[] actualPixels = new int[expectedComboImageWidth * expectedComboImageHeight];

        expected.getRGB(0, 0, expectedComboImageWidth, expectedComboImageHeight, expectedPixels, 0, expectedComboImageWidth);
        actual.getRGB(0, 0, expectedComboImageWidth, expectedComboImageHeight, actualPixels, 0, expectedComboImageWidth);

        assertArrayEquals(expectedPixels, actualPixels, "Pixels do not match");
    }
}
