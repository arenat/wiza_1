package com.wiza.controller;

import com.wiza.representation.Person;
import com.wiza.view.PersonView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/view")
@Produces(MediaType.TEXT_HTML)
public class FreemakerViewController {

    @GET
    public PersonView getPerson(@QueryParam("name") String name) {
        Person person = new Person();
        person.setFirstName(name);
        return new PersonView(person);
    }
}
