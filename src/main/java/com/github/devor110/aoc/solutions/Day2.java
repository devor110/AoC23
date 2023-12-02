package com.github.devor110.aoc.solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day2 {

    private final Map<String, Integer> cubes = Map.of("red", 12, "green", 13, "blue", 14);

    public void solve1() throws FileNotFoundException {
        File file = new File("D:\\coding\\AoC23\\d1mvn\\src\\main\\resources\\input2.txt");
        Scanner sc = new Scanner(file);

        String line;
        int sum = 0;
        int gameId = 1;
        while (sc.hasNextLine()) {
            line = sc.nextLine();

            line = line.substring(line.indexOf(':')+1, line.length());
            boolean poss = true;

            String[] pulls = line.split(";");


            for (int i=0; i<pulls.length && poss; ++i) {
                poss = possible(pulls[i]);
            }

            if (poss) {
                sum += gameId;
            }

            ++gameId;
        }

        System.out.println(sum);

    }

    public void solve2() throws FileNotFoundException {
        File file = new File("D:\\coding\\AoC23\\d1mvn\\src\\main\\resources\\input2.txt");
        Scanner sc = new Scanner(file);

        String line;
        int sum = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();

            line = line.substring(line.indexOf(':')+1);
            String[] pulls = line.split(";");

            int[] min = new int[3];

            for (String pull : pulls) {
                getMin(pull, min);
            }


            sum += (Arrays.stream(min).reduce(1, (a,b) -> a*b ));

        }

        System.out.println(sum);

    }

    private final List<String> colors = Arrays.asList("red", "green", "blue");

    private void getMin(String pull, int[] prevMin) {
        String[] split = pull.split(",");

        for (String value : split) {
            String s = value.trim();
            int splitPoint = s.indexOf(' ');
            int count = Integer.parseInt(s.substring(0, splitPoint));
            String color = s.substring(splitPoint + 1);

            int colorIndex = colors.indexOf(color);

            prevMin[colorIndex] = Math.max(prevMin[colorIndex], count);
        }

    }

    private boolean possible(String pull) {
        String[] split = pull.split(",");
        boolean possible = true;

        for (int i=0;i<split.length && possible; ++i) {
            String s = split[i].trim();
            int splitPoint = s.indexOf(' ');
            int count = Integer.parseInt(s.substring(0, splitPoint));
            String color = s.substring(splitPoint+1);

            possible = cubes.get(color) >= count;
        }

        return possible;
    }

}
