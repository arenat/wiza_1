package com.wiza.controller;

import com.codahale.metrics.annotation.Timed;
import com.wiza.dao.UserDAO;
import com.wiza.dto.MessageDto;
import io.dropwizard.hibernate.UnitOfWork;
import org.w3c.dom.UserDataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    UserDAO userDAO;
    private final AtomicLong counter = new AtomicLong();

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public MessageDto getId(@QueryParam("email") Optional<String> email) {
        Integer userId = userDAO.getUserIdByEmail(email.orElse("renat.ashirbakiev@hp.com"));
        return new MessageDto(userId.toString(), counter.getAndIncrement());
    }
}
