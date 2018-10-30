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
@Produces(MediaType.APPLICATION_JSON) // lets Jersey’s content negotiation code know that this resource produces representations which are application/json.
public class IndexController {
    private final String template;
    private final String defaultName;
    private final Integer port;
    private final AtomicLong counter;

    public IndexController(String template, String defaultName, Integer port) {
        this.template = template;
        this.defaultName = defaultName;
        counter = new AtomicLong();
        this.port = port;
    }

    @GET
    @Timed // dropwizard automatically records the duration and rate of tis invocations as a Metrics Timer
    public MessageDto doHust(@QueryParam("name") Optional<String> name) {
        String value = String.format(template, name.orElse(defaultName));
        return new MessageDto(value, counter.incrementAndGet());
    }


    @Path("/ports")
    @GET
    @Timed // dropwizard automatically records the duration and rate of tis invocations as a Metrics Timer
    public MessageDto doPorts(@QueryParam("name") Optional<String> name) {
        return new MessageDto(port.toString(), counter.incrementAndGet());
    }
    // after Jersey takes MessageDto objects and looking for a provider which can write MessageDto as application/json
}
