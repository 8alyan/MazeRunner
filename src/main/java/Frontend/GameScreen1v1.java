package Frontend;



import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class GameScreen1v1 {

    private int cellSize = 30;
    public static int[][] mazeMatrix;
    private Scene scene;
    private  static AnimationEngine e ;
    private long speed = 200; // default speed 300 ms per step
    private SceneManager sceneManager;

    public GameScreen1v1(SceneManager sceneManager,String a, String b,int i){
        if(i==0){cellSize=60;}
        else if (i==1) {cellSize=35;}
        else{cellSize=25;}
        this.sceneManager = sceneManager;
        display(sceneManager,a,b,mazeMatrix,i);
    }

    public void display(SceneManager sceneManager, String algo1, String algo2, int[][] mazeMatrix,int i) {
        Text titleText = new Text("GAME ON");

        titleText.getStyleClass().add("monet-title");
        HBox ti = new HBox(30,titleText);
        ti.setAlignment(Pos.CENTER);
        ti.setPadding(new Insets(55));

        BorderPane root = new BorderPane();

        HBox grids = new HBox();
        grids.setAlignment(Pos.CENTER);
        grids.setSpacing(50);
        grids.setPadding(new Insets(20));
        // Left Grid
        GridPane gridLeft = new GridPane();
        gridLeft.setHgap(1);
        gridLeft.setVgap(1);
        gridLeft.setAlignment(Pos.CENTER_LEFT);

        // Right Grid
        GridPane gridRight = new GridPane();
        gridRight.setHgap(1);
        gridRight.setVgap(1);
        gridRight.setAlignment(Pos.CENTER_RIGHT);

        grids.getChildren().addAll(gridLeft,gridRight);
        grids.setAlignment(Pos.CENTER);
        root.setCenter(grids);

        // Deep copy of maze for each engine to ensure independent playthroughs
        int[][] mazeCopy1 = copyMaze(mazeMatrix);
        int[][] mazeCopy2 = copyMaze(mazeMatrix);

        AnimationEngine engine1 = new AnimationEngine(gridLeft, mazeCopy1, cellSize, speed);
        AnimationEngine engine2 = new AnimationEngine(gridRight, mazeCopy2, cellSize, speed);

        Button startBtn = new Button("Start 1v1");
        startBtn.setId("startBtn");

        startBtn.setOnAction(e -> {
            playAlgorithm(engine1, algo1);
            playAlgorithm(engine2, algo2);
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> sceneManager.showrunners1v1(i));
        backBtn.setId("exitBtn");

        HBox buttons = new HBox(20, startBtn, backBtn);
        buttons.setPadding(new Insets(60));
        buttons.setAlignment(Pos.CENTER);
        root.setBottom(buttons);
        root.setTop(ti);
        this.scene = new Scene(root, 1000, 800);
        // Apply CSS
        String css = this.getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        String fontsCss = this.getClass().getResource("/fonts.css").toExternalForm();
        scene.getStylesheets().add(fontsCss);
    }

    private static int[][] copyMaze(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[0].length);
        }
        return copy;
    }

    private static void playAlgorithm(AnimationEngine engine, String algoName) {
        switch (algoName.toUpperCase()) {
            case "DFS" -> engine.playDFS();
            case "BFS" -> engine.playBFS();
            case "DIJKSTRA" -> engine.playDijkstra();
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algoName);
        }
    }

    public Scene getscene() {
        return scene;
    }
}
