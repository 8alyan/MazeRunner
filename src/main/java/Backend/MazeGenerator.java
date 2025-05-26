package Backend;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

public class MazeGenerator {
    public static void main(String[] args) {
        List<int[][]> mazes = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            int[][] maze = generateMaze(20, 20);
            mazes.add(maze);
        }

        saveMazesToJson(mazes, "/home/8alyan/Projects/Vault/MazeRunnerv1/src/main/java/Backend/mazes.json");
        System.out.println("✅ 100 mazes generated and saved to mazes.json");
    }

    public static int[][] generateMaze(int rows, int cols) {
        int[][] maze = new int[rows][cols];
        boolean[][] visited = new boolean[rows][cols];

        // 1. Use DFS to carve a valid path
        dfs(0, 0, rows, cols, maze, visited);

        // 2. Add random obstacles (1s) elsewhere, skip the path
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    maze[i][j] = rand.nextDouble() < 0.3 ? 1 : 0; // ~30% chance of wall
                }
            }
        }

        return maze;
    }

    private static boolean dfs(int x, int y, int rows, int cols, int[][] maze, boolean[][] visited) {
        if (x < 0 || y < 0 || x >= rows || y >= cols || visited[x][y]) return false;
        visited[x][y] = true;
        maze[x][y] = 0;

        if (x == rows - 1 && y == cols - 1) return true; // reached goal

        List<int[]> directions = Arrays.asList(
                new int[]{1, 0}, new int[]{0, 1}, new int[]{-1, 0}, new int[]{0, -1}
        );
        Collections.shuffle(directions); // randomness

        for (int[] dir : directions) {
            if (dfs(x + dir[0], y + dir[1], rows, cols, maze, visited)) return true;
        }

        return false; // dead end
    }

    public static void saveMazesToJson(List<int[][]> mazes, String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(fileName), mazes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

