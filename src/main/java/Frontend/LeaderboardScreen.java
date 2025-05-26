package Frontend;

import Backend.LeaderboardEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardScreen {

    private Scene scene;

    public LeaderboardScreen(Stage stage, SceneManager sceneManager) {
        // Title
        Text title = new Text("🏆 Algorithm Leaderboard (Top 10)");
        title.setFont(Font.font("Bebas Neue", 40));
        title.getStyleClass().add("monet-title");

        // Table setup
        TableView<LeaderboardEntry> table = new TableView<>();
        table.setPrefWidth(500);

        TableColumn<LeaderboardEntry, String> nameCol = new TableColumn<>("Runner Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(250);

        TableColumn<LeaderboardEntry, Double> timeCol = new TableColumn<>("Time Taken (ms)");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeCol.setPrefWidth(250);

        table.getColumns().addAll(nameCol, timeCol);

        // Load entries
        List<LeaderboardEntry> entries = loadEntriesFromJson("/home/8alyan/Projects/Vault/MazeRunnerv1/src/main/java/Backend/leaderboard.json");
        Collections.sort(entries, Comparator.comparingDouble(LeaderboardEntry::getTime));

        ObservableList<LeaderboardEntry> top10 = FXCollections.observableArrayList(
                entries.subList(0, Math.min(10, entries.size()))
        );
        table.setItems(top10);

        // VBox for title and table
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(30));
        centerBox.getChildren().addAll(title, table);

        // Back Button
        Button backButton = new Button("← Back");
        backButton.setFont(Font.font("Segoe UI", 16));
        backButton.setOnAction(e -> sceneManager.showHome()); // Change to the correct scene method
        backButton.setPadding(new Insets(10, 20, 10, 20));
        backButton.setId("exitBtn");

        VBox bottomBox = new VBox(backButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));

        // Main layout
        BorderPane layout = new BorderPane();
        layout.setCenter(centerBox);
        layout.setBottom(bottomBox);

        this.scene = new Scene(layout, 1000, 800);

        String css = this.getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        String fontsCss = this.getClass().getResource("/fonts.css").toExternalForm();
        scene.getStylesheets().add(fontsCss);
    }

    private List<LeaderboardEntry> loadEntriesFromJson(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(filePath), new TypeReference<List<LeaderboardEntry>>() {});
        } catch (Exception e) {
            System.out.println("Error loading leaderboard JSON: " + e.getMessage());
            return List.of();
        }
    }

    public Scene getScene() {
        return scene;
    }
}
