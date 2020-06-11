package life;

import java.util.Random;
import java.util.Scanner;

class World {
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
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int worldSize = sc.nextInt();
        int seed = sc.nextInt();
        World world = new World(worldSize, seed);
        world.populate();
        System.out.println(world);
    }

}
