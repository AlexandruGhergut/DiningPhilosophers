package com.dining.philosophers;

import com.dining.philosophers.strategy.arbitrator.LimitedPlatesArbitratorStrategy;
import com.dining.philosophers.strategy.Strategy;

public class Main {
    public static void main(String[] args) {
        Strategy currentStrategy = new LimitedPlatesArbitratorStrategy();
        currentStrategy.run();
    }
}
