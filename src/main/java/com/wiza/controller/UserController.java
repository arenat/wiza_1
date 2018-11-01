package com.wiza.controller;

import com.codahale.metrics.annotation.Timed;
import com.wiza.dao.UserDAO;
import com.wiza.dto.MessageDto;
import com.wiza.model.User;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import org.w3c.dom.UserDataHandler;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
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

    @RolesAllowed("ADMIN")
    @GET
    @Path("/get")
    public User getSecretPlan(@Context SecurityContext context) {
        User userPrincipal = (User) context.getUserPrincipal();
        return userPrincipal;
    }

    @GET
    @Path("/optional")
    public String getGreeting(@Auth Optional<User> userOpt) {
        if (userOpt.isPresent()) {
            return "Hello, " + userOpt.get().getName() + "!";
        } else {
            return "Greetings, anonymous visitor!";
        }
    }

}
