package com.wiza.controller;

import com.wiza.dto.MessageDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;

@Path("/error")
@Produces(MediaType.APPLICATION_JSON)
public class ExceptionController {

    @GET
    @Path("/{collection}")
    public MessageDto check(@PathParam("collection") String collection) {
        throw new WebApplicationException("Exception controller", Response.Status.BAD_REQUEST);
    }
}
