package com.wiza;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.wiza.config.PortConfig;
import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.jackson.DiscoverableSubtypeResolver;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jersey.validation.Validators;
import io.dropwizard.testing.ResourceHelpers;
import org.junit.Test;

import javax.sound.sampled.Port;
import javax.validation.Validator;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class YamlConfigTest {
    private final ObjectMapper objectMapper = Jackson.newObjectMapper();
    private final Validator validator = Validators.newValidator();
    private final YamlConfigurationFactory<PortConfig> factory =
            new YamlConfigurationFactory<>(PortConfig.class, validator, objectMapper, "ports");

    @Test
    public void testPortConfig() throws Exception {
        final File yml = new File(ResourceHelpers.resourceFilePath("yaml/portconfig.yml"));
        PortConfig pc = factory.build(yml);
        assertThat(pc).isInstanceOf(PortConfig.class);
        assertThat(pc.getServerPort()).isEqualTo(8000);
    }
}
