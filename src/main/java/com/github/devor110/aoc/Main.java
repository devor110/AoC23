package com.github.devor110.aoc;

import com.github.devor110.aoc.solutions.Day2;
import com.github.devor110.aoc.solutions.Day3;
import com.github.devor110.aoc.solutions.Day4;
import com.github.devor110.aoc.solutions.Day5;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        long start = System.nanoTime();

        Day5 day = new Day5();
        day.solve2();

        //Get the jvm heap size.
        //long heapSize = Runtime.getRuntime().maxMemory();

        //Print the jvm heap size.
        //System.out.println("Heap Size = " + heapSize);

        System.out.println((System.nanoTime() - start) / 1_000_000_000f);
    }


}
