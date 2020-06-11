package life;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        // Stage 1
//        let_there_be_light(sc);
        // Stage 2
//        generations(sc);
        // Stage 3
//        life_goes_by(sc);
        // Stage 4
        looking_good(sc);
    }

    private static void looking_good(Scanner sc) {

    }

    private static void generations(Scanner sc) {
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

    private static void let_there_be_light(Scanner sc) {
        int worldSize = sc.nextInt();
        int seed = sc.nextInt();
        World world = new World(worldSize, seed);
        world.populate();
        System.out.println(world);
    }

    private static void life_goes_by(Scanner sc) throws InterruptedException {
        int worldSize = sc.nextInt();
        int generations = 10;
        World world = new World(worldSize);

        world.populate();

        world.visualizeEvolution(generations);
    }

}
