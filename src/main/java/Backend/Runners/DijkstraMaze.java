package Backend.Runners;

import Backend.Structures.Maze;

import java.util.*;

public class DijkstraMaze{
    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    public static Result runDijkstra(Maze maze) {
        long star = System.nanoTime();
        int rows = maze.getRows();
        int cols = maze.getCols();
        int[] start = maze.getStart();
        int[] end = maze.getEnd();

        int[][] dist = new int[rows][cols];
        for (int[] row : dist)
            Arrays.fill(row, Integer.MAX_VALUE);

        int[][] parent = new int[rows * cols][2];
        for (int i = 0; i < rows * cols; i++) {
            parent[i][0] = -1;
            parent[i][1] = -1;
        }

        List<int[]> visitedOrder = new ArrayList<>();
        dist[start[0]][start[1]] = 0;

        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingInt(c -> c.dist));
        pq.offer(new Cell(start[0], start[1], 0));

        while (!pq.isEmpty()) {
            Cell current = pq.poll();
            int r = current.r, c = current.c;

            if (dist[r][c] < current.dist) continue; // already visited with better dist
            visitedOrder.add(new int[]{r, c});

            if (r == end[0] && c == end[1]) break;

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i], nc = c + dc[i];
                if (maze.isWalkable(nr,nc)) {
                    int newDist = dist[r][c] + 1;
                    if (newDist < dist[nr][nc]) {
                        dist[nr][nc] = newDist;
                        pq.offer(new Cell(nr, nc, newDist));
                        parent[nr * cols + nc] = new int[]{r, c};
                    }
                }
            }
        }
        long en = System.nanoTime();
        maze.stat.timeTaken=  ((en - star) / 1000000.0);

        // reconstruct path
        List<int[]> path = new ArrayList<>();
        int cr = end[0], cc = end[1];
        if (dist[cr][cc] == Integer.MAX_VALUE) return new Result(visitedOrder, path); // no path found

        while (!(cr == start[0] && cc == start[1])) {
            path.add(new int[]{cr, cc});
            int[] p = parent[cr * cols + cc];
            cr = p[0];
            cc = p[1];
        }
        path.add(start);
        Collections.reverse(path);

        return new Result(visitedOrder, path);
    }

    public static class Result {
        public List<int[]> visitedOrder;
        public List<int[]> path;

        public Result(List<int[]> visitedOrder, List<int[]> path) {
            this.visitedOrder = visitedOrder;
            this.path = path;
        }
    }

    static class Cell {
        int r, c, dist;

        Cell(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }
    }
}
