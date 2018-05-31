package com.dining.philosophers.arbitrator.domain;

import com.dining.philosophers.arbitrator.util.Pair;

import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable {

    private String id;
    private Waiter waiter;
    private Pair<Long, Long> thinkRangeMillis;
    private Pair<Long, Long> eatRangeMillis;

    public Philosopher() {

    }

    public String getId() {
        return id;
    }

    public Philosopher setId(String id) {
        this.id = id;
        return this;
    }

    public Philosopher setSimpleWaiter(Waiter waiter) {
        this.waiter = waiter;
        return this;
    }

    public Philosopher setThinkRangeMillis(Pair<Long, Long> thinkRangeMillis) {
        this.thinkRangeMillis = thinkRangeMillis;
        return this;
    }

    public Philosopher setEatRangeMillis(Pair<Long, Long> eatRangeMillis) {
        this.eatRangeMillis = eatRangeMillis;
        return this;
    }

    @Override
    public void run() {
        waiter.register(this);

        while (Thread.currentThread().isAlive()) {
            try {
                think();
                eat();
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                e.printStackTrace();
            }
        }
    }

    private void think() throws InterruptedException {
        long thinkTimeMillis = ThreadLocalRandom.current()
                .nextLong(thinkRangeMillis.getT1(), thinkRangeMillis.getT2() + 1);

        log("Thinking for " + thinkTimeMillis + " ms");
        Thread.sleep(1000);
        log("Stopped thinking");
    }

    private void eat() throws InterruptedException {
        long eatTimeMillis = ThreadLocalRandom.current()
                .nextLong(eatRangeMillis.getT1(), eatRangeMillis.getT2() + 1);

        waiter.performEatRequest(this);
        log("Eating for " + eatTimeMillis + " ms");
        Thread.sleep(1000);

        waiter.dropAccessories(this);
        log("Stopped eating");
    }

    void log(String message) {
        System.out.println(String.format("[%s]-[%s] %s", Thread.currentThread().getName(), id, message));
    }
}
