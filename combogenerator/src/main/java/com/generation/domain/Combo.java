package com.generation.domain;

import java.awt.image.BufferedImage;
import java.util.List;

public record Combo(int maxWidth, int maxHeight, int totalLines, List<BufferedImage> inputs) {
}
