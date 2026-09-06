package com.generation;

import com.generation.helpers.ComboGeneratorTestHelpers;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ComboGeneratorUITest extends ApplicationTest {
    private final ComboGeneratorUI comboGeneratorUI = new ComboGeneratorUI();

    @Override
    public void start(final Stage stage) {
        comboGeneratorUI.start(stage);
    }

    @Test
    void shouldGeneratePreviewComboImageWhenPreviewComboButtonIsClicked() throws IOException {
        clickOn("#comboStringTextField");
        write("Left+LightRight+Medium");
        sleep(100);

        final ImageView previewComboImageView = lookup("#previewComboImageView").queryAs(ImageView.class);
        final BufferedImage transparentPreviewComboImage = SwingFXUtils.fromFXImage(previewComboImageView.getImage(), null);
        ComboGeneratorTestHelpers.compareBufferedImages(transparentPreviewComboImage, new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB));

        clickOn("#previewComboButton");
        sleep(100);

        final BufferedImage previewComboImage = SwingFXUtils.fromFXImage(previewComboImageView.getImage(), null);
        ComboGeneratorTestHelpers.compareBufferedImages(
                previewComboImage,
                ComboGeneratorApplication.createCombinedComboBufferedImage(
                        "Left+LightRight+Medium",
                        "",
                        10,
                        10,
                        200
                ));
    }

}