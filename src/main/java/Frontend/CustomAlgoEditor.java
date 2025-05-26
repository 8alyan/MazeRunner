package Frontend;

import Backend.BenchmarkRunner;
import Backend.DynamicRunnerLoader;
import Backend.Runner;
import Backend.UserCodeWrapper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CustomAlgoEditor  {
    private SceneManager sceneManager;
    private Scene scene;

   public CustomAlgoEditor(Stage stage,SceneManager sceneManager){
       // Class name input
       this.sceneManager=sceneManager;
       Label classLabel = new Label("Class Name:");
       TextField classNameField = new TextField();
       classNameField.setPromptText("e.g. MySpeedySolver");

       classLabel.setStyle(
               "-fx-font-family: 'Bebas Neue';" +
                       "-fx-font-size: 28px;" +
                       "-fx-text-fill: #464655;" +
                       "-fx-font-weight: bold;" +
                       "-fx-padding: 10px;"
       );
       classNameField.setStyle(
               "-fx-font-family: 'Bebas Neue';" +
                       "-fx-font-size: 22px;" +
                       "-fx-text-fill: #464655;" +
                       "-fx-font-weight: bold;" +
                       "-fx-padding: 10px;"
       );



       // User code input
       Label codeLabel = new Label("Write your method (only the body below):");
       TextArea codeArea = new TextArea();
       codeArea.setText("public List<int[]> run(int[][] maze) {\n" +
               "                    [[--write code here do not change this method--]] \n" +
               "}");
       codeArea.setPrefHeight(400);
       codeArea.setWrapText(true);
       codeLabel.setStyle(
               "-fx-font-family: 'Bebas Neue';" +
                       "-fx-font-size: 22px;" +
                       "-fx-text-fill: #464655;" +
                       "-fx-font-weight: bold;" +
                       "-fx-padding: 10px;"
       );
       codeArea.setStyle(
               "-fx-font-family: 'Bebas Neue';" + "-fx-font-size: 22px;" + "-fx-text-fill: #464655;" + "-fx-font-weight: bold;" + "-fx-padding: 10px;");



       // Submit button
       Button submitButton = new Button("Submit");
       Label statusLabel = new Label();
       Button exitbutton = new Button("Back");
       statusLabel.setStyle(
               "-fx-font-family: 'Bebas Neue';" + "-fx-font-size: 22px;" + "-fx-text-fill: #464655;" + "-fx-font-weight: bold;" + "-fx-padding: 10px;");

       submitButton.setOnAction(e -> {
           String className = classNameField.getText().trim();
           String userCode = codeArea.getText().trim();

           if (className.isEmpty() || userCode.isEmpty()) {
               statusLabel.setText("Please enter both class name and code.");
               return;
           }

           try {
               String filePath = UserCodeWrapper.saveWrappedClass(className, userCode);
               statusLabel.setText("Saved file successfully");
               Runner runner = DynamicRunnerLoader.compileAndLoad(className);
               BenchmarkRunner.test(runner,sceneManager,className); // animate or test
               statusLabel.setText("Compiled and loaded successfully!");
           } catch (Exception ex) {
               statusLabel.setText("Error: " + ex.getMessage());
               ex.printStackTrace();
           }
       });
       exitbutton.setOnAction(e->sceneManager.switchtoGameSS());
       exitbutton.setId("exitBtn");
       submitButton.setId("startBtn");
        HBox layouut = new HBox(10);
        layouut.setPadding(new Insets(20));
        layouut.setAlignment(Pos.CENTER);
        layouut.getChildren().addAll(submitButton,exitbutton);
       // Layout
       VBox layout = new VBox(10);
       layout.setPadding(new Insets(20));
       layout.getChildren().addAll(classLabel, classNameField, codeLabel, codeArea,layouut,statusLabel);


       this.scene = new Scene(layout, 1000, 800);

       String css = this.getClass().getResource("/styles.css").toExternalForm();
       scene.getStylesheets().add(css);
       String fontsCss = this.getClass().getResource("/fonts.css").toExternalForm();
       scene.getStylesheets().add(fontsCss);

   }
    public Scene getScene() {
        return scene;
    }

}
