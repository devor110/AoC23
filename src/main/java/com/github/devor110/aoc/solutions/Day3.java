package com.github.devor110.aoc.solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day3 {

    public void solve1() throws FileNotFoundException {
        File file = new File("C:\\Users\\András\\IdeaProjects\\AoC23\\src\\main\\resources\\input3.txt");
        Scanner sc = new Scanner(file);

        int sum = 0;

        String prevLine = null;
        String currentLine = sc.nextLine();
        String nextLine = sc.nextLine();

        int lineLength = currentLine.length();

        while (sc.hasNextLine()) {
            sum += checkLine(lineLength, prevLine, currentLine, nextLine);

            prevLine = currentLine;
            currentLine = nextLine;
            nextLine = sc.nextLine();
        }

        sum += checkLine(lineLength, prevLine, currentLine, nextLine);

        prevLine = currentLine;
        currentLine = nextLine;


        sum += checkLine(lineLength, prevLine, currentLine, nextLine);


        //TODO you're mom :)))


        System.out.println(sum);

    }

    public void solve2() throws FileNotFoundException {
        File file = new File("C:\\Users\\András\\IdeaProjects\\AoC23\\src\\main\\resources\\input3.txt");
        Scanner sc = new Scanner(file);

        int sum = 0;

        String prevLine = null;
        String currentLine = sc.nextLine();
        String nextLine = sc.nextLine();

        int lineLength = currentLine.length();

        while (sc.hasNextLine()) {
            sum += findGear(prevLine, currentLine, nextLine);

            prevLine = currentLine;
            currentLine = nextLine;
            nextLine = sc.nextLine();
        }

        sum += findGear(prevLine, currentLine, nextLine);

        prevLine = currentLine;
        currentLine = nextLine;


        sum += findGear(prevLine, currentLine, nextLine);


        System.out.println(sum);

    }

    private boolean isPart(String num, int startPos, int lineLength, String prevLine, String currentLine, String nextLine) {
        return (prevLine != null && containsSpecChar(prevLine.substring(Math.max(0, startPos - 1), Math.min(lineLength, startPos + num.length() + 1))))
                || (nextLine != null && containsSpecChar(nextLine.substring(Math.max(0, startPos - 1), Math.min(lineLength, startPos + num.length() + 1))))
                || (startPos > 0 && isSpecChar(currentLine.charAt(startPos - 1)))
                || (startPos + num.length() < lineLength && isSpecChar(currentLine.charAt(startPos + num.length())));

    }

    private boolean containsSpecChar(String line) {
        for (char c : line.toCharArray()) {
            boolean asd = isSpecChar(c);
            if (asd) return true;
        }
        return false;
    }

    private boolean isSpecChar(char c) {
        return !Character.isDigit(c) && '.' != c;
    }

    private int checkLine(int lineLength, String prevLine, String currentLine, String nextLine) {
        int sum = 0;

        StringBuilder numBuffer = new StringBuilder();
        for (int i = 0; i < lineLength; ++i) {
            if (Character.isDigit(currentLine.charAt(i))) {
                numBuffer.append(currentLine.charAt(i));
                if (i == lineLength - 1) {
                    if (isPart(numBuffer.toString(), i - numBuffer.length() + 1, lineLength, prevLine, currentLine, nextLine)) {
                        sum += Integer.parseInt(numBuffer.toString());
                        System.out.println(numBuffer);
                    }
                }
            } else if (numBuffer.length() > 0) {
                if (isPart(numBuffer.toString(), i - numBuffer.length(), lineLength, prevLine, currentLine, nextLine)) {
                    sum += Integer.parseInt(numBuffer.toString());
                    System.out.println(numBuffer);
                }

                numBuffer = new StringBuilder();
            }
        }

        return sum;
    }

    private int findGear(String prevLine, String currentLine, String nextLine) {
        int sum = 0;

        int length = currentLine.length();
        for (int i = 0; i < length; ++i) {
            if ('*' == currentLine.charAt(i)) {
                sum += checkGear(length, i, prevLine, currentLine, nextLine);
            }
        }

        return sum;
    }

    private int checkGear(int lineLength, int gearPos, String prevLine, String currentLine, String nextLine) {
        ArrayList<Integer> verticalPositions = new ArrayList<>();
        List<Integer> neighbours = new ArrayList<>();



        if (gearPos > 0) {
            verticalPositions.add(gearPos - 1);
            if (Character.isDigit(currentLine.charAt(gearPos-1))) {
                neighbours.add(Integer.parseInt(checkToLeft(currentLine, gearPos - 1, Arrays.asList(gearPos-1), gearPos).toString() + currentLine.charAt(gearPos-1)));
            }
        }

        verticalPositions.add(gearPos);

        if (gearPos < lineLength - 1) {
            verticalPositions.add(gearPos + 1);
            if (Character.isDigit(currentLine.charAt(gearPos + 1))) {
                neighbours.add(Integer.parseInt(currentLine.charAt(gearPos + 1) + checkToRight(currentLine, gearPos + 1, Arrays.asList(gearPos-1), gearPos).toString()));
            }
        }

        neighbours.addAll(findVertNeighbours(verticalPositions, prevLine, gearPos));
        neighbours.addAll(findVertNeighbours(verticalPositions, nextLine, gearPos));

        if (neighbours.size() == 2) {
            neighbours.forEach(System.out::println);
            return neighbours.get(0) * neighbours.get(1);
        }

        return 0;
    }

    private StringBuilder checkToLeft(String line, int startPos, List<Integer> unchecked, int gearPos) {
        StringBuilder numBuffer = new StringBuilder();

        for (int i = startPos-1; i >= 0; --i) {
            char c = line.charAt(i);
            if (Character.isDigit(c) && (unchecked.contains(i) ||  i < gearPos - 1)) {
                numBuffer.insert(0, c);
                unchecked.remove((Integer) i);
            } else {
                break;
            }
        }

        return numBuffer;
    }

    private StringBuilder checkToRight(String line, int startPos, List<Integer> unchecked, int gearPos) {
        StringBuilder numBuffer = new StringBuilder();

        for (int i = startPos+1; i < line.length(); ++i) {
            char c = line.charAt(i);
            if (Character.isDigit(c) && (unchecked.contains(i) ||  i > gearPos)) {
                numBuffer.append(c);
                unchecked.remove((Integer) i);
            } else {
                break;
            }
        }

        return numBuffer;
    }

    private List<Integer> findVertNeighbours(List<Integer> verticalPositions, String line, int gearPos) {
        List<Integer> uncheckedVertPos = new ArrayList<>(verticalPositions);
        List<Integer> neighbours = new ArrayList<>();

        for (Integer i : verticalPositions) {
            char c = line.charAt(i);
            if (Character.isDigit(c) && uncheckedVertPos.contains(i)) {
                StringBuilder numBuffer = new StringBuilder();



                uncheckedVertPos.remove(i);

                numBuffer.append(checkToLeft(line, i, uncheckedVertPos, gearPos));


                numBuffer.append(c);

                numBuffer.append(checkToRight(line, i, uncheckedVertPos, gearPos));

                neighbours.add(Integer.parseInt(numBuffer.toString()));

            }
        }
        return neighbours;
    }

}
