package com.dining.philosophers.strategy.arbitrator;
import com.dining.philosophers.arbitrator.domain.WaiterWithLimitedPlates;
import com.dining.philosophers.strategy.Strategy;

public class LimitedPlatesArbitratorStrategy implements Strategy {

    @Override
    public void run() {
       Launcher.run(new WaiterWithLimitedPlates());
    }
}
