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

public class RunnerSelectionScreen {

    private Scene scene;
    private SceneManager sceneManager;
    public int dif;

    public RunnerSelectionScreen(Stage stage, SceneManager sceneManager,int i) {
        this.sceneManager = sceneManager;
        dif=i;

        Text title = new Text("Select Your Runner");
        title.setFont(Font.font("Segoe UI", 36));
        title.getStyleClass().add("title");
        title.getStyleClass().add("monet-title");

        Button dfsBtn = new Button("DFS San");
        dfsBtn.setId("startBtn");
        
        Button bfsBtn = new Button("BFS Kumar");
        bfsBtn.setId("leaderboardBtn");
        
        Button dijkstraBtn = new Button("Dijkstra Smith");
        dijkstraBtn.setId("optionsBtn");
        
        Button backBtn = new Button("Back");
        backBtn.setId("exitBtn");

        Button[] buttons = {dfsBtn, bfsBtn, dijkstraBtn, backBtn};
        for (Button btn : buttons) {
            btn.setPrefWidth(280);
            btn.setFont(Font.font("Segoe UI", 18));
            btn.getStyleClass().add("monet-text");
            btn.setMaxWidth(Double.MAX_VALUE);
        }

        dfsBtn.setOnAction(e -> {
            sceneManager.showgamescreen(0,dif);
            System.out.println("DFS San selected");
        });

        bfsBtn.setOnAction(e -> {
            sceneManager.showgamescreen(1,dif);
            System.out.println("BFS Kumar selected");
        });

        dijkstraBtn.setOnAction(e -> {
            sceneManager.showgamescreen(2,dif);
            System.out.println("Dijkstra Smith selected");
        });

        backBtn.setOnAction(e -> sceneManager.showdifi());

        VBox buttonLayout = new VBox(15);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(dfsBtn, bfsBtn, dijkstraBtn, backBtn);
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
