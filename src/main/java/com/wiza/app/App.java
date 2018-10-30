package com.wiza.app;

import com.wiza.config.AppConfig;
import com.wiza.controller.IndexController;
import com.wiza.controller.UserController;
import com.wiza.dao.UserDAO;
import com.wiza.healthcheck.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import java.util.Arrays;

    public class App extends Application<AppConfig> {
    public static void main(String[] args) throws Exception {
        new App().run((String[]) Arrays.asList("server", "config.yml").toArray());

    }

    @Override
    public String getName() {
        return "the-app";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        // bundles, configuration source providers, command etc
        // bundles: bootstrap.addBundle(new AssetsBundle("/assets/", "/")); - reusable group of functionality

        /**
         *     bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "css"));
         *     bootstrap.addBundle(new AssetsBundle("/assets/js", "/js", null, "js"));
         *     bootstrap.addBundle(new AssetsBundle("/assets/fonts", "/fonts", null, "fonts"));
         */
        /**
         * command
         * command: bootstrap.addCommand(new MyCommand()); - dropwizard runs based on the arguments provided on the commmand line
         *
         */
    }

    @Override
    public void run(AppConfig configuration,
                    Environment environment) {
        // nothing to do yet
        IndexController controller = new IndexController(
                configuration.getTemplate(),
                configuration.getDefaultName(),
                configuration.getPortConfig().getServerPort());

        TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

        // create a new DBIFactory
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDatabase(), "mysql"); // DataSourceFactory

        // Create dao
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(controller);
        environment.jersey().register(new UserController(userDAO));

        /**         *public class TruncateDatabaseTask extends Task {
         *     private final Database database;
         *
         *     public TruncateDatabaseTask(Database database) {
         *         super('truncate');
         *         this.database = database;
         *     }
         *
         *       @Override
         *     public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
         *         this.database.truncate();
         *     }
         * }
         *
         * environment.admin().addTask(new TruncateDatabaseTask(database));
         *
         * $ curl -X POST http://dw.example.com:8081/tasks/gc
         * Running GC...
         * Done!
         */
    }
}