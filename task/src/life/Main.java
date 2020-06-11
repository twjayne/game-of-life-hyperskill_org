package life;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int worldSize = sc.nextInt();
        int generations = 10;
        World world = new World(worldSize);

        world.populate();

        world.visualizeEvolution(generations);
    }

}
