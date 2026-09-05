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
        final TextField comboString = new TextField();
        comboString.setPromptText("Enter combo...");

        final TextField imageDirectory = new TextField();
        imageDirectory.setPromptText("Enter directory of your input images...");

        final TextField outputFileName = new TextField();
        outputFileName.setPromptText("Enter output file name...");

        final TextField outputDirectory = new TextField();
        outputDirectory.setPromptText("Enter output directory...");

        final Spinner<Integer> imageWidthSpacingBuffer = new Spinner<>(0, 1000, 10);
        imageWidthSpacingBuffer.setPromptText("Enter width spacing buffer (in pixels) for your images...");

        final Spinner<Integer> imageHeightSpacingBuffer = new Spinner<>(0, 1000, 10);
        imageHeightSpacingBuffer.setPromptText("Enter height spacing buffer (in pixels) for your images...");

        final Spinner<Integer> maxWidthPerLine = new Spinner<>(0, 2000, 500);
        maxWidthPerLine.setPromptText("Enter max width (in pixels) per line...");

        final Button previewComboButton = new Button("Preview combo");

        final Button createComboButton = new Button("Create combo");

        final ImageView comboImageView = new ImageView(
                toFXImage(
                        new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB),
                        null));


        previewComboButton.setOnAction(_ -> {
            try {
                comboImageView.setImage(toFXImage(
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
                comboImageView);

        final Scene scene = new Scene(layout, 400, 400);
        stage.setTitle("Combo Generator");
        stage.setScene(scene);
        stage.show();
    }
}
