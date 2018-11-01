package com.wiza.controller;

import com.codahale.metrics.annotation.Timed;
import com.wiza.dao.PeopleDAO;
import com.wiza.dao.UserDAO;
import com.wiza.dto.MessageDto;
import com.wiza.representation.PeopleTable;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class PeopleController {
    PeopleDAO peopleDao;
    private final AtomicLong counter = new AtomicLong();

    public PeopleController(PeopleDAO peopleDAO) {
        this.peopleDao = peopleDAO;
    }

    @UnitOfWork
    @GET
    @Timed
    @RolesAllowed("ADMIN")
    public MessageDto getId(@QueryParam("id") Optional<Integer> id) {
        PeopleTable peopleTable = peopleDao.findById(id.orElse(1));
        return new MessageDto(peopleTable.getFullName() + " " + peopleTable.getJobTitle(), counter.getAndIncrement());
    }


    @Path("/findAll")
    @GET
    @UnitOfWork
    @Timed
    public List<PeopleTable> getAll() {
        return peopleDao.findAll();
    }
}
