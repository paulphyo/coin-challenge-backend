package com.paulphyo;

import com.paulphyo.health.BasicHealthCheck;
import com.paulphyo.resources.CoinResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.util.EnumSet;

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
        environment.healthChecks().register("basic", new BasicHealthCheck());
        configureCors(environment);
    }

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }
}
