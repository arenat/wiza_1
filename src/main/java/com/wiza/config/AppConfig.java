package com.wiza.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class AppConfig extends Configuration {

    @Valid
    @NotNull
    private PortConfig portConfig = new PortConfig();

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty
    private Map<String, Map<String, String>> viewRendererConfiguration;

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "AppConfigDefaultName";

    @JsonProperty("ports")
    public PortConfig getPortConfig() {
        return portConfig;
    }

    @JsonProperty("ports")
    public void setPortConfig(PortConfig portConfig) {
        this.portConfig = portConfig;
    }

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

    @JsonProperty
    public DataSourceFactory getDatabase() {
        return database;
    }

    @JsonProperty
    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }

    @JsonProperty("views")
    public Map<String, Map<String, String>> getViewRendererConfiguration() {
        return viewRendererConfiguration;
    }

    @JsonProperty("views")
    public void setViewRendererConfiguration(Map<String, Map<String, String>> viewRendererConfiguration) {
        this.viewRendererConfiguration = viewRendererConfiguration;
    }
}
