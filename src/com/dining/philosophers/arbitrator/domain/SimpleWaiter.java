package com.dining.philosophers.arbitrator.domain;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class SimpleWaiter implements Waiter {

    private final ReentrantLock lock;
    private final List<ReentrantLock> forkLocks;
    private final Map<Philosopher, List<Integer>> forkLocksMap;

    public SimpleWaiter() {
        lock = new ReentrantLock(true);

        forkLocks = new ArrayList<>();
        forkLocks.add(new ReentrantLock());

        forkLocksMap = new HashMap<>();
    }

    @Override
    public void register(Philosopher philosopher) {
        lock.lock();

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
    }
}
