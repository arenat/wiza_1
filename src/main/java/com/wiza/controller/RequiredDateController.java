package com.wiza.controller;

import com.wiza.jerseyfilter.annotation.DateRequired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/filtered")
public class RequiredDateController {

    @GET
    @DateRequired
    public String filtered() {
        return "filtered";
    }
}
