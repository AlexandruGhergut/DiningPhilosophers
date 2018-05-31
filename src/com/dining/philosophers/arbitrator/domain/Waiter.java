package com.dining.philosophers.arbitrator.domain;

public interface Waiter {
    void register(Philosopher philosopher);
    void performEatRequest(Philosopher philosopher);
    void dropAccessories(Philosopher philosopher);
}
