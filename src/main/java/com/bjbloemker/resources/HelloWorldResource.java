package com.bjbloemker.resources;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;


@Path("/parkpay/name/{name}")
public class HelloWorldResource {
    @GET
    public String helloWorld(@PathParam("name") String name) {
        if(name != null)
            return "Hello " + name + "!";
        else
            return "Hello not World! ";

    }
}