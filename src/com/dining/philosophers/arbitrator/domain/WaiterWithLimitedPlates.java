package com.dining.philosophers.arbitrator.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class WaiterWithLimitedPlates implements Waiter {

    private final ReentrantLock lock;
    private final List<ReentrantLock> forkLocks;
    private final Map<Philosopher, List<Integer>> forkLocksMap;

    private final List<ReentrantLock> plateLocks;
    private final Map<Philosopher, Integer> plateLocksMap;
    private int plateIndex;

    public WaiterWithLimitedPlates() {
        lock = new ReentrantLock(true);

        forkLocks = new ArrayList<>();
        forkLocks.add(new ReentrantLock());

        forkLocksMap = new HashMap<>();

        plateLocks = new ArrayList<>();
        plateLocksMap = new HashMap<>();
        plateIndex = 0;
    }

    @Override
    public void register(Philosopher philosopher) {
        lock.lock();

        if (!forkLocks.isEmpty())
            plateLocks.add(new ReentrantLock());

        // Philosopher will be associated with fork forkIndex and forkIndex + 1
        int forkIndex = forkLocksMap.size();

        forkLocksMap.put(philosopher, new ArrayList<>());
        forkLocks.add(new ReentrantLock());

        IntStream.range(forkIndex, forkIndex + 2)
                .forEach(lockIndex -> forkLocksMap.get(philosopher).add(lockIndex));

        lock.unlock();
    }

    @Override
    public void performEatRequest(Philosopher philosopher) {
        philosopher.log("Asking waiter for permission to eat");
        lock.lock();

        philosopher.log("Entering waiter supervision. Trying to pickup forks");
        forkLocksMap.get(philosopher)
                .forEach(lockIndex -> {
                    philosopher.log("Picking up fork " + lockIndex);
                    forkLocks.get(lockIndex).lock();
                    philosopher.log("Picked up fork " + lockIndex);
                });

        philosopher.log("Picked up my forks");

        plateIndex = (plateIndex + 1) % plateLocks.size();
        philosopher.log("Attempting to use plate #" + plateIndex);
        plateLocksMap.put(philosopher, plateIndex);
        plateLocks.get(plateIndex).lock();

        philosopher.log("Using plate #" + plateIndex);

        lock.unlock();
        philosopher.log("Left waiter supervision");
    }

    @Override
    public void dropAccessories(Philosopher philosopher) {
        forkLocksMap.get(philosopher)
                .forEach(lockIndex -> {
                    philosopher.log("Dropping fork " + lockIndex);
                    forkLocks.get(lockIndex).unlock();
                    philosopher.log("Dropped fork " + lockIndex);
                });
        philosopher.log("I dropped my forks");

        int currentPlateIndex = plateLocksMap.get(philosopher);
        philosopher.log("Dropping plate #" + currentPlateIndex);
        plateLocks.get(currentPlateIndex).unlock();
        philosopher.log("Dropped my plate");
    }
}
