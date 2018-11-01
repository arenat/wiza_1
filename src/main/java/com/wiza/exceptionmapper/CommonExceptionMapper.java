package com.wiza.exceptionmapper;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.wiza.exception.CommonException;
import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static com.codahale.metrics.MetricRegistry.name;

public class CommonExceptionMapper implements ExceptionMapper<CommonException> {
    private final Meter exceptions;
    public CommonExceptionMapper(MetricRegistry metrics) {
        exceptions = metrics.meter(name(getClass(), "exceptions"));
    }

    @Override
    public Response toResponse(CommonException e) {
        exceptions.mark();
        return Response.status(Response.Status.BAD_REQUEST)
                .header("X-YOU-SILLY", "true")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),
                        e.getMessage()))
                .build();
    }
}
