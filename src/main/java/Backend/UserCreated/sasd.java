package Backend.UserCreated;

import Backend.Runner;
import java.util.*;

public class sasd implements Runner {
    @Override
    public List<int[]> run(int[][] maze) {
                    int n = maze.length;
        int m = maze[0].length;

        int[] dirX = {0, 1, 0, -1};  // Right, Down, Left, Up
        int[] dirY = {1, 0, -1, 0};

        boolean[][] visited = new boolean[n][m];
        List<int[]> path = new ArrayList<>();
        Random rand = new Random();

        int x = 0, y = 0;
        visited[x][y] = true;
        path.add(new int[]{x, y});

        while (!(x == n - 1 && y == m - 1)) {
            List<int[]> neighbors = new ArrayList<>();

            for (int d = 0; d < 4; d++) {
                int nx = x + dirX[d];
                int ny = y + dirY[d];

                if (nx >= 0 && ny >= 0 && nx < n && ny < m &&
                        maze[nx][ny] == 0 && !visited[nx][ny]) {
                    neighbors.add(new int[]{nx, ny});
                }
            }

            if (neighbors.isEmpty()) {
                // backtrack
                path.remove(path.size() - 1);
                if (path.isEmpty()) return new ArrayList<>();  // No path

                int[] prev = path.get(path.size() - 1);
                x = prev[0];
                y = prev[1];
            } else {
                int[] next = neighbors.get(rand.nextInt(neighbors.size()));
                x = next[0];
                y = next[1];
                visited[x][y] = true;
                path.add(new int[]{x, y});
            }
        }

        return path;
}
}
