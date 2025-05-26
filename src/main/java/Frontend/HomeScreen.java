package Frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.stage.Stage;

public class HomeScreen {

    private Scene scene;
    private SceneManager sceneManager;

    public HomeScreen(Stage stage, SceneManager sceneManager) {
        this.sceneManager = sceneManager;

        // Create title with Monet style
        Text titleText = new Text("MAZE RUNNER");

        titleText.getStyleClass().add("monet-title");
        
        // Stack title
        StackPane titlePane = new StackPane();
        titlePane.getChildren().add(titleText);
        titlePane.setAlignment(Pos.CENTER);
        
        // Version number with soft styling
        Text versionText = new Text("v1.0");
        versionText.setFill(Color.web("#6A8FA3"));
        versionText.setOpacity(0.8);
        versionText.getStyleClass().add("monet-text");

        // Buttons
        Button startBtn = new Button("Singleplayer");
        Button leaderboardBtn = new Button("Leader Board");
        Button optionsBtn = new Button("Options");
        Button exitBtn = new Button("Quit Game");
        
        // Set button IDs for specific styling
        startBtn.setId("startBtn");
        leaderboardBtn.setId("leaderboardBtn");
        optionsBtn.setId("optionsBtn");
        exitBtn.setId("exitBtn");

        // Style buttons
        Button[] buttons = {startBtn, leaderboardBtn, optionsBtn, exitBtn};
        for (Button btn : buttons) {
            btn.setPrefWidth(280);
            btn.setFont(Font.font("Segoe UI", 18));
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.getStyleClass().add("monet-text");
        }

        // Button actions
        startBtn.setOnAction(e -> sceneManager.switchtoGameSS());
        leaderboardBtn.setOnAction(e -> sceneManager.showleaderboard());
//        optionsBtn.setOnAction(e -> sceneManager.switchScene("Options"));
        exitBtn.setOnAction(e -> stage.close());

        // Layout for buttons with Monet style
        VBox buttonLayout = new VBox(15); // slightly larger spacing for elegant feel
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(startBtn, leaderboardBtn, optionsBtn, exitBtn);
        buttonLayout.getStyleClass().add("monet-container");
        buttonLayout.setMaxWidth(320);
        buttonLayout.setPadding(new Insets(25, 20, 25, 20));

        // Main layout
        VBox layout = new VBox(40);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titlePane, buttonLayout);
        
        // Add version to bottom right
        StackPane rootLayout = new StackPane();
        rootLayout.getChildren().add(layout);
        rootLayout.getChildren().add(versionText);
        StackPane.setAlignment(versionText, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(versionText, new Insets(0, 15, 10, 0));

        // Scene with elegant size
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
