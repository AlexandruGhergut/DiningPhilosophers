package com.dining.philosophers.arbitrator.util;

import com.dining.philosophers.arbitrator.domain.Philosopher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PhilosophersFileReader {
    public List<Philosopher> extractPhilosophers(String filePath) {
        List<Philosopher> philosophers = new ArrayList<>();

        try (Scanner reader = new Scanner(new File(filePath))) {
            while (reader.hasNextLine()) {
                Philosopher philosopher = new Philosopher()
                        .setId(reader.next())
                        .setThinkRangeMillis(Pair.of(reader.nextLong(), reader.nextLong()))
                        .setEatRangeMillis(Pair.of(reader.nextLong(), reader.nextLong()));
                philosophers.add(philosopher);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return philosophers;
    }
}
