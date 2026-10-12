package com.generation;

import com.generation.helpers.ComboGeneratorTestHelpers;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    void shouldParseInputsSeparatedByPeriodAsOneString() {
        final List<String> comboInputs = ComboGeneratorApplication.parseComboIntoIndividualInputs("Left.Left+LightMedium");
        assertEquals(List.of("Left.Left+Light", "Medium"), comboInputs);
    }

    @Test
    void shouldCreateCombinedComboBufferedImage() throws IOException {
        final BufferedImage combinedComboImage = ComboGeneratorApplication.createCombinedComboBufferedImage(
                "UpDownLeftRight+LightMediumHeavy+Assist",
                "",
                10,
                10,
                200);

        final BufferedImage expectedComboImage = ImageIO.read(
                Objects.requireNonNull(this.getClass().getResourceAsStream("/exampleCombo.png")));

        ComboGeneratorTestHelpers.compareBufferedImages(expectedComboImage, combinedComboImage);
    }

    @Test
    void shouldNotPutArrowBetweenButtonsIfInputsSeparatedByPeriod() throws IOException {
        final BufferedImage combinedComboImage = ComboGeneratorApplication.createCombinedComboBufferedImage(
                "Left.Left+Light",
                "",
                10,
                10,
                200);

        final BufferedImage expectedComboImage = ImageIO.read(
                Objects.requireNonNull(this.getClass().getResourceAsStream("/dashLightExampleCombo.png")));

        ComboGeneratorTestHelpers.compareBufferedImages(expectedComboImage, combinedComboImage);
    }
}
