package com.generation.domain;

import java.util.List;

public record ComboBuilderOptions(
        List<String> comboInputs,
        String imageDirectory,
        int imageWidthBuffer,
        int imageHeightBuffer,
        int maxWidthPerLine) {
}
