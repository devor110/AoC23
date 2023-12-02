package com.github.devor110.aoc;

import com.github.devor110.aoc.solutions.Day2;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        long start = System.nanoTime();

        Day2 day2 = new Day2();
        day2.solve1();

        System.out.println((System.nanoTime() - start) / 1_000_000_000f);
    }


}
