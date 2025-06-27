package com.paulphyo;

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
    public void run(final CoinChangeConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
