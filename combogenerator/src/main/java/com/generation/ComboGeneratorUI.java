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

        final Spinner<Integer> imageSpacingBuffer = new Spinner<>(0, 100, 10);
        imageSpacingBuffer.setPromptText("Enter spacing buffer for your images...");

        final Button createComboButton = new Button("Create combo");

        createComboButton.setOnAction(_ -> {

            try {
                ComboGeneratorApplication.createCombinedComboImage(
                        comboString.getText(),
                        imageDirectory.getText(),
                        outputFileName.getText(),
                        imageSpacingBuffer.getValue());
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(comboString, imageDirectory, outputFileName, imageSpacingBuffer, createComboButton);

        Scene scene = new Scene(layout, 350, 250);
        stage.setTitle("Combo Generator");
        stage.setScene(scene);
        stage.show();
    }
}
