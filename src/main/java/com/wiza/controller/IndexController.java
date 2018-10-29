package com.wiza.controller;

import com.codahale.metrics.annotation.Timed;
import com.wiza.dto.MessageDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/") // tells Jersey that this resource is accessible at the URI /
@Produces(MediaType.APPLICATION_JSON)
public class IndexController {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public IndexController(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        counter = new AtomicLong();
    }

    @GET
    @Timed // dropwizard automatically records the duration and rate of tis invocations as a Metrics Timer
    public MessageDto doHust(@QueryParam("name") Optional<String> name) {
        String value = String.format(template, name.orElse(defaultName));
        return new MessageDto(value, counter.incrementAndGet());
    }
    // after Jersey takes MessageDto objects and looking for a provider which can write MessageDto as application/json
}
