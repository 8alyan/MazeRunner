package Backend.Runners;

import Backend.Structures.Maze;

import java.util.ArrayList;
import java.util.List;

public class DFSsan {


    public static List<int[]> dfs(Maze maze) {
        long start = System.nanoTime();
        boolean[][] vis = new boolean[maze.getEnd()[0] + 1][maze.getEnd()[1] + 1];
        List<int[]> path = new ArrayList<>();
        helper(0, 0, maze, vis, path);
        long end = System.nanoTime();
        maze.stat.timeTaken=  ((end - start) / 1000000.0);
        return path;
    }

    public static boolean helper(int i, int j, Maze maze, boolean[][] vis, List<int[]> path) {
        if (!maze.isWalkable(i, j)) return false;

        vis[i][j] = true;
        path.add(new int[]{i, j});

        if (i == vis.length-1 && j == vis[0].length-1) return true;

        // Explore all directions
        if ((i< vis.length-1&&!vis[i+1][j])&&helper(i + 1, j, maze, vis, path)){return true;}
        else if (path.getLast()[0]!=i||path.getLast()[1]!=j){path.add(new int[]{i, j});}
        if((i>0&&!vis[i-1][j])&&helper(i - 1, j, maze, vis, path)){return true;}
        else if (path.getLast()[0]!=i||path.getLast()[1]!=j){path.add(new int[]{i, j});}
        if((j>0&&!vis[i][j-1])&&helper(i, j - 1, maze, vis, path)) {return true;}
        else if (path.getLast()[0]!=i||path.getLast()[1]!=j){path.add(new int[]{i, j});}
        if((j<vis[0].length-1&&!vis[i][j+1])&&helper(i, j + 1, maze, vis, path)){return true;}
        else if (path.getLast()[0]!=i||path.getLast()[1]!=j){path.add(new int[]{i, j});}


        return false;
    }

}
