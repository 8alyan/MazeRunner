package Frontend;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class SceneManager {
    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;

    }

    public void showHome() {
        HomeScreen home = new HomeScreen(stage,this);
        stage.setScene(home.getScene());
    }

    public void switchtoGameSS() {
        GameSelectionScreen gs = new GameSelectionScreen(stage,this);
        stage.setScene(gs.getScene());
    }

    public void showdifi() {
        NormalModeSelectionScreen ss = new NormalModeSelectionScreen(stage,this);
        stage.setScene(ss.getScene());
    }

    public void showrunners(int i){
        RunnerSelectionScreen rs = new RunnerSelectionScreen(stage,this,i);
        stage.setScene(rs.getScene());
    }
    public void showgamescreen(int i,int dif){
        GameScreen gs = new GameScreen(stage,this,dif);
        stage.setScene(gs.getScene());
        if(i==1){gs.animateBFS();}
        if(i==0){gs.animatedfs();}
        if(i==2){gs.animateDijkstra();}

    }

    public void showonevone() {
        OnevOneModeSelectionScreen os= new OnevOneModeSelectionScreen(stage,this);
        stage.setScene(os.getScene());
    }

    public void showrunners1v1(int i) {
        RunnerSelectionScreen1v1 rs = new RunnerSelectionScreen1v1(stage,this,i);
        stage.setScene(rs.getScene());
    }

    public void show1v1GameScreen(String player1Choice, String player2Choice,int diff) {
        GameScreen1v1 gs = new GameScreen1v1(this,player1Choice,player2Choice,diff);
        stage.setScene(gs.getscene());
    }

    public void showcustom() {
        CustomAlgoEditor cs = new CustomAlgoEditor(stage,this);
        stage.setScene(cs.getScene());
    }

    public void showstat(String name, boolean b, double avg, int[][] maze, List<int[]> path) {
        StatScreen sc = new StatScreen(this,name,b,avg,maze,path);
        stage.setScene(sc.getScene());
    }

    public void showcustomalgo(String name,double time,int[][] maze, List<int[]> path) {
        customalgoanimation cgs = new customalgoanimation(stage,this,name,time,maze,path);
        stage.setScene(cgs.getScene());
    }
    public void showleaderboard(){
        LeaderboardScreen ls = new LeaderboardScreen(stage,this);
        stage.setScene(ls.getScene());
    }

//
}
