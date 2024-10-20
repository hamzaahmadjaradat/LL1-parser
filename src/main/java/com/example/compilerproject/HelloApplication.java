package com.example.compilerproject;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class HelloApplication extends Application {
    TextArea codeTextArea = new TextArea();
    Text resultParsing = new Text("");



    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LL1 PARSER");

        Text instructionText = new Text("😊 Enjoy using LL1 PARSER!");
        instructionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        instructionText.setFill(Color.DARKSLATEBLUE);

        Button openButton = new Button("📂");
        openButton.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        openButton.setStyle("-fx-background-color: #6A5ACD; -fx-text-fill: white;");
        openButton.setMinWidth(200);
        openButton.setMinHeight(50);

        codeTextArea.setFont(Font.font("Arial", 16));
        codeTextArea.setEditable(false);
        codeTextArea.setWrapText(true);
        codeTextArea.setPrefHeight(300);

        resultParsing.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        resultParsing.setFill(Color.MEDIUMSLATEBLUE);

        openButton.setOnAction(e -> {
            codeTextArea.setText("");
            resultParsing.setText("");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    String content = new String(Files.readAllBytes(Paths.get(selectedFile.getPath())));
                    String originalContent=content;
                    String[] lines = content.split("\n");
                    content = "";
                    for (int i = 0; i < lines.length; i++) {
                        content += i+1 + "      "+lines[i]+"\n";
                    }
                    codeTextArea.setText(content);
                    doParsing(originalContent);
                } catch (IOException ex) {
                    codeTextArea.setText("Error reading file: " + ex.getMessage());
                }
            } else {
                instructionText.setText("File selection cancelled.");
            }
        });

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.getChildren().addAll(openButton, instructionText, codeTextArea, resultParsing);

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void doParsing(String content) {
        lexicalAnalayzer x = new lexicalAnalayzer();
        List<Token> list = x.generateTokens(content);
        LL1Parser y = new LL1Parser();
        String result = y.parse(list);
        if (!result.isEmpty()) {
            resultParsing.setText("" + result + " ❌");
        } else {
            resultParsing.setText("The Code Provided is Syntactically Correct ✔");

        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}