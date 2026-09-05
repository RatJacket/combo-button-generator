package com.generation;

import com.generation.domain.Combo;
import com.generation.domain.ComboBuilderOptions;

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

    public static Combo buildCombo(final ComboBuilderOptions comboBuilderOptions) throws IOException {

        final List<String> comboInputs = comboBuilderOptions.comboInputs();
        final int imageWidthBuffer = comboBuilderOptions.imageWidthBuffer();
        final int imageHeightBuffer = comboBuilderOptions.imageHeightBuffer();

        final List<BufferedImage> comboImages = new ArrayList<>();
        int totalWidth = 0;
        int currentLineWidth = 0;
        int totalHeight = 0;
        int currentLineHeight = 0;
        int totalLines = 1;

        final Map<String, BufferedImage> knownImages = new HashMap<>();
        for (int i = 0; i < comboInputs.size(); i++) {
            final String[] splitCombo = splitInputByButtonWithPluses(comboInputs.get(i));
            for (final String input : splitCombo) {
                final BufferedImage comboImage = input.equals("+") ?
                        getImageForSymbolFromResources("Plus", knownImages) :
                        getImageForInput(comboBuilderOptions.imageDirectory(), input, knownImages);
                comboImages.add(comboImage);
                currentLineWidth += comboImage.getWidth() + imageWidthBuffer;
                currentLineHeight = Math.max(currentLineHeight, comboImage.getHeight() + imageHeightBuffer);
            }
            totalWidth = Math.max(totalWidth, currentLineWidth);
            totalHeight = Math.max(totalHeight, currentLineHeight);
            if (currentLineWidth > comboBuilderOptions.maxWidthPerLine()) {
                currentLineWidth = 0;
                currentLineHeight = totalHeight + currentLineHeight;
                if (i != comboInputs.size() - 1) {
                    totalLines++;
                }
            }
            if (i != comboInputs.size() - 1 && currentLineWidth != 0) {
                final BufferedImage arrowImage = getImageForSymbolFromResources("Arrow", knownImages);
                comboImages.add(arrowImage);
                currentLineWidth += arrowImage.getWidth() + imageWidthBuffer;
                currentLineHeight = Math.max(currentLineHeight, arrowImage.getHeight() + imageHeightBuffer);
                totalWidth = Math.max(totalWidth, currentLineWidth);
                totalHeight = Math.max(totalHeight, currentLineHeight);
            }
        }
        return new Combo(totalWidth, totalHeight, totalLines, comboImages);
    }

    private static BufferedImage getImageForInput(
            final String imageDirectory,
            final String input,
            final Map<String, BufferedImage> knownImages) throws IOException {
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

    static BufferedImage getImageForSymbolFromResources(
            final String symbol,
            final Map<String, BufferedImage> knownImages) throws IOException {
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
