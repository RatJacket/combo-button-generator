package com.generation;

import java.io.IOException;

public class ComboGeneratorMain {
    public static void main(String[] args) throws IOException {
        final String combo = args[0];
        final String imageDirectory = args[1];
        final String outputImageName = args[2];
        final String imageBuffer = args[3];
        ComboGeneratorApplication.createCombinedComboImage(combo, imageDirectory, outputImageName, Integer.parseInt(imageBuffer));
    }
}
