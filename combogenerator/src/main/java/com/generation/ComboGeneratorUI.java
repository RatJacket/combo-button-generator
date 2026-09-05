package com.generation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.io.IOException;


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
        imageWidthSpacingBuffer.setPromptText("Enter width spacing buffer for your images...");

        final Spinner<Integer> imageHeightSpacingBuffer = new Spinner<>(0, 1000, 10);
        imageHeightSpacingBuffer.setPromptText("Enter height spacing buffer for your images...");

        final Spinner<Integer> maxWidthPerLine = new Spinner<>(0, 2000, 200);
        maxWidthPerLine.setPromptText("Enter max width per line...");

        final Button createComboButton = new Button("Create combo");

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

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                comboString,
                imageDirectory,
                outputFileName,
                outputDirectory,
                imageWidthSpacingBuffer,
                imageHeightSpacingBuffer,
                maxWidthPerLine,
                createComboButton);

        Scene scene = new Scene(layout, 350, 300);
        stage.setTitle("Combo Generator");
        stage.setScene(scene);
        stage.show();
    }
}
