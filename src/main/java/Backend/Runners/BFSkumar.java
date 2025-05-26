package Backend.Runners;
import Backend.Structures.Maze;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSkumar {

    public static List<List<int[]>> bfs(Maze maze) {
        long start = System.nanoTime();
        System.out.println("BFS start time: " + start);
        List<List<int[]>> path = new ArrayList<>();
        int endX = maze.getEnd()[0];
        int endY = maze.getEnd()[1];
        boolean[][] vis = new boolean[endX + 1][endY + 1];

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0,0});

        while (!queue.isEmpty()) {
            List<int[]> temp = new ArrayList<>();;
            int r = queue.size();;
            for (int i = 0; i <r ; i++) {
            int[] p = queue.poll();

            if (!maze.isWalkable(p[0], p[1]) || vis[p[0]][p[1]]) {
                continue;
            }
            vis[p[0]][p[1]] = true;
            temp.add(new int[]{p[0],p[1]});
            if (p[0] == endX && p[1] == endY) {
                path.add(temp);
                long end = System.nanoTime();
                System.out.println("BFS end time: " + end);
                maze.stat.timeTaken=  ((end - start) / 1000000.0);
                return path;
            }
            queue.add(new int[]{p[0]+1,p[1]});
            queue.add(new int[]{p[0]-1,p[1]});
            queue.add(new int[]{p[0],p[1]+1});
            queue.add(new int[]{p[0],p[1]-1});
        }
            path.add(temp);
        }


        return path;
    }
}

