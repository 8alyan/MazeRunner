package Frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RunnerSelectionScreen1v1 {

    private Scene scene;
    private String player1Choice = null;
    private String player2Choice = null;
    public int diff;
    SceneManager sceneManager;

    public RunnerSelectionScreen1v1(Stage stage, SceneManager sceneManager,int i) {
        diff=i;
        this.sceneManager=sceneManager;
        // Title
        Text title = new Text("Choose Runners for 1v1");
        title.getStyleClass().add("monet-title");

        // Create two selection panels
        VBox player1Layout = createRunnerSelector("Player 1", true);
        VBox player2Layout = createRunnerSelector("Player 2", false);

        // Start Button (disabled by default)
        Button startButton = new Button("Start 1v1 Battle");
        startButton.setDisable(true);
        startButton.setId("startBtn");
        startButton.setFont(Font.font("Segoe UI", 20));
        Button exitbutton = new Button("Back");
        exitbutton.setId("exitBtn");
        exitbutton.setOnAction(e->sceneManager.showonevone());
        startButton.setOnAction(e -> {
            System.out.println("Starting 1v1 with " + player1Choice + " vs " + player2Choice);
            sceneManager.show1v1GameScreen(player1Choice, player2Choice,diff); // You implement this
        });

        HBox runnerLayout = new HBox(80);
        runnerLayout.setAlignment(Pos.CENTER);
        runnerLayout.setPadding(new Insets(30));
        runnerLayout.getChildren().addAll(player1Layout, player2Layout);

        VBox mainLayout = new VBox(40);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(30));
        mainLayout.getChildren().addAll(title, runnerLayout, startButton,exitbutton);

        scene = new Scene(mainLayout, 1000, 800);

        // Method to check both players selected
        Runnable updateStartBtn = () -> {
            if (player1Choice != null && player2Choice != null)
                startButton.setDisable(false);
        };

        // Store this in lambdas below 👇
        this.updateStartBtn = updateStartBtn;
        String css = this.getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        String fontsCss = this.getClass().getResource("/fonts.css").toExternalForm();
        scene.getStylesheets().add(fontsCss);
    }

    private Runnable updateStartBtn;

    private VBox createRunnerSelector(String label, boolean isPlayer1) {
        Text playerLabel = new Text(label);
        playerLabel.getStyleClass().add("monet-title2");

        RadioButton dfsBtn = new RadioButton     ("DFS San");
        RadioButton bfsBtn = new RadioButton     ("BFS Kumar");
        RadioButton dijkstraBtn = new RadioButton("Dijkstra Smith");

        // Group them so only one can be selected at a time
        ToggleGroup group = new ToggleGroup();
        dfsBtn.setToggleGroup(group);
        bfsBtn.setToggleGroup(group);
        dijkstraBtn.setToggleGroup(group);


        // Listener to update player choices
        group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selected = (RadioButton) newToggle;
                String selectedText = selected.getText().split(" ")[0]; // Just get "DFS", "BFS", etc.
                if (isPlayer1) player1Choice = selectedText;
                else player2Choice = selectedText;
                updateStartBtn.run(); // Check if both players have selected
            }
        });

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.getChildren().addAll(playerLabel, dfsBtn, bfsBtn, dijkstraBtn);
        layout.setPadding(new Insets(25));
        layout.getStyleClass().add("monet-container");
        layout.setMaxWidth(300);

        return layout;
    }


    public Scene getScene() {
        return scene;
    }
}
