package com.wiza.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class AppConfig extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "AppConfigDefaultName";

    //  @JsonProperty, which allows Jackson to both deserialize the properties from a YAML file but also to serialize it.
    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }
}
