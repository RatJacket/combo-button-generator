package com.generation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static javafx.embed.swing.SwingFXUtils.toFXImage;

public class ComboGeneratorUI extends Application {

    @Override
    public void start(final Stage stage) {
        final TextField comboString = createTextFieldWithIdAndPrompt("comboStringTextField", "Enter combo...");

        final TextField imageDirectory = createTextFieldWithIdAndPrompt("imageDirectoryTextField", "Enter directory of your input images...");

        final TextField outputFileName = createTextFieldWithIdAndPrompt("outputFileNameTextField", "Enter output file name...");

        final TextField outputDirectory = createTextFieldWithIdAndPrompt("outputDirectoryTextField", "Enter output directory...");

        final Spinner<Integer> imageWidthSpacingBuffer = new Spinner<>(0, 1000, 10);
        imageWidthSpacingBuffer.setId("imageWidthSpacingBufferSpinner");
        imageWidthSpacingBuffer.setPromptText("Enter width spacing buffer (in pixels) for your images...");

        final Spinner<Integer> imageHeightSpacingBuffer = new Spinner<>(0, 1000, 10);
        imageHeightSpacingBuffer.setId("imageHeightSpacingBufferSpinner");
        imageHeightSpacingBuffer.setPromptText("Enter height spacing buffer (in pixels) for your images...");

        final Spinner<Integer> maxWidthPerLine = new Spinner<>(0, 2000, 500);
        maxWidthPerLine.setId("maxWidthPerLineSpinner");
        maxWidthPerLine.setPromptText("Enter max width (in pixels) per line...");

        final Button previewComboButton = new Button("Preview combo");
        previewComboButton.setId("previewComboButton");

        final Button createComboButton = new Button("Create combo");
        createComboButton.setId("createComboButton");

        final ImageView previewComboImageView = new ImageView(
                toFXImage(
                        new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB),
                        null));
        previewComboImageView.setId("previewComboImageView");

        previewComboButton.setOnAction(_ -> {
            try {
                previewComboImageView.setImage(toFXImage(
                        ComboGeneratorApplication.createCombinedComboBufferedImage(
                                comboString.getText(),
                                imageDirectory.getText(),
                                imageWidthSpacingBuffer.getValue(),
                                imageHeightSpacingBuffer.getValue(),
                                maxWidthPerLine.getValue()),
                        null));

            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        });

        createComboButton.setOnAction(_ -> {
            try {
                ComboGeneratorApplication.generateCombinedCombo(
                        comboString.getText(),
                        imageDirectory.getText(),
                        outputFileName.getText(),
                        outputDirectory.getText(),
                        imageWidthSpacingBuffer.getValue(),
                        imageHeightSpacingBuffer.getValue(),
                        maxWidthPerLine.getValue());
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        });

        final VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                comboString,
                imageDirectory,
                outputFileName,
                outputDirectory,
                imageWidthSpacingBuffer,
                imageHeightSpacingBuffer,
                maxWidthPerLine,
                previewComboButton,
                createComboButton,
                previewComboImageView);

        Scene scene = new Scene(layout, 400, 400);
        stage.setTitle("Combo Generator");
        stage.setScene(scene);
        stage.show();
    }

    private static TextField createTextFieldWithIdAndPrompt(final String id, final String promptText) {
        final TextField comboString = new TextField();
        comboString.setId(id);
        comboString.setPromptText(promptText);
        return comboString;
    }
}
