package com.dining.philosophers.strategy.arbitrator;

import com.dining.philosophers.arbitrator.domain.Philosopher;
import com.dining.philosophers.arbitrator.domain.Waiter;
import com.dining.philosophers.arbitrator.util.PhilosophersFileReader;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {
    private final static String INPUT_FILE_PATH = "input.in";

    public static void run(Waiter waiter) {
        PhilosophersFileReader philosophersFileReader = new PhilosophersFileReader();

        List<Philosopher> philosophers = philosophersFileReader.extractPhilosophers(INPUT_FILE_PATH);
        ExecutorService executorService = Executors.newFixedThreadPool(philosophers.size());

        philosophers.stream()
                .map(philosopher -> philosopher.setSimpleWaiter(waiter))
                .forEach(executorService::execute);
    }
}
