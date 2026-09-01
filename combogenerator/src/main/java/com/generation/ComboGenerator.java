package com.generation;

import java.io.IOException;

public class ComboGenerator {
    public static void main(String[] args) throws IOException {
        final String combo = args[0];
        final String imageDirectory = args[1];
        final String outputImageName = args[2];
        ComboGeneratorApplication.createCombinedComboImage(combo, imageDirectory, outputImageName);
    }
}
