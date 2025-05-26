package Frontend;

import Backend.Structures.Maze;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class GameScreen {
    private Scene scene;
    private GridPane gridPane;
    private int cellSize = 30;
    public static int[][] mazeMatrix;
    private  static AnimationEngine e ;
    private long speed = 300; // default speed 300 ms per step
    private SceneManager sceneManager;

    // Track which algorithm is currently running ("DFS", "BFS", "DIJKSTRA", or null)
    private String currentAlgorithm = null;


    public GameScreen(Stage stage, SceneManager sceneManager,int i) {
        if(i==0){cellSize=60;}
        else if (i==1) {cellSize=35;}
        else{cellSize=25;}

        this.sceneManager = sceneManager;
        BorderPane root = new BorderPane();

        // Top menu buttons
        HBox topMenu = new HBox(15);
        topMenu.setPadding(new Insets(15));
        topMenu.setAlignment(Pos.TOP_CENTER);

        Button exitBtn = new Button("Exit");
        Button speedBtn = new Button("Toggle Speed");

        exitBtn.setId("exitBtn");
        speedBtn.setId("leaderboardBtn");

        exitBtn.getStyleClass().add("monet-text");
        speedBtn.getStyleClass().add("monet-text");

        exitBtn.setOnAction(e -> sceneManager.showrunners(i));


        topMenu.getChildren().addAll(exitBtn, speedBtn);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setGridLinesVisible(false); // No grid lines for cleaner look
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setPadding(new Insets(10));
        gridPane.setBackground(javafx.scene.layout.Background.EMPTY);


        root.setTop(topMenu);
        root.setCenter(gridPane);

        this.scene = new Scene(root, 1000, 800);
        e = new AnimationEngine(gridPane,mazeMatrix,cellSize,speed);
        speedBtn.setOnAction(p -> {
            speed = (speed == 300) ? 100 : 300;  // toggle speed
            System.out.println("Speed set to: " + speed + "ms per move");
            e.speed=speed;
            // Restart current animation with new speed

            if (currentAlgorithm != null) {
                switch (currentAlgorithm) {
                    case "DFS" -> animatedfs();
                    case "BFS" -> animateBFS();
                    case "DIJKSTRA" -> animateDijkstra();
                }
            }
        });

        // Apply CSS
        String css = this.getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        String fontsCss = this.getClass().getResource("/fonts.css").toExternalForm();
        scene.getStylesheets().add(fontsCss);
    }

    public void animatedfs() {
        currentAlgorithm = "DFS";
        e.playDFS();

    }

    public void animateBFS() {
        currentAlgorithm = "BFS";
        e.playBFS();

    }

    public void animateDijkstra() {
        currentAlgorithm = "DIJKSTRA";
        e.playDijkstra();
    }

    public Scene getScene() {
        return scene;
    }


}
