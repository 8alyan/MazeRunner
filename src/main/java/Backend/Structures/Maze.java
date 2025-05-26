package Backend.Structures;


import Backend.Runners.DFSsan;

import java.util.List;

    public class Maze {
        private int[][] grid;
        private int rows, cols;
        private int[] start = {0, 0};
        private int[] end;
        public MazeStat stat=new MazeStat();


    public Maze(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.end = new int[]{rows - 1, cols - 1};
    }

    public int[][] getGrid() {
        return grid;
    }

    public int[] getStart() {
        return start;
    }

    public int[] getEnd() {
        return end;
    }
    public boolean isInsideMaze(int row, int col) {
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

    public boolean isWalkable(int row, int col) {
        if(!isInsideMaze(row, col)||grid[row][col] == 1){stat.wallHits++;return false;}
        stat.steps++;
        return true;
    }

    public static void main(String[] args) {
        int[][] medium = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        Maze m = new Maze(medium);
        List<int[]> dfs = DFSsan.dfs(m);
        for(int[] n: dfs) {
            System.out.println(n[0] + "-" + n[1]);
        }

    }

        public int getRows() {
        return rows;
        }
        public int getCols(){
        return cols;
        }
    }
