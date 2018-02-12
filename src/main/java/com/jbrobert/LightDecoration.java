package com.jbrobert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This program places lights on a grid.
 * From a list of instructions entered in the file "instructions.txt", the program displays the number of lights lit.
 *
 * @author Jean-Baptiste Robert
 */
public class LightDecoration {

    public static void main(String[] args) {

        if (args.length == 0) {

            int height = 1000;
            int width = 1000;

            // Path to instructions file
            String instructionsPath = "./src/main/resources/instructions.txt";

            // Instructions pattern
            Pattern PATTERN = Pattern.compile("(toggle|turn on|turn off) ([0-9]{1,3}),([0-9]{1,3}) through ([0-9]{1,3}),([0-9]{1,3})");

            // Grid of lights
            int[] grid = new int[height * width];

            // List of instructions
            List<String> instructions;

            System.out.println("-------------------------------------------------------------------");
            System.out.println("Lecture des instructions");

            // Read instructions from file
            try {
                instructions = Files.readAllLines(Paths.get(instructionsPath));

                if (instructions.size() > 0) {
                    System.out.println("Éxécution des instructions");

                    // For each instruction, toggle lights
                    for (String instruction : instructions) {
                        Matcher m = PATTERN.matcher(instruction);
                        m.matches();
                        int x1 = Integer.parseInt(m.group(2));
                        int y1 = Integer.parseInt(m.group(3));
                        int x2 = Integer.parseInt(m.group(4));
                        int y2 = Integer.parseInt(m.group(5));
                        for (int x = x1; x <= x2; x++) {
                            for (int y = y1; y <= y2; y++) {
                                int index = height * x + y;
                                if (m.group(1).equals("turn on")) grid[index] = 1;
                                if (m.group(1).equals("turn off")) grid[index] = 0;
                                if (m.group(1).equals("toggle")) grid[index] = (grid[index] == 0 ? 1 : 0);
                            }
                        }
                    }

                    // Calculate the number of lights lit
                    System.out.println("-------------------------------------------------------------------");
                    System.out.println("Résultat : " + Arrays.stream(grid).sum() + " lumières allumées");
                } else {
                    System.out.println("-------------------------------------------------------------------");
                    System.out.println("Aucune instruction trouvée : 0 lumières allumées");
                }

            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture des instructions : ");
                e.printStackTrace();
            }

            System.out.println("-------------------------------------------------------------------");
        } else {
            System.err.println("Auncun argument n'est accepté dans le cadre de l'exécution de l'application");
        }

    }
}
