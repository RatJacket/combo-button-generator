package com.generation.domain;

import java.awt.image.BufferedImage;
import java.util.List;

public record Combo (int maxHeight, int totalWidth, List<BufferedImage> inputs){}
