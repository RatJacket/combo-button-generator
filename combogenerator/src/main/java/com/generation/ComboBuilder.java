package com.generation;

import com.generation.domain.Combo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboBuilder {
    public static final String CONTAINS_PLUS_PATTERN = ".*\\+.*";

    public static Combo buildCombo(final List<String> comboInputs, final String imageDirectory, final int imageBuffer) throws IOException {
        final List<BufferedImage> comboImages = new ArrayList<>();

        int totalWidth = 0;
        int maxHeight = 0;
        final Map<String, BufferedImage> knownImages = new HashMap<>();
        for (int i = 0; i < comboInputs.size(); i++) {
            final String comboInput = comboInputs.get(i);
            if (comboInput.matches(CONTAINS_PLUS_PATTERN)) {
                final String[] splitCombo = splitInputByButtonWithPluses(comboInput);
                for (final String input : splitCombo) {
                    if (input.equals("+")) {
                        final BufferedImage plusImage = getImageForSymbolFromResources("Plus", knownImages);
                        comboImages.add(plusImage);
                        totalWidth += plusImage.getWidth() + imageBuffer;
                        maxHeight = Math.max(maxHeight, plusImage.getHeight());
                    } else {
                        final BufferedImage comboImage = getImageForInput(imageDirectory, input, knownImages);
                        comboImages.add(comboImage);
                        totalWidth += comboImage.getWidth() + imageBuffer;
                        maxHeight = Math.max(maxHeight, comboImage.getHeight());
                    }
                }
            } else {
                final BufferedImage comboImage = getImageForInput(imageDirectory, comboInput, knownImages);
                comboImages.add(comboImage);
                totalWidth += comboImage.getWidth() + imageBuffer;
                maxHeight = Math.max(maxHeight, comboImage.getHeight());
            }
            if (!(i == comboInputs.size() - 1)) {
                final BufferedImage arrowImage = getImageForSymbolFromResources("Arrow", knownImages);
                comboImages.add(arrowImage);
                totalWidth += arrowImage.getWidth() + imageBuffer;
            }
        }
        return new Combo(maxHeight, totalWidth, comboImages);
    }

    private static BufferedImage getImageForInput(final String imageDirectory, final String input, final Map<String, BufferedImage> knownImages) throws IOException {
        final BufferedImage knownImage = knownImages.get(input);
        if (knownImage == null) {
            final File imageFile = new File(imageDirectory, input + ".png");
            if (!imageFile.exists()) {
                return getImageForSymbolFromResources(input, knownImages);
            }
            return ImageIO.read(imageFile);
        }
        return knownImage;
    }

    static BufferedImage getImageForSymbolFromResources(final String symbol, final Map<String, BufferedImage> knownImages) throws IOException {
        final BufferedImage knownImage = knownImages.get(symbol);
        if (knownImage == null) {
            try (final InputStream inputStream = ComboBuilder.class.getResourceAsStream("/" + symbol + ".png")) {
                if (inputStream == null) {
                    throw new IllegalArgumentException("Could not find button image: " + symbol + " in resources!");
                }
                return ImageIO.read(inputStream);
            }
        }
        return knownImage;
    }

    static String[] splitInputByButtonWithPluses(final String comboInput) {
        return comboInput.split("(?<=\\+)|(?=\\+)");
    }
}
