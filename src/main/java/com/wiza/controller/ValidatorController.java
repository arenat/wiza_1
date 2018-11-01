package com.wiza.controller;

import com.google.common.base.Strings;
import com.wiza.dto.MessageDto;
import com.wiza.representation.Person;
import io.dropwizard.validation.OneOf;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/validator")
@Produces(MediaType.APPLICATION_JSON)
public class ValidatorController {

    @GET
    public String validate(@NotEmpty @QueryParam("v") String v) {
        return "Got: " + v;
    }

    @PUT
    public Person person(@NotNull @Valid Person person) {
        return person;
    }

    @GET
    @Path("/max")
    public Integer max(@QueryParam("m") @Max(256) Integer m) {
        return m;
    }

    @GET
    @Path("params")
    public String getBean(@Valid @BeanParam MyBeanParams params) {
        return params.getField();
    }

    public static class MyBeanParams {
        @NotEmpty
        private String field;
        public String getField() {
            return field;
        }
        @QueryParam("foo")
        public void setField(String field) {
            this.field = Strings.nullToEmpty(field).trim();
        }
    }

    @GET
    @Path("oneof")
    public String oneOf(@QueryParam("e") @OneOf(value = {"in", "out"}, ignoreCase = true, ignoreWhitespace = true) String in) {
        return in;
    }

    @GET
    @Path("len")
    public String len(@QueryParam("l") @Length(max = 5) String len) {
        return  len;
    }

}