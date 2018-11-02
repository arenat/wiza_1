package com.wiza;

import com.wiza.app.App;
import com.wiza.config.AppConfig;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class IntegrationTest {

    @ClassRule
    public static final DropwizardAppRule<AppConfig> RULE =
            new DropwizardAppRule<AppConfig>(App.class, ResourceHelpers.resourceFilePath("test.yml"));


    @Test
    public void firstIntegrationTest() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");

        String response = client.target(
                String.format("http://localhost:%d/api/validator/max?m=100", RULE.getLocalPort()))
                .request()
                .get(String.class);

        assertThat(response).isEqualTo("100");
    }
}
