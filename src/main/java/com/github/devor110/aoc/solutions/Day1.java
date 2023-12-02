package com.github.devor110.aoc.solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class Day1 {

    public void yep() throws FileNotFoundException {
        File file = new File("D:\\coding\\AoC23\\d1mvn\\src\\main\\java\\com\\github\\devor110\\aoc\\input1.txt");
        Scanner sc = new Scanner(file);
        int sum = 0;
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();

            char first = 0;
            char last = 0;
            StringBuilder seq = new StringBuilder();
            boolean isFirst = true;
            for (char c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    seq = new StringBuilder();
                    if (isFirst) {
                        first = c;
                        last = c;
                        isFirst = false;
                    } else {
                        last = c;
                    }
                } else {
                    Integer val = isNum(seq.append(c).toString());
                    if (val == null) {
                        seq.deleteCharAt(0);
                    } else if (val != -1) {
                        char asd = val.toString().charAt(0);
                        if (isFirst) {
                            first = asd;
                            last = asd;
                            isFirst = false;
                        } else {
                            last = asd;
                        }

                        char fi = seq.charAt(seq.length() - 1);
                        seq = new StringBuilder().append(fi);
                    }
                }
            }

            sum += (10 * Character.getNumericValue(first)) + Character.getNumericValue(last);

        }

        System.out.println(sum);
    }

    private static Integer isNum(String seq) {
        //null if break, -1 if potential, num else
        for (String key : what.keySet()) {
            if (seq.equals(key)) {
                return what.get(key);
            }
            if (key.startsWith(seq)) {
                return -1;
            }
        }

        return null;
    }

    private static final Map<String, Integer> what = Map.of("one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9);

}
