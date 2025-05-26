package Backend;

import Frontend.SceneManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Random;

public class BenchmarkRunner {

    public static void test(Runner algo, SceneManager sceneManager,String name) throws Exception {
        List<int[][]> mazes = loadMazes("/home/8alyan/Projects/Vault/MazeRunnerv1/src/main/java/Backend/mazes.json");
        boolean a =true;
        int total = mazes.size();
        int successCount = 0;
        double totalTime = 0;

        for (int i = 0; i < total; i++) {
            int[][] maze = mazes.get(i);

            long start = System.nanoTime();
            List<int[]> path = algo.run(maze);
            long end = System.nanoTime();

            totalTime += (end - start);

            if (isValidPath(path, maze)) {
                successCount++;
            } else {
                System.out.println("❌ Failed on maze #" + (i + 1));
                a=false;
                break;
            }
        }
        Random r = new Random();
        int[][] mz = mazes.get(r.nextInt(0,99));
        List<int[]> pathi= algo.run(mz);
        double avgTimeMs = (totalTime / 1_000_000.0) / total;
        if(a){
            LeaderboardEntry.addEntry(name,totalTime,"/home/8alyan/Projects/Vault/MazeRunnerv1/src/main/java/Backend/leaderboard.json");
        }
        sceneManager.showstat(name,a,totalTime,mz,pathi);

    }

    public static List<int[][]> loadMazes(String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filePath), new TypeReference<>() {});
    }

    // Very basic path validator: check start & end match and only walk on zeros
    public static boolean isValidPath(List<int[]> path, int[][] maze) {
        if (path == null || path.isEmpty()) return false;
        int rows = maze.length, cols = maze[0].length;

        int[] start = path.get(0), end = path.get(path.size() - 1);
        if (start[0] != 0 || start[1] != 0 || end[0] != rows - 1 || end[1] != cols - 1) {
            return false;
        }

        for (int i = 1; i < path.size(); i++) {
            int[] prev = path.get(i - 1), curr = path.get(i);
            int dx = Math.abs(prev[0] - curr[0]), dy = Math.abs(prev[1] - curr[1]);

            if (dx + dy != 1 || curr[0] < 0 || curr[1] < 0 || curr[0] >= rows || curr[1] >= cols)
                return false;

            if (maze[curr[0]][curr[1]] != 0) return false;
        }

        return true;
    }
}
