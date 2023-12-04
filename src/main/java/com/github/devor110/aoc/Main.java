package com.github.devor110.aoc;

import com.github.devor110.aoc.solutions.Day2;
import com.github.devor110.aoc.solutions.Day3;
import com.github.devor110.aoc.solutions.Day4;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        long start = System.nanoTime();

        Day4 day4 = new Day4();
        day4.solve2();

        System.out.println((System.nanoTime() - start) / 1_000_000_000f);
    }


}
