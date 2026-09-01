package com.generation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComboGeneratorApplication {

    private static final int IMAGE_BUFFER = 10;

    public static void createCombinedComboImage(
            final String combo,
            final String imageDirectory,
            final String outputImageName) throws IOException {

        final List<String> comboInputs = parseComboIntoIndividualInputs(combo);

        final List<BufferedImage> comboImages = new ArrayList<>();

        final BufferedImage plusImage = getImageForSymbolFromResources("Plus");
        final BufferedImage arrowImage = getImageForSymbolFromResources("Arrow");

        int totalWidth = 0;
        int maxHeight = 0;
        for (int i = 0; i < comboInputs.size(); i++) {
            String comboInput = comboInputs.get(i);
            if (comboInput.length() == 1) {
                final BufferedImage comboImage = getImageForInput(imageDirectory, comboInput);
                comboImages.add(comboImage);
                totalWidth += comboImage.getWidth() + IMAGE_BUFFER;
                maxHeight = Math.max(maxHeight, comboImage.getHeight());
            } else {
                char[] symbols = comboInput.toCharArray();
                final BufferedImage comboImage1 = getImageForInput(imageDirectory, String.valueOf(symbols[0]));
                comboImages.add(comboImage1);
                totalWidth += comboImage1.getWidth() + IMAGE_BUFFER;
                maxHeight = Math.max(maxHeight, comboImage1.getHeight());

                comboImages.add(plusImage);
                totalWidth += plusImage.getWidth() + IMAGE_BUFFER;
                maxHeight = Math.max(maxHeight, plusImage.getHeight());

                final BufferedImage comboImage2 = getImageForInput(imageDirectory, String.valueOf(symbols[2]));
                comboImages.add(comboImage2);
                totalWidth += comboImage2.getWidth() + IMAGE_BUFFER;
                maxHeight = Math.max(maxHeight, comboImage2.getHeight());
            }
            if (!(i == comboInputs.size() - 1)) {
                comboImages.add(arrowImage);
                totalWidth += arrowImage.getWidth() + IMAGE_BUFFER;
            }
        }

        final BufferedImage combinedComboImage = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics2D = combinedComboImage.createGraphics();

        int currentImagePositionX = 0;
        for (final BufferedImage comboImage : comboImages) {
            final int centerY = maxHeight - comboImage.getHeight();
            graphics2D.drawImage(comboImage, currentImagePositionX, centerY / 2, null);
            currentImagePositionX += comboImage.getWidth() + IMAGE_BUFFER;
        }

        graphics2D.dispose();

        final File outputDirectory = new File("out");
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        final File outputFile = new File(outputDirectory, outputImageName);

        final String format = outputImageName.substring(outputImageName.lastIndexOf(".") + 1);

        boolean success = ImageIO.write(combinedComboImage, format, outputFile);
        if (!success) {
            throw new IOException("No appropriate writer found for format: " + format);
        }
    }

    static List<String> parseComboIntoIndividualInputs(final String combo) {
        Pattern pattern = Pattern.compile(".\\+.|.");
        Matcher matcher = pattern.matcher(combo);

        final List<String> comboInputs = new ArrayList<>();
        while (matcher.find()) {
            comboInputs.add(matcher.group());
        }

        return comboInputs;
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
}
