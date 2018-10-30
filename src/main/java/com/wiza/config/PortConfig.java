package com.wiza.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class PortConfig {

    @Min(8000)
    @Max(8080)
    private int serverPort = 8000;

    @JsonProperty
    public int getServerPort() {
        return serverPort;
    }

    @JsonProperty
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
