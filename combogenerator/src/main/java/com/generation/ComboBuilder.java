package com.generation;

import com.generation.domain.Combo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ComboBuilder {
    public static final String CONTAINS_PLUS_PATTERN = ".*\\+.*";

    public static Combo buildCombo(final List<String> comboInputs, final String imageDirectory, final int imageBuffer) throws IOException {
        final List<BufferedImage> comboImages = new ArrayList<>();

        final BufferedImage plusImage = getImageForSymbolFromResources("Plus");
        final BufferedImage arrowImage = getImageForSymbolFromResources("Arrow");

        int totalWidth = 0;
        int maxHeight = 0;
        for (int i = 0; i < comboInputs.size(); i++) {
            final String comboInput = comboInputs.get(i);
            if (comboInput.matches(CONTAINS_PLUS_PATTERN)) {
                final String[] splitCombo = splitInputByButtonWithPluses(comboInput);
                for (final String input : splitCombo) {
                    if (input.equals("+")) {
                        comboImages.add(plusImage);
                        totalWidth += plusImage.getWidth() + imageBuffer;
                        maxHeight = Math.max(maxHeight, plusImage.getHeight());
                    } else {
                        final BufferedImage comboImage = getImageForInput(imageDirectory, input);
                        comboImages.add(comboImage);
                        totalWidth += comboImage.getWidth() + imageBuffer;
                        maxHeight = Math.max(maxHeight, comboImage.getHeight());
                    }
                }
            } else {
                final BufferedImage comboImage = getImageForInput(imageDirectory, comboInput);
                comboImages.add(comboImage);
                totalWidth += comboImage.getWidth() + imageBuffer;
                maxHeight = Math.max(maxHeight, comboImage.getHeight());
            }
            if (!(i == comboInputs.size() - 1)) {
                comboImages.add(arrowImage);
                totalWidth += arrowImage.getWidth() + imageBuffer;
            }
        }
        return new Combo(maxHeight, totalWidth, comboImages);
    }

    private static BufferedImage getImageForInput(final String imageDirectory, final String input) throws IOException {
        final File imageFile = new File(imageDirectory, input + ".png");

        if (!imageFile.exists()) {
            throw new IOException("Missing Image Error: Could not find '" + input +
                    "' inside your specified folder:\n" + imageFile.getAbsolutePath());
        }

        return ImageIO.read(imageFile);
    }

    static BufferedImage getImageForSymbolFromResources(final String symbol) throws IOException {
        try (final InputStream inputStream = ComboGeneratorApplication.class.getResourceAsStream("/" + symbol + ".png")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Could not find button image in resources!");
            }
            return ImageIO.read(inputStream);
        }
    }

    static String[] splitInputByButtonWithPluses(final String comboInput) {
        return comboInput.split("(?<=\\+)|(?=\\+)");
    }
}
