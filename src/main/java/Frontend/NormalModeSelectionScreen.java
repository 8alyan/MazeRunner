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

public class NormalModeSelectionScreen {

    private Scene scene;
    private SceneManager sceneManager;

    public NormalModeSelectionScreen(Stage stage, SceneManager sceneManager) {
        this.sceneManager = sceneManager;

        Text title = new Text("Select Difficulty");
        title.setFont(Font.font("Segoe UI", 36));
        title.getStyleClass().add("title");
        title.getStyleClass().add("monet-title");

        Button easyBtn = new Button("Easy");
        easyBtn.setId("startBtn");
        
        Button mediumBtn = new Button("Medium");
        mediumBtn.setId("leaderboardBtn");
        
        Button hardBtn = new Button("Hard");
        hardBtn.setId("optionsBtn");
        
        Button backBtn = new Button("Back");
        backBtn.setId("exitBtn");

        Button[] buttons = {easyBtn, mediumBtn, hardBtn, backBtn};
        for (Button btn : buttons) {
            btn.setPrefWidth(280);
            btn.setFont(Font.font("Segoe UI", 18));
            btn.getStyleClass().add("monet-text");
            btn.setMaxWidth(Double.MAX_VALUE);
        }

        // Actions for difficulty buttons
        easyBtn.setOnAction(e -> {
            System.out.println("Easy mode selected");
            Backend.GridGenerator.gridsuupply(0,0);
            sceneManager.showrunners(0);
        });
        mediumBtn.setOnAction(e -> {
            System.out.println("Medium mode selected");
            Backend.GridGenerator.gridsuupply(1,0);
            sceneManager.showrunners(1);
        });
        hardBtn.setOnAction(e -> {
            System.out.println("Hard mode selected");
            Backend.GridGenerator.gridsuupply(2,0);
            sceneManager.showrunners(2);
        });
        backBtn.setOnAction(e -> sceneManager.switchtoGameSS());

        VBox buttonLayout = new VBox(15);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(easyBtn, mediumBtn, hardBtn, backBtn);
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
