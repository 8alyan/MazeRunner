package Frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameSelectionScreen {

    private Scene scene;
    private SceneManager sceneManager;

    public GameSelectionScreen(Stage stage, SceneManager sceneManager) {
        this.sceneManager = sceneManager;

        Text title = new Text("Select Game Mode");
        title.getStyleClass().add("title");
        title.getStyleClass().add("monet-title");

        Button normalModeBtn = new Button("Normal Mode");
        normalModeBtn.setId("startBtn");
        
        Button oneVOneBtn = new Button("1v1 Mode");
        oneVOneBtn.setId("leaderboardBtn");
        
        Button customRunnerBtn = new Button("Create Your Own Runner");
        customRunnerBtn.setId("optionsBtn");
        
        Button backBtn = new Button("Back");
        backBtn.setId("exitBtn");

        Button[] buttons = {normalModeBtn, oneVOneBtn, customRunnerBtn, backBtn};
        for (Button btn : buttons) {
            btn.setPrefWidth(280);
            btn.setFont(Font.font("Segoe UI", 18));
            btn.getStyleClass().add("monet-text");
            btn.setMaxWidth(Double.MAX_VALUE);
        }

        // Actions
        normalModeBtn.setOnAction(e -> sceneManager.showdifi());
       oneVOneBtn.setOnAction(e -> sceneManager.showonevone());
       customRunnerBtn.setOnAction(e -> sceneManager.showcustom());
        backBtn.setOnAction(e -> sceneManager.showHome());

        VBox buttonLayout = new VBox(15);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(normalModeBtn, oneVOneBtn, customRunnerBtn, backBtn);
        buttonLayout.getStyleClass().add("monet-container");
        buttonLayout.setMaxWidth(320);
        buttonLayout.setPadding(new Insets(25, 20, 25, 20));

        VBox layout = new VBox(40);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, buttonLayout);

        StackPane rootLayout = new StackPane();
        rootLayout.getChildren().add(layout);


        scene = new Scene(rootLayout, 1000, 800);
        
        // Apply CSS
        String css = this.getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        String fontsCss = this.getClass().getResource("/fonts.css").toExternalForm();
        scene.getStylesheets().add(fontsCss);
    }

    public Scene getScene() {
        return scene;
    }
}
