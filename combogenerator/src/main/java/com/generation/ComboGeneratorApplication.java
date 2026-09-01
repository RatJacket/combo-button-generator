package com.generation;

import com.generation.domain.Combo;

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

    private static final int IMAGE_BUFFER = 10;
    public static final String COMBO_PATTERN = "[A-Z][a-z]+(?:\\+[A-Z][a-z]+)?";

    public static void createCombinedComboImage(
            final String comboString,
            final String imageDirectory,
            final String outputImageName) throws IOException {

        final List<String> comboInputs = parseComboIntoIndividualInputs(comboString);

        final Combo combo = ComboBuilder.buildCombo(comboInputs, imageDirectory);

        final BufferedImage combinedComboImage = new BufferedImage(combo.totalWidth(), combo.maxHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics2D = combinedComboImage.createGraphics();

        int currentImagePositionX = 0;
        for (final BufferedImage comboImage : combo.inputs()) {
            final int centerY = combo.maxHeight() - comboImage.getHeight();
            graphics2D.drawImage(comboImage, currentImagePositionX, centerY / 2, null);
            currentImagePositionX += comboImage.getWidth() + IMAGE_BUFFER;
        }

        graphics2D.dispose();

        final File outputDirectory = new File("out");
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        final String format = outputImageName.substring(outputImageName.lastIndexOf(".") + 1);

        boolean success = ImageIO.write(combinedComboImage, format, new File(outputDirectory, outputImageName));
        if (!success) {
            throw new IOException("No appropriate writer found for format: " + format);
        }
    }

    static List<String> parseComboIntoIndividualInputs(final String combo) {
        Pattern pattern = Pattern.compile(COMBO_PATTERN);
        Matcher matcher = pattern.matcher(combo);

        List<String> comboInputs = new ArrayList<>();
        while (matcher.find()) {
            comboInputs.add(matcher.group());
        }

        return comboInputs;
    }
}
