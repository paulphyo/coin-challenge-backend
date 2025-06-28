package com.paulphyo;

import com.paulphyo.resources.CoinResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class CoinChangeApplication extends Application<CoinChangeConfiguration> {

    public static void main(final String[] args) throws Exception {
        new CoinChangeApplication().run(args);
    }

    @Override
    public String getName() {
        return "CoinChange";
    }

    @Override
    public void initialize(final Bootstrap<CoinChangeConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(CoinChangeConfiguration configuration, Environment environment) {
        environment.jersey().register(new CoinResource());
    }
}
