package com.wiza.app;

import com.codahale.metrics.MetricRegistry;
import com.wiza.config.AppConfig;
import com.wiza.controller.*;
import com.wiza.dao.PeopleDAO;
import com.wiza.dao.UserDAO;
import com.wiza.exceptionmapper.CommonExceptionMapper;
import com.wiza.exceptionmapper.IllegalArgumentExceptionMapper;
import com.wiza.healthcheck.TemplateHealthCheck;
import com.wiza.jerseyfilter.dynamicfilter.DateRequiredFeature;
import com.wiza.model.User;
import com.wiza.representation.PeopleTable;
import com.wiza.security.ExampleAuthenticator;
import com.wiza.security.ExampleAuthorizer;
import com.wiza.view.data.Chats;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryHealthCheck;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;

import java.util.Arrays;
import java.util.Map;

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

        // support of environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );


        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(new MigrationsBundle<AppConfig>() {
            @Override
            public DataSourceFactory getDataSourceFactory(AppConfig configuration) {
                return configuration.getDatabase(); // data source factory
            }
        });
        bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
        bootstrap.addBundle(new AssetsBundle("/assets/css", "/assets/css", null, "css"));

        bootstrap.addBundle(new ViewBundle<AppConfig>() {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(AppConfig config) {
                return config.getViewRendererConfiguration();
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
        final ExceptionController exceptionController = new ExceptionController();
        final PersonController personController = new PersonController();


        // create health checks
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

        // set up environment
        environment.healthChecks().register("template", healthCheck);

        // exceptions
        environment.jersey().register(new IllegalArgumentExceptionMapper(environment.metrics()));
        environment.jersey().register(new CommonExceptionMapper(environment.metrics()));

        environment.jersey().register(DateRequiredFeature.class);

        //        security
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new ExampleAuthenticator())
                        .setAuthorizer(new ExampleAuthorizer())
                        .setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
//If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));


        // controllers
        environment.jersey().register(controller);
        environment.jersey().register(userController);
        environment.jersey().register(peopleController);
        environment.jersey().register(exceptionController);
        environment.jersey().register(personController);
        environment.jersey().register(new RequiredDateController());
        environment.jersey().register(new ValidatorController());
        environment.jersey().register(new FreemakerViewController());
        environment.jersey().register(new ChatController(new MetricRegistry(), new Chats()));

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