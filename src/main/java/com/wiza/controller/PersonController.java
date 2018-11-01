package com.wiza.controller;

import com.wiza.representation.Person;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonController {

    @GET
    public Person get() {
        return new Person("camelCaseTo_snake_case");
    }
}
