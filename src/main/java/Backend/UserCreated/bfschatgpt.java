package Backend.UserCreated;

import Backend.Runner;
import java.util.*;

public class bfschatgpt implements Runner {
    @Override
    public List<int[]> run(int[][] maze) {
                    int n = maze.length;
        int m = maze[0].length;

        boolean[][] visited = new boolean[n][m];
        int[][][] parent = new int[n][m][2];  // to backtrack path

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        visited[0][0] = true;

        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0], y = current[1];

            if (x == n - 1 && y == m - 1) {
                // Reached goal, build path
                List<int[]> path = new ArrayList<>();
                while (!(x == 0 && y == 0)) {
                    path.add(new int[]{x, y});
                    int[] p = parent[x][y];
                    x = p[0];
                    y = p[1];
                }
                path.add(new int[]{0, 0});
                Collections.reverse(path);
                return path;
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX >= 0 && newX < n && newY >= 0 && newY < m &&
                    maze[newX][newY] == 0 && !visited[newX][newY]) {
                    
                    visited[newX][newY] = true;
                    parent[newX][newY] = new int[]{x, y};
                    queue.add(new int[]{newX, newY});
                }
            }
        }

        // No path found
        return new ArrayList<>();
}
}
