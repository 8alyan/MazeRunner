package Frontend;

import Backend.Structures.Maze;
import Backend.Runners.BFSkumar;
import Backend.Runners.DFSsan;
import Backend.Runners.DijkstraMaze;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class AnimationEngine {
    private final GridPane gridPane;
    private final int[][] mazeMatrix;
    private final Rectangle[][] gridCells;
    private final int cellSize;
    public long speed;

    private Timeline timeline;
    private Circle runner;

    private final Color WALL_COLOR = Color.web("#08605F");
    private final Color PATH_COLOR = Color.web("#F2F7FA");
    private final Color RUNNER_COLOR = Color.web("#D00000");
    private final Color VISITED_COLOR = Color.web("#87F5FB");
    private final Color FINAL_PATH_COLOR = Color.web("#DE3C4B");

    public AnimationEngine(GridPane gridPane, int[][] mazeMatrix, int cellSize, long speed) {
        this.gridPane = gridPane;
        this.mazeMatrix = mazeMatrix;
        this.cellSize = cellSize;
        this.speed = speed;
        this.gridCells = new Rectangle[mazeMatrix.length][mazeMatrix[0].length];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < mazeMatrix.length; i++) {
            for (int j = 0; j < mazeMatrix[0].length; j++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setArcWidth(4);
                cell.setArcHeight(4);
                cell.setStroke(Color.web("#E6EEF5"));
                cell.setStrokeWidth(0.5);
                cell.setFill(mazeMatrix[i][j] == 1 ? WALL_COLOR : PATH_COLOR);
                gridPane.add(cell, j, i);
                gridCells[i][j] = cell;
            }
        }
        gridCells[mazeMatrix.length-1][mazeMatrix.length-1].setFill(Color.LIGHTGREEN);
        gridCells[0][0].setFill(Color.LIGHTGREY);
    }

    public void playDFS() {

        if (timeline != null) timeline.stop();
        Maze maze = new Maze(mazeMatrix);
        List<int[]> dfs = DFSsan.dfs(maze);

        if (runner != null) gridPane.getChildren().remove(runner);
        runner = new Circle(cellSize / 2.5);
        runner.setFill(RUNNER_COLOR);
        runner.setStroke(Color.WHITE);
        runner.setStrokeWidth(1);
        gridPane.add(runner, 0, 0);
        GridPane.setHalignment(runner, HPos.CENTER);
        GridPane.setValignment(runner, VPos.CENTER);

        timeline = new Timeline();
        Duration delay = Duration.ZERO;
        Duration stepDelay = Duration.millis(speed);

        for (int[] cell : dfs) {
            int row = cell[0], col = cell[1];
            timeline.getKeyFrames().add(new KeyFrame(delay, e -> {
                GridPane.setColumnIndex(runner, col);
                GridPane.setRowIndex(runner, row);
            }));
            delay = delay.add(stepDelay);
        }

        timeline.play();
        timeline.setOnFinished(e -> showStats("DFS", maze));
    }
    public void playDFS1(List<int[]> dfs) {

        if (timeline != null) timeline.stop();

        if (runner != null) gridPane.getChildren().remove(runner);
        runner = new Circle(cellSize / 2.5);
        runner.setFill(RUNNER_COLOR);
        runner.setStroke(Color.WHITE);
        runner.setStrokeWidth(1);
        gridPane.add(runner, 0, 0);
        GridPane.setHalignment(runner, HPos.CENTER);
        GridPane.setValignment(runner, VPos.CENTER);

        timeline = new Timeline();
        Duration delay = Duration.ZERO;
        Duration stepDelay = Duration.millis(speed);

        for (int[] cell : dfs) {
            int row = cell[0], col = cell[1];
            timeline.getKeyFrames().add(new KeyFrame(delay, e -> {
                GridPane.setColumnIndex(runner, col);
                GridPane.setRowIndex(runner, row);
            }));
            delay = delay.add(stepDelay);
        }
        timeline.play();
    }

    public void playBFS() {
        if (timeline != null) timeline.stop();
        Maze maze = new Maze(mazeMatrix);
        List<List<int[]>> bfs = BFSkumar.bfs(maze);
        resetGrid();

        timeline = new Timeline();
        Duration delay = Duration.ZERO;
        Duration stepDelay = Duration.millis(speed);

        for (List<int[]> level : bfs) {
            for (int[] cell : level) {
                int r = cell[0], c = cell[1];
                timeline.getKeyFrames().add(new KeyFrame(delay, e -> {
                    gridCells[r][c].setFill(VISITED_COLOR);
                }));
            }
            delay = delay.add(stepDelay);
        }

        timeline.play();
        timeline.setOnFinished(e -> showStats("BFS", maze));
    }

    public void playDijkstra() {
        if (timeline != null) timeline.stop();
        Maze maze = new Maze(mazeMatrix);
        DijkstraMaze.Result result = DijkstraMaze.runDijkstra(maze);
        List<int[]> visitedOrder = result.visitedOrder;
        List<int[]> path = result.path;

        resetGrid();

        timeline = new Timeline();
        Duration delay = Duration.ZERO;
        Duration stepDelay = Duration.millis(speed);

        for (int[] cell : visitedOrder) {
            int r = cell[0], c = cell[1];
            timeline.getKeyFrames().add(new KeyFrame(delay, e -> {
                gridCells[r][c].setFill(VISITED_COLOR);
            }));
            delay = delay.add(stepDelay);
        }

        for (int[] cell : path) {
            int r = cell[0], c = cell[1];
            timeline.getKeyFrames().add(new KeyFrame(delay, e -> {
                gridCells[r][c].setFill(FINAL_PATH_COLOR);
            }));
            delay = delay.add(stepDelay);
        }

        timeline.play();
        timeline.setOnFinished(e -> showStats("Djikstra", maze));
    }
    private void showStats(String algoName, Maze maze) {

        Stage statsStage = new Stage();
        statsStage.setTitle("Algorithm Stats");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("monet-container");
        layout.setStyle("-fx-background-color: #F2F7FA;");

        Label algoLabel = new Label("Algorithm: " + algoName);
        Label stepsLabel = new Label("Steps Taken: " + maze.stat.steps);
        Label pathLabel = new Label("Walls Hit: " + maze.stat.wallHits);
        Label timeLabel = new Label("Estimated Time: " + maze.stat.timeTaken + "ms");

        // Style the labels
        Label[] labels = {algoLabel, stepsLabel, pathLabel, timeLabel};
        for (Label label : labels) {
            label.getStyleClass().add("monet-text");
            label.setStyle("-fx-font-size: 14px; -fx-text-fill: #4A6B8A;");
        }

        algoLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2E5475;");

        Button restartBtn = new Button("Restart " + algoName);
        Button backBtn = new Button("Back to Menu");

        restartBtn.setId("startBtn");
        backBtn.setId("leaderboardBtn");

        restartBtn.getStyleClass().add("monet-text");
        backBtn.getStyleClass().add("monet-text");

        restartBtn.setOnAction(e -> {
            statsStage.close();
            switch (algoName) {
                case "DFS" -> playDFS();
                case "BFS" -> playBFS();
                case "DIJKSTRA" -> playDijkstra();
            }
        });

        backBtn.setOnAction(e -> {
            statsStage.close();
        });

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(restartBtn, backBtn);

        layout.getChildren().addAll(algoLabel, stepsLabel, pathLabel, timeLabel, buttonBox);

        Scene statsScene = new Scene(layout, 350, 250);

        // Apply CSS to the stats scene
        String css = this.getClass().getResource("/styles.css").toExternalForm();
        statsScene.getStylesheets().add(css);
        String fontsCss = this.getClass().getResource("/fonts.css").toExternalForm();
        statsScene.getStylesheets().add(fontsCss);

        statsStage.setScene(statsScene);
        statsStage.show();
    }

    private void resetGrid() {
        for (int i = 0; i < mazeMatrix.length; i++) {
            for (int j = 0; j < mazeMatrix[0].length; j++) {
                gridCells[i][j].setFill(mazeMatrix[i][j] == 1 ? WALL_COLOR : PATH_COLOR);
            }
        }
    }

    public void restartCurrent() {
    }
}
