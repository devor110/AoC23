package com.github.devor110.aoc.solutions;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class Day5 {

    private Map<Pair<String, String>, List<Triplet<BigInteger, BigInteger, BigInteger>>> mapping = new HashMap<>();

    private List<Pair<String, String>> steps = Arrays.asList(
            new Pair<>("soil", "seed"),
            new Pair<>("fertilizer", "soil"),
            new Pair<>("water", "fertilizer"),
            new Pair<>("light", "water"),
            new Pair<>("temperature", "light"),
            new Pair<>("humidity", "temperature"),
            new Pair<>("location", "humidity")
    );

    public void solve1() throws FileNotFoundException {
        File file = new File("C:\\Users\\András\\IdeaProjects\\AoC23\\src\\main\\resources\\input5.txt");
        Scanner sc = new Scanner(file);

        List<BigInteger> seeds = Arrays.stream(sc.nextLine().split(" ")).skip(1).map(BigInteger::new).toList();
        sc.nextLine();
        while (sc.hasNextLine()) {
            parseNextMap(sc);
        }
        sc.close();

        BigInteger closest = null;

        for (BigInteger seed : seeds) {
            BigInteger dist = eval(seed);
            if (closest == null || dist.compareTo(closest) < 0) {
                closest = dist;
            }
        }

        System.out.println(closest);
        System.out.println("lol");
    }

    public void solve2() throws FileNotFoundException {
        File file = new File("C:\\Users\\András\\IdeaProjects\\AoC23\\src\\main\\resources\\input5.txt");
        Scanner sc = new Scanner(file);
        List<BigInteger> seeds = new ArrayList<>();
        List<BigInteger> seedRanges = new ArrayList<>();

        List<BigInteger> firstLine = Arrays.stream(sc.nextLine().split(" ")).skip(1).map(BigInteger::new).toList();
        for (int i = 0; i < firstLine.size(); ++i) {
            if (i % 2 == 0) {
                seeds.add(firstLine.get(i));
            } else {
                seedRanges.add(firstLine.get(i));
            }
        }



        sc.nextLine();
        while (sc.hasNextLine()) {
            parseNextMap(sc);
        }
        sc.close();



        //new BigInteger("126471773"
        final BigInteger[] closest = {new BigInteger("126471773")};
        //closest[0] == null ||


        seeds.parallelStream()
                .forEach(seed -> {
                    BigInteger range = seedRanges.get(seeds.indexOf(seed));

                    Stream.iterate(seed, (a) -> a.compareTo(seed.add(range)) < 0, (a) -> a.add(BigInteger.ONE))
                            .parallel()
                            .forEach(seedValue -> {
                                closest[0] = eval(seedValue).min(closest[0]);

                            });

                });

           /* int index = seeds.indexOf(seed);
            try {
                CompletableFuture.runAsync( () -> {
                            for (BigInteger seedValue = seed; seedValue.compareTo(seed.add(seedRanges.get(index))) < 0; seedValue = seedValue.add(BigInteger.ONE)) {
                                BigInteger finalSeedValue = seedValue;
                                CompletableFuture<BigInteger> dist = new CompletableFuture<>();

                                CompletableFuture.runAsync(() -> {
                                    dist.complete(eval(finalSeedValue));
                                }).thenRun(() -> {
                                    try {
                                        if (closest[0] == null || dist.get().compareTo(closest[0]) < 0) {
                                            closest[0] = dist.get();
                                        }
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    }
                                });

                            }

                }).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }*/

            /*fuckMe(() -> {
                int index = seeds.indexOf(seed);
                for (BigInteger seedValue = seed; seedValue.compareTo(seed.add(seedRanges.get(index))) < 0; seedValue = seedValue.add(BigInteger.ONE)) {
                    BigInteger finalSeedValue = seedValue;
                    fuckMe(() -> {
                        BigInteger dist = eval(finalSeedValue);

                        if (closest[0] == null || dist.compareTo(closest[0]) < 0) {
                            closest[0] = dist;
                        }
                    });
                }
                //System.out.println(closest[0]);
                //System.out.println("lol of " + Thread.currentThread().getName());

            });*/

      /*  seeds.parallelStream()
                .forEach(a -> {
                    int index = seeds.indexOf(a);


                    for (BigInteger seedValue = a; seedValue.compareTo(a.add(seedRanges.get(index))) < 0; seedValue = seedValue.add(BigInteger.ONE)) {
                        BigInteger finalSeedValue = seedValue;
                        fuckMe(() -> {
                            BigInteger dist = eval(finalSeedValue);

                            if (dist.compareTo(closest[0]) < 0) {
                                closest[0] = dist;
                            }
                        });
                    }

                   /* produceRange(a, seedRanges.get(index)).parallelStream()
                            .forEach(b -> {
                                BigInteger dist = eval(b);

                                if (dist.compareTo(closest[0]) < 0) {
                                    closest[0] = dist;
                                }
                            });

                });*/

        System.out.println(closest[0]);
        System.out.println("lol");

    }

    private void parseNextMap(Scanner sc) {
        List<String> mappingInput = new ArrayList<>();
        String line = null;

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if ("".equals(line)) {
                break;
            }
            mappingInput.add(line);
        }

        String[] asArray = new String[mappingInput.size()];

        addToMap(mappingInput.toArray(asArray));
    }

    private void addToMap(String[] input) {
        Pair<String, String> asd = extractPath(input[0]);

        mapping.put(asd, parseValues(Arrays.copyOfRange(input, 1, input.length)));
    }

    private Pair<String, String> extractPath(String input) {
        if (input == null) {
            throw new IllegalStateException("Fuck you");
        }

        String[] split = input.split("-");

        return new Pair<>(split[2].substring(0, split[2].indexOf(" ")), split[0].trim());
    }

    private List<Triplet<BigInteger, BigInteger, BigInteger>> parseValues(String[] input) {
        List<Triplet<BigInteger, BigInteger, BigInteger>> values = new ArrayList<>();
        for (String s : input) {
            String[] split = s.split(" ");
            values.add(new Triplet<>(new BigInteger(split[0]), new BigInteger(split[1]), new BigInteger(split[2])));
        }

        return values;
    }

    private BigInteger eval(BigInteger seed) {
        BigInteger num = seed;

        for (Pair<String, String> step : steps) {
            num = transform(num, step);
        }

        return num;
    }

    private BigInteger transform(BigInteger input, Pair<String, String> step) {
        for (Triplet<BigInteger, BigInteger, BigInteger> seedTransform : mapping.get(step)) {
            BigInteger source = seedTransform.getValue1();
            BigInteger range = seedTransform.getValue2();
            BigInteger dest = seedTransform.getValue0();

            if (input.compareTo(source) >= 0 && input.compareTo(source.add(range)) < 0) {
                return dest.add(input.subtract(source));
            }
        }

        return input;
    }

    private List<BigInteger> produceRange(BigInteger start, BigInteger range) {
        List<BigInteger> result = new ArrayList<>();

        for (BigInteger seedValue = start; seedValue.compareTo(start.add(range)) < 0; seedValue = seedValue.add(BigInteger.ONE)) {
            result.add(seedValue);
        }

        return result;
    }

    private void fuckMe(Runnable r) {

        ForkJoinPool.commonPool().execute(r);


    }

    private void bloodyHell(BigInteger start, BigInteger range) {


    }
}

