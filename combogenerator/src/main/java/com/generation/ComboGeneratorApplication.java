package com.generation;

import com.generation.domain.Combo;
import com.generation.domain.ComboBuilderOptions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComboGeneratorApplication {

    public static final String COMBO_PATTERN = "[A-Z][a-z]+(?:\\+[A-Z][a-z]+)?";

    public static void generateCombinedCombo(
            final String comboString,
            final String imageDirectory,
            final String outputImageName,
            final String outputDirectoryInput,
            final int imageWidthBuffer,
            final int imageHeightBuffer,
            final int maxWidthPerLine) throws IOException {

        final BufferedImage combinedComboImage = createCombinedComboBufferedImage(
                comboString,
                imageDirectory,
                imageWidthBuffer,
                imageHeightBuffer,
                maxWidthPerLine);

        final File outputDirectory = new File(outputDirectoryInput);
        if (!outputDirectory.exists()) {
            throw new IOException("Output directory does not exist!");
        }

        final String format = outputImageName.substring(outputImageName.lastIndexOf(".") + 1);

        boolean success = ImageIO.write(combinedComboImage, format, new File(outputDirectory, outputImageName));
        if (!success) {
            throw new IOException("No appropriate writer found for format: " + format);
        }
    }

    static BufferedImage createCombinedComboBufferedImage(
            final String comboString,
            final String imageDirectory,
            final int imageWidthBuffer,
            final int imageHeightBuffer,
            final int maxWidthPerLine) throws IOException {
        final List<String> comboInputs = parseComboIntoIndividualInputs(comboString);

        final Combo combo = ComboBuilder.buildCombo(new ComboBuilderOptions(comboInputs, imageDirectory, imageWidthBuffer, imageHeightBuffer, maxWidthPerLine));

        final BufferedImage combinedComboImage = new BufferedImage(combo.maxWidth(), combo.maxHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics2D = combinedComboImage.createGraphics();

        int currentImagePositionX = 0;
        int lineCount = 0;
        for (final BufferedImage comboImage : combo.inputs()) {
            if (currentImagePositionX + comboImage.getWidth() + imageWidthBuffer > combo.maxWidth()) {
                currentImagePositionX = 0;
                lineCount++;
            }
            final int maxHeightPerLine = combo.maxHeight() / combo.totalLines();
            final int imageCenterY = maxHeightPerLine - comboImage.getHeight();
            graphics2D.drawImage(comboImage, currentImagePositionX, (imageCenterY / 2) + (lineCount * maxHeightPerLine), null);
            currentImagePositionX += comboImage.getWidth() + imageWidthBuffer;
        }

        graphics2D.dispose();
        return combinedComboImage;
    }

    static List<String> parseComboIntoIndividualInputs(final String combo) {
        final Pattern pattern = Pattern.compile(COMBO_PATTERN);
        final Matcher matcher = pattern.matcher(combo);

        final List<String> comboInputs = new ArrayList<>();
        while (matcher.find()) {
            comboInputs.add(matcher.group());
        }

        return comboInputs;
    }
}
