package com.wiza.app;

import com.wiza.config.AppConfig;
import com.wiza.controller.IndexController;
import com.wiza.healthcheck.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<AppConfig> {
    public static void main(String[] args) throws Exception {
        new App().run(args);

    }

    @Override
    public String getName() {
        return "the-app";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        // bundles, configuration source providers, etc
    }

    @Override
    public void run(AppConfig configuration,
                    Environment environment) {
        // nothing to do yet
        IndexController controller = new IndexController(
                configuration.getTemplate(),
                configuration.getDefaultName());

        TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(controller);
    }
}