package life;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class World {
    public static final int[] ROW_DIR = new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
    public static final int[] COL_DIR = new int[] { -1, 0, 1, -1, 1, -1, 0, 1 };
    public static final Long DELAY = 100L;
    private final int size;
    private int seed;
    private final Random random;
    private boolean[][] grid;

    public World(int size) {
        this.size = size;
        this.random = new Random();
    }

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

    public void visualizeEvolution(int generations) throws InterruptedException {
        System.out.println(this.toString());
        for (int i = 0; i < generations; i++) {
            int alive = evolve();

            clearScreen();

            Thread.sleep(DELAY);

            System.out.printf("Generation #%d\n", i+1);
            System.out.printf("Alive: %d\n", alive);
            System.out.println(this.toString());

        }
    }

    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }
        catch (IOException | InterruptedException e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public int evolve() {
        boolean[][] evolvedGrid = new boolean[size][size];
        int aliveCells = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell(i, j);
                boolean cellAlive = isAlive(cell);
                aliveCells += cellAlive ? 1 : 0;
                evolvedGrid[i][j] = cellAlive;
            }
        }
        grid = evolvedGrid;
        return aliveCells;
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
