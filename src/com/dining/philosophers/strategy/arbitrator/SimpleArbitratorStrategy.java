package com.dining.philosophers.strategy.arbitrator;

import com.dining.philosophers.arbitrator.domain.SimpleWaiter;
import com.dining.philosophers.strategy.Strategy;

public class SimpleArbitratorStrategy implements Strategy {

    @Override
    public void run() {
        Launcher.run(new SimpleWaiter());
    }
}
