package com.wiza.app;

import com.wiza.config.AppConfig;
import com.wiza.controller.IndexController;
import com.wiza.controller.PeopleController;
import com.wiza.controller.UserController;
import com.wiza.dao.PeopleDAO;
import com.wiza.dao.UserDAO;
import com.wiza.healthcheck.TemplateHealthCheck;
import com.wiza.representation.PeopleTable;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryHealthCheck;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import java.util.Arrays;

public class App extends Application<AppConfig> {
    public static void main(String[] args) throws Exception {
        new App().run(args);

    }

    @Override
    public String getName() {
        return "the-app";
    }

    private final HibernateBundle<AppConfig> hibernate = new HibernateBundle<AppConfig>(PeopleTable.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(AppConfig configuration) {
            return configuration.getDatabase();
        }
    };


    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(new MigrationsBundle<AppConfig>() {
            @Override
            public DataSourceFactory getDataSourceFactory(AppConfig configuration) {
                return configuration.getDatabase(); // data source factory
            }
        });
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
                    Environment environment)
    {
        // create a new DBIFactory
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDatabase(), "mysql"); // DataSourceFactory

        // create daos
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);
        final PeopleDAO peopleDAO = new PeopleDAO(hibernate.getSessionFactory());

        // create controllers
        final IndexController controller = new IndexController(
                configuration.getTemplate(),
                configuration.getDefaultName(),
                configuration.getPortConfig().getServerPort());
        final UserController userController = new UserController(userDAO); // jdbi
        final PeopleController peopleController = new PeopleController(peopleDAO); // hibernate


        // create health checks
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

        // set up environment
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(controller);
        environment.jersey().register(userController);
        environment.jersey().register(peopleController);
    }
}
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