package com.github.devor110.aoc.solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4 {

    public void solve1() throws FileNotFoundException {
        File file = new File("C:\\Users\\András\\IdeaProjects\\AoC23\\src\\main\\resources\\input4.txt");
        Scanner sc = new Scanner(file);

        String line;
        int sum = 0;

        while (sc.hasNextLine()) {
            line = sc.nextLine();

            int colonPos = line.indexOf(':')+2;
            line = line.substring(colonPos);

            String[] sections = line.split(" \\| ");


            List<String> winningNums = Arrays.stream(sections[0].replaceAll(" {2}", " ").trim().split(" ")).toList();
            List<String> ownNums = Arrays.stream(sections[1].replaceAll(" {2}", " ").trim().split(" ")).toList();


            sum += eval(winningNums, ownNums);

            System.out.println(winningNums);
            System.out.println(ownNums);
        }

        System.out.println(sum);
    }

    public void solve2() throws FileNotFoundException {
        File file = new File("C:\\Users\\András\\IdeaProjects\\AoC23\\src\\main\\resources\\input4.txt");
        Scanner sc = new Scanner(file);

        String line;
        int sum = 0;
        int[] copies = new int[218];
        Arrays.fill(copies, 1);

        int cardId = 0;
        while (sc.hasNextLine()) {
            sum += copies[cardId];
            line = sc.nextLine();
            int colonPos = line.indexOf(':') + 2;
            line = line.substring(colonPos);

            String[] sections = line.split(" \\| ");


            List<String> winningNums = Arrays.stream(sections[0].replaceAll(" {2}", " ").trim().split(" ")).toList();
            List<String> ownNums = Arrays.stream(sections[1].replaceAll(" {2}", " ").trim().split(" ")).toList();

            int eval = eval2(winningNums, ownNums);

                for (int j = cardId+1; j < cardId + eval+1; ++j) {
                    copies[j] += copies[cardId];

                }


            ++cardId;
        }

        System.out.println(sum);
    }

    private int eval(List<String> win, List<String> own) {
        int points = 0;

        for (String o : own) {
            for (String w : win) {
                if (o.equals(w)) {
                    ++points;
                }
            }
        }

        return points == 0 ? 0 : (int) Math.pow(2, points-1);
    }

    private int eval2(List<String> win, List<String> own) {
        int points = 0;

        for (String o : own) {
            for (String w : win) {
                if (o.equals(w)) {
                    ++points;
                }
            }
        }

        return points;
    }
}
