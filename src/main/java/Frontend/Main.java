package Frontend;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {

        Font.loadFont(getClass().getResourceAsStream("/fonts/BebasNeue-Regular.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Baumans-Regular.ttf"), 10);
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.showHome(); // Start with home screen
        stage.setTitle("MAZERUNNER");
        stage.show();
    }
}
