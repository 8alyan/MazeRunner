package Frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class StatScreen {

    private SceneManager sceneManager;
    private Scene scene;

    public StatScreen(SceneManager sceneManager, String algoName, boolean pathFound, double executionTime, int[][] matrix, List<int[]> path) {
        this.sceneManager=sceneManager;
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        // Top Thumbs
        Label verdictIcon = new Label(pathFound ? "👍" : "👎");
        verdictIcon.setStyle(
                "-fx-font-family: 'Bebas Neue';" +
                        "-fx-font-size: 80px;" +
                        "-fx-text-fill: #464655;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px;"
        );

        // Result details
        VBox stats = new VBox(10);
        stats.setAlignment(Pos.CENTER);
        stats.getChildren().addAll(
                new Label("Algorithm: " + algoName),
                new Label("Path Status: " + (pathFound ? "Found ✅" : "Not Found ❌")),
                new Label("Execution Time: " + executionTime + " ms")
        );
        stats.setStyle(
                "-fx-font-family: 'Bebas Neue';" +
                        "-fx-font-size: 28px;" +
                        "-fx-text-fill: #464655;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10px;"
        );;

        // Buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button animateBtn = new Button("Animate Path");
        animateBtn.setDisable(!pathFound);  // Disable if path not found
       animateBtn.setOnAction(e -> sceneManager.showcustomalgo(algoName,executionTime,matrix,path));
       animateBtn.setId("startBtn");

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> sceneManager.showcustom());
        backBtn.setId("exitBtn");

        buttonBox.getChildren().addAll(animateBtn, backBtn);

        root.getChildren().addAll(verdictIcon, stats, buttonBox);
        this.scene= new Scene(root,1000,800);

        String css = this.getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        String fontsCss = this.getClass().getResource("/fonts.css").toExternalForm();
        scene.getStylesheets().add(fontsCss);
    }
    public Scene getScene(){
        return scene;
    }
}

