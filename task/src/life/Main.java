package life;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Cell {
    private int row, col;
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

class World {
    public static final int[] ROW_DIR = new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
    public static final int[] COL_DIR = new int[] { -1, 0, 1, -1, 1, -1, 0, 1 };
    private final int size;
    private final int seed;
    private final Random random;
    private boolean[][] grid;

    public World(int size, int seed) {
        this.size = size;
        this.seed = seed;
        this.random = new Random(seed);
    }

    public int getSize() {
        return size;
    }

    public int getSeed() {
        return seed;
    }

    public Random getRandom() {
        return random;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    void populate() {
        grid = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = random.nextBoolean();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(grid[i][j] ? "O" : " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void evolve() {
        boolean[][] evolvedGrid = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell(i, j);
                boolean cellAlive = isAlive(cell);
                evolvedGrid[i][j] = cellAlive;
            }
        }
        grid = evolvedGrid;
    }

    private boolean isAlive(Cell cell) {
        if (grid[cell.getRow()][cell.getCol()]) {
            return survived(cell);
        }
        else {
            return reborn(cell);
        }
    }

    private boolean survived(Cell cell) {
        /* alive cell survives if has two or three alive neighbors */
        int sum = getNeighborSum(cell);
        return sum == 2 || sum == 3;
    }

    private boolean reborn(Cell cell) {
        /* dead cell is reborn if it has exactly three alive neighbors */
        int sum = getNeighborSum(cell);
        return sum == 3;
    }

    private int getNeighborSum(Cell cell) {
        ArrayList<Integer> neighbors = getNeighbors(cell);
        return neighbors.stream().mapToInt(Integer::intValue).sum();
    }

    private ArrayList<Integer> getNeighbors(Cell cell) {
        ArrayList<Integer> neighbors = new ArrayList<>();
        // get all 8 neighbors, wrap at corners
        for (int i = 0; i < 8; i++) {
            int row = wrapCorners(cell.getRow() + ROW_DIR[i]);
            int col = wrapCorners(cell.getCol() + COL_DIR[i]);
            neighbors.add(grid[row][col] ? 1 : 0);
        }
        return neighbors;
    }

    private int wrapCorners(int index) {
        index %= size;
        if (index < 0) index += size;
        return index;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int worldSize = sc.nextInt();
        int seed = sc.nextInt();
        int generations = sc.nextInt();
        World world = new World(worldSize, seed);
        world.populate();
        for (int i = 0; i < generations; i++) {
            world.evolve();
        }
        System.out.println(world);
    }

}
